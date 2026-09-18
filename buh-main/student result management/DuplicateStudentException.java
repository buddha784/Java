/**
 * Exception thrown when attempting to enroll a student with an ID that already exists.
 *
 * Author: BUDDHA S (Reg No: 25BAI11592)
 * Institution: VIT Bhopal University
 */
public class DuplicateStudentException extends Exception {
    public DuplicateStudentException(String message) {
        super(message);
    }
}
