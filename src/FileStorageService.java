import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Persistence Service providing CSV file import/export capabilities
 * for long-term storage of student records.
 *
 * Author: BUDDHA S (Reg No: 25BAI11592)
 * Institution: VIT Bhopal University
 */
public class FileStorageService {

    public static final String DEFAULT_FILE_PATH = "students_data.csv";
    private static final String CSV_HEADER = "id,name,maths,java,physics";

    private final String filePath;

    public FileStorageService() {
        this(DEFAULT_FILE_PATH);
    }

    public FileStorageService(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Saves the collection of students to a CSV file.
     *
     * @param students List of students to persist.
     * @return true if write was successful.
     * @throws IOException on file write error.
     */
    public boolean saveToFile(List<Student> students) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(CSV_HEADER);
            writer.newLine();

            for (Student s : students) {
                // Escape commas in names if any
                String sanitizedName = s.getName().replace(",", ";");
                String line = String.format("%d,%s,%.2f,%.2f,%.2f",
                        s.getId(), sanitizedName, s.getMaths(), s.getJava(), s.getPhysics());
                writer.write(line);
                writer.newLine();
            }
            return true;
        }
    }

    /**
     * Loads student records from the CSV file.
     *
     * @return List of parsed Student records.
     * @throws IOException if reading the file fails.
     */
    public List<Student> loadFromFile() throws IOException {
        List<Student> loaded = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            return loaded;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine(); // Read header

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(",");
                if (parts.length < 5) continue;

                try {
                    int id = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim().replace(";", ",");
                    double maths = Double.parseDouble(parts[2].trim());
                    double java = Double.parseDouble(parts[3].trim());
                    double physics = Double.parseDouble(parts[4].trim());

                    loaded.add(new Student(id, name, maths, java, physics));
                } catch (IllegalArgumentException e) {
                    System.err.println("[!] Skipping corrupt CSV row: " + line + " (" + e.getMessage() + ")");
                }
            }
        }
        return loaded;
    }

    public String getFilePath() {
        return filePath;
    }
}
