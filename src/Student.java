import java.util.Objects;

/**
 * Represents a student entity in the Student Result Management System.
 * Encapsulates student demographic details and marks across academic subjects
 * with strict data validation.
 *
 * Author: BUDDHA S (Reg No: 25BAI11592)
 * Institution: VIT Bhopal University
 */
public class Student {

    private int id;
    private String name;
    private double maths;
    private double java;
    private double physics;

    /**
     * Constructs a new Student record with full validation.
     *
     * @param id      Unique positive identifier for the student.
     * @param name    Full name of the student (non-empty).
     * @param maths   Marks obtained in Mathematics (0.0 to 100.0).
     * @param java    Marks obtained in Java Programming (0.0 to 100.0).
     * @param physics Marks obtained in Physics (0.0 to 100.0).
     * @throws IllegalArgumentException if any field fails validation constraints.
     */
    public Student(int id, String name, double maths, double java, double physics) {
        validateId(id);
        validateName(name);
        validateMark(maths, "Maths");
        validateMark(java, "Java");
        validateMark(physics, "Physics");

        this.id = id;
        this.name = name.trim();
        this.maths = maths;
        this.java = java;
        this.physics = physics;
    }

    // --- Validation Helpers ---

    public static void validateId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Student ID must be a positive integer (> 0). Provided: " + id);
        }
    }

    public static void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Student name cannot be null or empty.");
        }
    }

    public static void validateMark(double mark, String subject) {
        if (mark < 0.0 || mark > 100.0) {
            throw new IllegalArgumentException(subject + " marks must be between 0.0 and 100.0. Provided: " + mark);
        }
    }

    // --- Getters ---

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public double getMaths() {
        return this.maths;
    }

    public double getJava() {
        return this.java;
    }

    public double getPhysics() {
        return this.physics;
    }

    // --- Setters with Validation ---

    public void setName(String name) {
        validateName(name);
        this.name = name.trim();
    }

    public void setMaths(double maths) {
        validateMark(maths, "Maths");
        this.maths = maths;
    }

    public void setJava(double java) {
        validateMark(java, "Java");
        this.java = java;
    }

    public void setPhysics(double physics) {
        validateMark(physics, "Physics");
        this.physics = physics;
    }

    /**
     * Atomically updates all subject marks after verifying validity.
     */
    public void updateMarks(double maths, double java, double physics) {
        validateMark(maths, "Maths");
        validateMark(java, "Java");
        validateMark(physics, "Physics");
        this.maths = maths;
        this.java = java;
        this.physics = physics;
    }

    // --- Standard Object Overrides ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Student [ID=%d, Name='%s', Maths=%.2f, Java=%.2f, Physics=%.2f]",
                id, name, maths, java, physics);
    }
}