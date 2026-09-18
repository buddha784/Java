import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Analytical service providing comprehensive class performance metrics,
 * including central tendencies, extreme values (topper/lowest), pass/fail ratios,
 * subject-wise statistics, and grade distribution charts.
 *
 * Author: BUDDHA S (Reg No: 25BAI11592)
 * Institution: VIT Bhopal University
 */
public final class Statistics {

    private Statistics() {
        throw new UnsupportedOperationException("Utility class Statistics cannot be instantiated.");
    }

    /**
     * Computes and displays an executive analytics dashboard for the student batch.
     */
    public static void showStatistics(StudentManager mgr) {
        List<Student> list = mgr.getStudents();
        if (list.isEmpty()) {
            System.out.println("\n[!] No student records available to calculate statistics.");
            return;
        }

        int total = list.size();
        int passedCount = 0;
        double sumPercentage = 0.0;

        double sumMaths = 0.0, maxMaths = Double.MIN_VALUE, minMaths = Double.MAX_VALUE;
        double sumJava = 0.0, maxJava = Double.MIN_VALUE, minJava = Double.MAX_VALUE;
        double sumPhysics = 0.0, maxPhysics = Double.MIN_VALUE, minPhysics = Double.MAX_VALUE;

        Student topper = null;
        Student lowestScorer = null;
        double maxPct = -1.0;
        double minPct = 101.0;

        Map<String, Integer> gradeDist = new HashMap<>();
        String[] grades = {"A+", "A", "B", "C", "D", "E", "F"};
        for (String g : grades) {
            gradeDist.put(g, 0);
        }

        for (Student s : list) {
            double pct = ResultCalculator.getPercentage(s);
            sumPercentage += pct;

            if (ResultCalculator.isPassed(s)) {
                passedCount++;
            }

            // Grade counts
            String grade = ResultCalculator.getGrade(s);
            gradeDist.put(grade, gradeDist.getOrDefault(grade, 0) + 1);

            // Topper and Lowest Scorer
            if (pct > maxPct) {
                maxPct = pct;
                topper = s;
            }
            if (pct < minPct) {
                minPct = pct;
                lowestScorer = s;
            }

            // Maths
            double m = s.getMaths();
            sumMaths += m;
            if (m > maxMaths) maxMaths = m;
            if (m < minMaths) minMaths = m;

            // Java
            double j = s.getJava();
            sumJava += j;
            if (j > maxJava) maxJava = j;
            if (j < minJava) minJava = j;

            // Physics
            double p = s.getPhysics();
            sumPhysics += p;
            if (p > maxPhysics) maxPhysics = p;
            if (p < minPhysics) minPhysics = p;
        }

        int failedCount = total - passedCount;
        double passPercentage = ((double) passedCount / total) * 100.0;
        double classAvg = sumPercentage / total;

        System.out.println("\n+===================================================================+");
        System.out.println("|             ACADEMIC PERFORMANCE & ANALYTICS DASHBOARD            |");
        System.out.println("|                      VIT BHOPAL UNIVERSITY                        |");
        System.out.println("+===================================================================+");
        System.out.printf("| Total Enrolled Students  : %-38d |\n", total);
        System.out.printf("| Passed Students          : %-38d |\n", passedCount);
        System.out.printf("| Failed Students          : %-38d |\n", failedCount);
        System.out.printf("| Overall Pass Percentage  : %-37.2f%% |\n", passPercentage);
        System.out.printf("| Batch Average Percentage : %-37.2f%% |\n", classAvg);
        System.out.println("+-------------------------------------------------------------------+");
        System.out.println("| TOP PERFORMER & LOWEST SCORER                                     |");
        System.out.println("+-------------------------------------------------------------------+");
        if (topper != null) {
            System.out.printf("| Batch Topper : #%-4d %-22s  (%6.2f%%, Grade %-2s) |\n",
                topper.getId(), topper.getName(), ResultCalculator.getPercentage(topper), ResultCalculator.getGrade(topper));
        }
        if (lowestScorer != null) {
            System.out.printf("| Lowest Score : #%-4d %-22s  (%6.2f%%, Grade %-2s) |\n",
                lowestScorer.getId(), lowestScorer.getName(), ResultCalculator.getPercentage(lowestScorer), ResultCalculator.getGrade(lowestScorer));
        }
        System.out.println("+-------------------------------------------------------------------+");
        System.out.println("| SUBJECT-WISE PERFORMANCE SUMMARY                                  |");
        System.out.println("+-------------------------------------------------------------------+");
        System.out.println("| Subject      | Average Marks | Highest Score | Lowest Score       |");
        System.out.println("+--------------+---------------+---------------+--------------------+");
        System.out.printf("| Mathematics  | %13.2f | %13.2f | %18.2f |\n", sumMaths / total, maxMaths, minMaths);
        System.out.printf("| Java Lang    | %13.2f | %13.2f | %18.2f |\n", sumJava / total, maxJava, minJava);
        System.out.printf("| Physics      | %13.2f | %13.2f | %18.2f |\n", sumPhysics / total, maxPhysics, minPhysics);
        System.out.println("+-------------------------------------------------------------------+");
        System.out.println("| GRADE DISTRIBUTION BREAKDOWN                                      |");
        System.out.println("+-------------------------------------------------------------------+");
        for (String g : grades) {
            int cnt = gradeDist.get(g);
            double pct = ((double) cnt / total) * 100.0;
            // Print mini ASCII bar
            int barLen = (int) Math.round((pct / 100.0) * 20);
            String bar = "#".repeat(barLen) + " ".repeat(20 - barLen);
            System.out.printf("| Grade %-2s : %2d students (%5.1f%%) [%s] |\n", g, cnt, pct, bar);
        }
        System.out.println("+===================================================================+\n");
    }
}