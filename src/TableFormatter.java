import java.util.List;

/**
 * Presentation utility that formats student academic records and summaries
 * into clean, aligned ASCII grid tables for console visualization.
 *
 * Author: BUDDHA S (Reg No: 25BAI11592)
 * Institution: VIT Bhopal University
 */
public final class TableFormatter {

    private static final String BORDER_LINE = 
        "+------+----------------------+-------+-------+---------+-------+------------+-------+------+--------+";
    private static final String HEADER_FORMAT = 
        "| %-4s | %-20s | %-5s | %-5s | %-7s | %-5s | %-10s | %-5s | %-4s | %-6s |\n";
    private static final String ROW_FORMAT = 
        "| %-4d | %-20s | %5.1f | %5.1f | %7.1f | %5.1f | %9.2f%% | %-5s | %4.1f | %-6s |\n";

    private TableFormatter() {
        throw new UnsupportedOperationException("Utility class TableFormatter cannot be instantiated.");
    }

    /**
     * Renders a list of students as a bordered ASCII table.
     */
    public static void printStudentTable(List<Student> students) {
        if (students == null || students.isEmpty()) {
            System.out.println("\n[!] No student records to display.");
            return;
        }

        System.out.println(BORDER_LINE);
        System.out.printf(HEADER_FORMAT, "ID", "Student Name", "Maths", "Java", "Physics", "Total", "Percentage", "Grade", "GPA", "Status");
        System.out.println(BORDER_LINE);

        for (Student s : students) {
            String displayName = s.getName();
            if (displayName.length() > 20) {
                displayName = displayName.substring(0, 17) + "...";
            }
            System.out.printf(ROW_FORMAT,
                s.getId(),
                displayName,
                s.getMaths(),
                s.getJava(),
                s.getPhysics(),
                ResultCalculator.getTotal(s),
                ResultCalculator.getPercentage(s),
                ResultCalculator.getGrade(s),
                ResultCalculator.getGPA(s),
                ResultCalculator.getResult(s)
            );
        }
        System.out.println(BORDER_LINE);
        System.out.println("Total Records: " + students.size());
    }

    /**
     * Prints a detailed performance report card for a single student.
     */
    public static void printStudentCard(Student s) {
        if (s == null) {
            System.out.println("[!] No student record provided.");
            return;
        }

        System.out.println("\n=======================================================");
        System.out.println("            OFFICIAL STUDENT REPORT CARD               ");
        System.out.println("              VIT BHOPAL UNIVERSITY                    ");
        System.out.println("=======================================================");
        System.out.printf(" Student ID      : %d\n", s.getId());
        System.out.printf(" Student Name    : %s\n", s.getName());
        System.out.println("-------------------------------------------------------");
        System.out.println(" Subject Breakdown:");
        System.out.printf("   - Mathematics : %6.2f / 100.00  [%s]\n", s.getMaths(), s.getMaths() >= ResultCalculator.PASSING_MARK ? "PASS" : "FAIL");
        System.out.printf("   - Java Lang   : %6.2f / 100.00  [%s]\n", s.getJava(), s.getJava() >= ResultCalculator.PASSING_MARK ? "PASS" : "FAIL");
        System.out.printf("   - Physics     : %6.2f / 100.00  [%s]\n", s.getPhysics(), s.getPhysics() >= ResultCalculator.PASSING_MARK ? "PASS" : "FAIL");
        System.out.println("-------------------------------------------------------");
        System.out.printf(" Grand Total     : %6.2f / 300.00\n", ResultCalculator.getTotal(s));
        System.out.printf(" Aggregate       : %6.2f%%\n", ResultCalculator.getPercentage(s));
        System.out.printf(" Letter Grade    : %s\n", ResultCalculator.getGrade(s));
        System.out.printf(" Grade Point Avg : %.1f / 10.0\n", ResultCalculator.getGPA(s));
        System.out.printf(" Final Result    : %s\n", ResultCalculator.getResult(s));
        System.out.printf(" Remarks         : %s\n", ResultCalculator.getRemark(s));
        System.out.println("=======================================================");
    }
}
