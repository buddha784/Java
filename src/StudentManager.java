import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Service and controller layer managing student records in the
 * Student Result Management System. Implements full CRUD operations,
 * search queries, sorting algorithms, and data persistence.
 *
 * Author: BUDDHA S (Reg No: 25BAI11592)
 * Institution: VIT Bhopal University
 */
public class StudentManager {

    private final List<Student> students = new ArrayList<>();
    private final FileStorageService storageService;

    public StudentManager() {
        this.storageService = new FileStorageService();
    }

    public StudentManager(FileStorageService storageService) {
        this.storageService = storageService != null ? storageService : new FileStorageService();
    }

    /**
     * Returns an unmodifiable view of the current student collection.
     */
    public List<Student> getStudents() {
        return Collections.unmodifiableList(students);
    }

    /**
     * Enrolls a new student record into the system.
     *
     * @param s Student entity to add.
     * @return true on successful addition.
     * @throws DuplicateStudentException if a student with the same ID already exists.
     */
    public boolean addStudent(Student s) throws DuplicateStudentException {
        if (s == null) {
            throw new IllegalArgumentException("Cannot add a null student record.");
        }

        if (searchStudent(s.getId()) != null) {
            throw new DuplicateStudentException("A student with ID #" + s.getId() + " is already enrolled.");
        }

        return students.add(s);
    }

    /**
     * Searches for a student by exact ID.
     *
     * @param searchId The numeric student ID.
     * @return Matching Student or null if not found.
     */
    public Student searchStudent(int searchId) {
        for (Student s : students) {
            if (s.getId() == searchId) {
                return s;
            }
        }
        return null;
    }

    /**
     * Retrieves a student by ID or throws StudentNotFoundException.
     */
    public Student getStudentById(int searchId) throws StudentNotFoundException {
        Student s = searchStudent(searchId);
        if (s == null) {
            throw new StudentNotFoundException("No student found with ID: " + searchId);
        }
        return s;
    }

    /**
     * Searches for students matching a name query (case-insensitive substring).
     */
    public List<Student> searchByName(String nameQuery) {
        List<Student> results = new ArrayList<>();
        if (nameQuery == null || nameQuery.trim().isEmpty()) {
            return results;
        }

        String lowerQuery = nameQuery.trim().toLowerCase();
        for (Student s : students) {
            if (s.getName().toLowerCase().contains(lowerQuery)) {
                results.add(s);
            }
        }
        return results;
    }

    /**
     * Updates subject marks for an existing student.
     *
     * @throws StudentNotFoundException if student ID does not exist.
     * @throws InvalidStudentDataException if marks are out of bounds.
     */
    public void updateStudentMarks(int id, double maths, double java, double physics)
            throws StudentNotFoundException, InvalidStudentDataException {
        Student s = getStudentById(id);
        try {
            s.updateMarks(maths, java, physics);
        } catch (IllegalArgumentException e) {
            throw new InvalidStudentDataException(e.getMessage());
        }
    }

    /**
     * Deletes a student record by ID.
     *
     * @param id Student ID to remove.
     * @return true if successfully removed.
     * @throws StudentNotFoundException if no student matches the ID.
     */
    public boolean deleteStudent(int id) throws StudentNotFoundException {
        Student s = getStudentById(id);
        return students.remove(s);
    }

    /**
     * Renders all student records in a structured ASCII table.
     */
    public void viewStudents() {
        TableFormatter.printStudentTable(students);
    }

    /**
     * Returns a new list of students ranked by aggregate percentage (descending).
     */
    public List<Student> getStudentsSortedByRank() {
        List<Student> ranked = new ArrayList<>(students);
        ranked.sort((s1, s2) -> Double.compare(
            ResultCalculator.getPercentage(s2),
            ResultCalculator.getPercentage(s1)
        ));
        return ranked;
    }

    /**
     * Returns a new list of students sorted by ID (ascending).
     */
    public List<Student> getStudentsSortedById() {
        List<Student> sorted = new ArrayList<>(students);
        sorted.sort(Comparator.comparingInt(Student::getId));
        return sorted;
    }

    /**
     * Returns a new list of students sorted by name alphabetically.
     */
    public List<Student> getStudentsSortedByName() {
        List<Student> sorted = new ArrayList<>(students);
        sorted.sort(Comparator.comparing(Student::getName, String.CASE_INSENSITIVE_ORDER));
        return sorted;
    }

    /**
     * Returns total count of enrolled students.
     */
    public int getStudentCount() {
        return students.size();
    }

    /**
     * Seeds realistic sample records for instant testing and vivas.
     */
    public void seedSampleData() {
        students.clear();
        try {
            addStudent(new Student(101, "BUDDHA S", 96.5, 98.0, 94.0));
            addStudent(new Student(102, "Aarav Sharma", 88.0, 91.5, 85.0));
            addStudent(new Student(103, "Rohan Verma", 74.0, 82.0, 78.5));
            addStudent(new Student(104, "Priya Nair", 62.0, 68.0, 59.0));
            addStudent(new Student(105, "Ananya Patel", 42.0, 50.5, 45.0));
            addStudent(new Student(106, "Vikram Singh", 35.0, 70.0, 65.0)); // Failed Maths
        } catch (DuplicateStudentException e) {
            // Unreachable with distinct sample IDs
        }
    }

    /**
     * Persists all student records to disk via FileStorageService.
     */
    public boolean saveToFile() throws IOException {
        return storageService.saveToFile(students);
    }

    /**
     * Loads student records from disk via FileStorageService.
     */
    public int loadFromFile() throws IOException {
        List<Student> loaded = storageService.loadFromFile();
        int addedCount = 0;
        for (Student s : loaded) {
            if (searchStudent(s.getId()) == null) {
                students.add(s);
                addedCount++;
            }
        }
        return addedCount;
    }
}