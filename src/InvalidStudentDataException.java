/**
 * Exception thrown when student demographic or academic mark data violates
 * business logic constraints (e.g. marks outside 0-100 range, non-positive ID).
 *
 * Author: BUDDHA S (Reg No: 25BAI11592)
 * Institution: VIT Bhopal University
 */
public class InvalidStudentDataException extends Exception {
    public InvalidStudentDataException(String message) {
        super(message);
    }
}
