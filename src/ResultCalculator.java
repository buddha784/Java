import java.util.Objects;

/**
 * Utility class providing academic computations for student results,
 * including total marks, percentage, grades, GPA, and pass/fail status.
 *
 * Author: BUDDHA S (Reg No: 25BAI11592)
 * Institution: VIT Bhopal University
 */
public final class ResultCalculator {

    public static final double PASSING_MARK = 40.0;
    public static final double MAX_MARK_PER_SUBJECT = 100.0;
    public static final int TOTAL_SUBJECTS = 3;
    public static final double MAX_TOTAL_MARKS = MAX_MARK_PER_SUBJECT * TOTAL_SUBJECTS;

    // Suppress default constructor for noninstantiability
    private ResultCalculator() {
        throw new UnsupportedOperationException("Utility class ResultCalculator cannot be instantiated.");
    }

    /**
     * Computes total marks obtained across all subjects.
     */
    public static double getTotal(Student st) {
        Objects.requireNonNull(st, "Student record cannot be null.");
        return st.getMaths() + st.getJava() + st.getPhysics();
    }

    /**
     * Computes the overall percentage.
     */
    public static double getPercentage(Student st) {
        return getTotal(st) / TOTAL_SUBJECTS;
    }

    /**
     * Determines whether the student cleared all subjects.
     * A student passes if and only if each subject score is >= 40.0.
     */
    public static boolean isPassed(Student st) {
        Objects.requireNonNull(st, "Student record cannot be null.");
        return st.getMaths() >= PASSING_MARK &&
               st.getJava() >= PASSING_MARK &&
               st.getPhysics() >= PASSING_MARK;
    }

    /**
     * Returns "PASS" or "FAIL" status string.
     */
    public static String getResult(Student st) {
        return isPassed(st) ? "PASS" : "FAIL";
    }

    /**
     * Evaluates letter grade based on percentage and pass status.
     * Students failing any subject are designated 'F' grade.
     */
    public static String getGrade(Student st) {
        if (!isPassed(st)) {
            return "F";
        }

        double pct = getPercentage(st);
        if (pct >= 90.0) return "A+";
        if (pct >= 80.0) return "A";
        if (pct >= 70.0) return "B";
        if (pct >= 60.0) return "C";
        if (pct >= 50.0) return "D";
        if (pct >= 40.0) return "E";
        return "F";
    }

    /**
     * Computes Grade Point Average (GPA) on a 10.0 scale.
     */
    public static double getGPA(Student st) {
        String grade = getGrade(st);
        return switch (grade) {
            case "A+" -> 10.0;
            case "A"  -> 9.0;
            case "B"  -> 8.0;
            case "C"  -> 7.0;
            case "D"  -> 6.0;
            case "E"  -> 5.0;
            default   -> 0.0;
        };
    }

    /**
     * Returns academic performance remarks for reporting.
     */
    public static String getRemark(Student st) {
        if (!isPassed(st)) {
            return "Needs Improvement (Failed in one or more subjects)";
        }
        double pct = getPercentage(st);
        if (pct >= 90.0) return "Outstanding - First Class with Distinction";
        if (pct >= 80.0) return "Excellent - First Class";
        if (pct >= 70.0) return "Very Good - First Class";
        if (pct >= 60.0) return "Good - Second Class";
        if (pct >= 50.0) return "Average - Second Class";
        return "Pass";
    }
}
