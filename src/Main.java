import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Main application entry point for the Student Result Management System (SRMS).
 * Provides an interactive console-based user interface with input validation,
 * clear workflow navigation, and comprehensive student result lifecycle management.
 *
 * Project: VITyarthi - Build Your Own Project (Flipped Course Evaluation)
 * Author: BUDDHA S
 * Registration Number: 25BAI11592
 * Institution: Vellore Institute of Technology (VIT), Bhopal
 */
public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        StudentManager sm = new StudentManager();

        printWelcomeBanner();

        boolean running = true;
        while (running) {
            showMenu(sm.getStudentCount());
            String raw = in.nextLine().trim();
            if (raw.isEmpty()) continue;

            int cmd;
            try {
                cmd = Integer.parseInt(raw);
            } catch (NumberFormatException e) {
                System.out.println("\n[!] Invalid input: Please enter a numeric option between 1 and 11.");
                continue;
            }

            switch (cmd) {
                case 1 -> handleAddStudent(in, sm);
                case 2 -> sm.viewStudents();
                case 3 -> handleSearch(in, sm);
                case 4 -> handleUpdateMarks(in, sm);
                case 5 -> handleDeleteStudent(in, sm);
                case 6 -> handleLeaderboard(sm);
                case 7 -> Statistics.showStatistics(sm);
                case 8 -> handleSaveFile(sm);
                case 9 -> handleLoadFile(sm);
                case 10 -> handleSeedDemo(sm);
                case 11 -> {
                    System.out.println("\n=======================================================================");
                    System.out.println(" Thank you for using Student Result Management System (SRMS)!");
                    System.out.println(" Developed by: BUDDHA S (25BAI11592) | VIT Bhopal University");
                    System.out.println("=======================================================================");
                    running = false;
                }
                default -> System.out.println("\n[!] Invalid option: Please select a valid menu item [1-11].");
            }
        }
        in.close();
    }

    private static void printWelcomeBanner() {
        System.out.println("=======================================================================");
        System.out.println("       STUDENT RESULT MANAGEMENT SYSTEM (SRMS) - ACADEMIC SUITE       ");
        System.out.println("                    VELLORE INSTITUTE OF TECHNOLOGY                   ");
        System.out.println("                              VIT BHOPAL                              ");
        System.out.println("=======================================================================");
        System.out.println(" Developer  : BUDDHA S");
        System.out.println(" Reg. No.   : 25BAI11592");
        System.out.println(" Program    : B.Tech Computer Science & Engineering (AI & ML)");
        System.out.println(" Evaluation : VITyarthi - Build Your Own Project");
        System.out.println("=======================================================================");
    }

    private static void showMenu(int activeRecords) {
        System.out.println("\n+------------------- MAIN SYSTEM MENU -------------------+");
        System.out.printf("| Active Records in Memory: %-28d |\n", activeRecords);
        System.out.println("+--------------------------------------------------------+");
        System.out.println("  1)  Enroll New Student Record");
        System.out.println("  2)  View All Student Records (Tabular Report)");
        System.out.println("  3)  Search Student (By ID or Name)");
        System.out.println("  4)  Update Student Subject Marks");
        System.out.println("  5)  Delete Student Record");
        System.out.println("  6)  View Class Leaderboard (Ranked by %)");
        System.out.println("  7)  View Performance & Analytical Statistics");
        System.out.println("  8)  Save Records to Disk (CSV Storage)");
        System.out.println("  9)  Load Records from Disk (CSV Storage)");
        System.out.println("  10) Seed Demo Sample Records (Quick Evaluation)");
        System.out.println("  11) Exit Application");
        System.out.println("+--------------------------------------------------------+");
        System.out.print("Select an option [1-11]: ");
    }

    private static void handleAddStudent(Scanner in, StudentManager sm) {
        System.out.println("\n--- [1] ENROLL NEW STUDENT RECORD ---");
        int id = promptInt(in, "Enter Unique Student ID: ", 1, Integer.MAX_VALUE);

        if (sm.searchStudent(id) != null) {
            System.out.println("\n[!] Error: Student ID #" + id + " already exists in the system.");
            return;
        }

        String name = promptString(in, "Enter Student Full Name: ");
        double maths = promptDouble(in, "Enter Mathematics Marks [0.0 - 100.0]: ", 0.0, 100.0);
        double java = promptDouble(in, "Enter Java Programming Marks [0.0 - 100.0]: ", 0.0, 100.0);
        double physics = promptDouble(in, "Enter Physics Marks [0.0 - 100.0]: ", 0.0, 100.0);

        try {
            Student student = new Student(id, name, maths, java, physics);
            sm.addStudent(student);
            System.out.println("\n[+] Success: Student #" + id + " (" + name + ") registered successfully!");
            TableFormatter.printStudentCard(student);
        } catch (DuplicateStudentException | IllegalArgumentException e) {
            System.out.println("\n[!] Registration Failed: " + e.getMessage());
        }
    }

    private static void handleSearch(Scanner in, StudentManager sm) {
        System.out.println("\n--- [3] SEARCH STUDENT RECORDS ---");
        System.out.println("1) Search by Student ID");
        System.out.println("2) Search by Student Name");
        int subCmd = promptInt(in, "Choose search mode [1-2]: ", 1, 2);

        if (subCmd == 1) {
            int searchId = promptInt(in, "Enter Student ID to lookup: ", 1, Integer.MAX_VALUE);
            Student s = sm.searchStudent(searchId);
            if (s != null) {
                TableFormatter.printStudentCard(s);
            } else {
                System.out.println("\n[-] No student record found with ID: " + searchId);
            }
        } else {
            String query = promptString(in, "Enter name or substring to search: ");
            List<Student> results = sm.searchByName(query);
            if (results.isEmpty()) {
                System.out.println("\n[-] No students found matching query: \"" + query + "\"");
            } else {
                System.out.println("\nMatching Records Found (" + results.size() + "):");
                TableFormatter.printStudentTable(results);
            }
        }
    }

    private static void handleUpdateMarks(Scanner in, StudentManager sm) {
        System.out.println("\n--- [4] UPDATE STUDENT MARKS ---");
        int id = promptInt(in, "Enter Student ID to modify: ", 1, Integer.MAX_VALUE);
        Student s = sm.searchStudent(id);

        if (s == null) {
            System.out.println("\n[-] Cannot update: Student ID #" + id + " does not exist.");
            return;
        }

        System.out.println("Existing record found for: " + s.getName());
        System.out.printf("Current Marks -> Maths: %.2f | Java: %.2f | Physics: %.2f\n",
                s.getMaths(), s.getJava(), s.getPhysics());

        double maths = promptDouble(in, "Enter New Mathematics Marks [0.0 - 100.0]: ", 0.0, 100.0);
        double java = promptDouble(in, "Enter New Java Marks [0.0 - 100.0]: ", 0.0, 100.0);
        double physics = promptDouble(in, "Enter New Physics Marks [0.0 - 100.0]: ", 0.0, 100.0);

        try {
            sm.updateStudentMarks(id, maths, java, physics);
            System.out.println("\n[+] Marks updated successfully!");
            TableFormatter.printStudentCard(s);
        } catch (StudentNotFoundException | InvalidStudentDataException e) {
            System.out.println("\n[!] Update failed: " + e.getMessage());
        }
    }

    private static void handleDeleteStudent(Scanner in, StudentManager sm) {
        System.out.println("\n--- [5] DELETE STUDENT RECORD ---");
        int id = promptInt(in, "Enter Student ID to delete: ", 1, Integer.MAX_VALUE);
        Student s = sm.searchStudent(id);

        if (s == null) {
            System.out.println("\n[-] Cannot delete: Student ID #" + id + " does not exist.");
            return;
        }

        System.out.print("Are you sure you want to permanently delete record #" + id + " (" + s.getName() + ")? [y/N]: ");
        String confirm = in.nextLine().trim().toLowerCase();
        if (confirm.equals("y") || confirm.equals("yes")) {
            try {
                sm.deleteStudent(id);
                System.out.println("\n[+] Student record #" + id + " removed successfully.");
            } catch (StudentNotFoundException e) {
                System.out.println("\n[!] Deletion failed: " + e.getMessage());
            }
        } else {
            System.out.println("[*] Deletion cancelled by user.");
        }
    }

    private static void handleLeaderboard(StudentManager sm) {
        System.out.println("\n--- [6] CLASS LEADERBOARD (ACADEMIC MERIT LIST) ---");
        List<Student> ranked = sm.getStudentsSortedByRank();
        TableFormatter.printStudentTable(ranked);
    }

    private static void handleSaveFile(StudentManager sm) {
        System.out.println("\n--- [8] SAVE RECORDS TO DISK (CSV) ---");
        try {
            boolean success = sm.saveToFile();
            if (success) {
                System.out.println("\n[+] Successfully persisted " + sm.getStudentCount() + " student records to " + FileStorageService.DEFAULT_FILE_PATH);
            }
        } catch (IOException e) {
            System.out.println("\n[!] File persistence failed: " + e.getMessage());
        }
    }

    private static void handleLoadFile(StudentManager sm) {
        System.out.println("\n--- [9] LOAD RECORDS FROM DISK (CSV) ---");
        try {
            int loaded = sm.loadFromFile();
            System.out.println("\n[+] Loaded " + loaded + " new student record(s) from " + FileStorageService.DEFAULT_FILE_PATH);
            System.out.println("Total active records now: " + sm.getStudentCount());
        } catch (IOException e) {
            System.out.println("\n[!] Failed to load data file: " + e.getMessage());
        }
    }

    private static void handleSeedDemo(StudentManager sm) {
        System.out.println("\n--- [10] SEED DEMO SAMPLE RECORDS ---");
        sm.seedSampleData();
        System.out.println("[+] Pre-loaded 6 diverse sample student records into memory.");
        System.out.println("[*] Viewing populated class records:");
        sm.viewStudents();
    }

    // --- Input Validation Helpers ---

    private static int promptInt(Scanner sc, String msg, int min, int max) {
        while (true) {
            System.out.print(msg);
            String line = sc.nextLine().trim();
            try {
                int val = Integer.parseInt(line);
                if (val < min || val > max) {
                    System.out.printf("[!] Value must be between %d and %d. Please try again.\n", min, max);
                    continue;
                }
                return val;
            } catch (NumberFormatException e) {
                System.out.println("[!] Invalid input. Please enter a valid integer.");
            }
        }
    }

    private static double promptDouble(Scanner sc, String msg, double min, double max) {
        while (true) {
            System.out.print(msg);
            String line = sc.nextLine().trim();
            try {
                double val = Double.parseDouble(line);
                if (val < min || val > max) {
                    System.out.printf("[!] Value must be between %.2f and %.2f. Please try again.\n", min, max);
                    continue;
                }
                return val;
            } catch (NumberFormatException e) {
                System.out.println("[!] Invalid input. Please enter a valid decimal number.");
            }
        }
    }

    private static String promptString(Scanner sc, String msg) {
        while (true) {
            System.out.print(msg);
            String line = sc.nextLine().trim();
            if (line.isEmpty()) {
                System.out.println("[!] Text cannot be empty. Please enter a valid value.");
                continue;
            }
            return line;
        }
    }
}