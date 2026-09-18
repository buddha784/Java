import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Automated Verification and Unit Test Suite for Student Result Management System.
 * Validates domain rules, calculation engines, exception triggers, and data persistence.
 * Can be executed directly via command line without external test dependencies.
 *
 * Author: BUDDHA S (Reg No: 25BAI11592)
 * Institution: VIT Bhopal University
 */
public class StudentValidationTest {

    private static int totalTests = 0;
    private static int passedTests = 0;

    public static void main(String[] args) {
        System.out.println("=======================================================================");
        System.out.println("     RUNNING AUTOMATED UNIT & VALIDATION TESTS FOR SRMS");
        System.out.println("=======================================================================");

        testStudentValidCreation();
        testStudentInvalidIdRejection();
        testStudentInvalidMarksRejection();
        testResultCalculatorPassCalculation();
        testResultCalculatorFailCalculation();
        testResultCalculatorGradingScale();
        testDuplicateStudentPrevention();
        testStudentSearchAndLookup();
        testStudentUpdateMarks();
        testStudentDeletion();
        testLeaderboardSorting();
        testFileStorageRoundtrip();

        System.out.println("=======================================================================");
        System.out.printf(" TEST SUMMARY: %d / %d Tests Passed (%.1f%% Success Rate)\n",
                passedTests, totalTests, ((double) passedTests / totalTests) * 100.0);
        System.out.println("=======================================================================");

        if (passedTests == totalTests) {
            System.out.println("[SUCCESS] ALL VERIFICATION TESTS PASSED SUCCESSFULLY!");
        } else {
            System.err.println("[FAILURE] SOME TESTS FAILED. PLEASE REVIEW TEST OUTPUT.");
            System.exit(1);
        }
    }

    private static void assertTrue(String testName, boolean condition) {
        totalTests++;
        if (condition) {
            passedTests++;
            System.out.println("  [PASS] " + testName);
        } else {
            System.err.println("  [FAIL] " + testName);
        }
    }

    private static void testStudentValidCreation() {
        Student s = new Student(101, "BUDDHA S", 95.0, 92.5, 90.0);
        assertTrue("Student Creation & Getters",
                s.getId() == 101 &&
                s.getName().equals("BUDDHA S") &&
                s.getMaths() == 95.0 &&
                s.getJava() == 92.5 &&
                s.getPhysics() == 90.0);
    }

    private static void testStudentInvalidIdRejection() {
        boolean exceptionThrown = false;
        try {
            new Student(-5, "Invalid Student", 50, 50, 50);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assertTrue("Negative Student ID Rejection", exceptionThrown);
    }

    private static void testStudentInvalidMarksRejection() {
        boolean exceptionThrownHigh = false;
        try {
            new Student(102, "High Mark", 105.0, 80, 80);
        } catch (IllegalArgumentException e) {
            exceptionThrownHigh = true;
        }

        boolean exceptionThrownLow = false;
        try {
            new Student(103, "Low Mark", 50, -10.0, 80);
        } catch (IllegalArgumentException e) {
            exceptionThrownLow = true;
        }

        assertTrue("Marks Boundary Validation (>100 and <0)", exceptionThrownHigh && exceptionThrownLow);
    }

    private static void testResultCalculatorPassCalculation() {
        Student s = new Student(104, "Passing Student", 90.0, 80.0, 70.0);
        double total = ResultCalculator.getTotal(s);
        double pct = ResultCalculator.getPercentage(s);
        boolean pass = ResultCalculator.isPassed(s);

        assertTrue("Total & Percentage Calculation",
                Math.abs(total - 240.0) < 0.001 &&
                Math.abs(pct - 80.0) < 0.001 &&
                pass);
    }

    private static void testResultCalculatorFailCalculation() {
        // High marks in two subjects, but failed in one (< 40)
        Student s = new Student(105, "Borderline Student", 95.0, 95.0, 35.0);
        boolean pass = ResultCalculator.isPassed(s);
        String grade = ResultCalculator.getGrade(s);
        String result = ResultCalculator.getResult(s);

        assertTrue("Subject Fail Overrides Overall Result",
                !pass &&
                "FAIL".equals(result) &&
                "F".equals(grade));
    }

    private static void testResultCalculatorGradingScale() {
        Student sDistinction = new Student(1, "A+", 95.0, 92.0, 91.0);
        Student sA = new Student(2, "A", 85.0, 82.0, 81.0);
        Student sB = new Student(3, "B", 75.0, 72.0, 71.0);
        Student sC = new Student(4, "C", 65.0, 62.0, 61.0);
        Student sD = new Student(5, "D", 55.0, 52.0, 51.0);
        Student sE = new Student(6, "E", 45.0, 42.0, 41.0);

        assertTrue("Grading Scale Verification (A+ to E)",
                "A+".equals(ResultCalculator.getGrade(sDistinction)) &&
                "A".equals(ResultCalculator.getGrade(sA)) &&
                "B".equals(ResultCalculator.getGrade(sB)) &&
                "C".equals(ResultCalculator.getGrade(sC)) &&
                "D".equals(ResultCalculator.getGrade(sD)) &&
                "E".equals(ResultCalculator.getGrade(sE)));
    }

    private static void testDuplicateStudentPrevention() {
        StudentManager sm = new StudentManager();
        boolean caught = false;
        try {
            sm.addStudent(new Student(201, "First Entry", 80, 80, 80));
            sm.addStudent(new Student(201, "Duplicate Entry", 90, 90, 90));
        } catch (DuplicateStudentException e) {
            caught = true;
        }
        assertTrue("Duplicate ID Prevention Triggered", caught && sm.getStudentCount() == 1);
    }

    private static void testStudentSearchAndLookup() {
        StudentManager sm = new StudentManager();
        try {
            sm.addStudent(new Student(301, "BUDDHA S", 90, 90, 90));
            sm.addStudent(new Student(302, "Aarav Sharma", 80, 80, 80));
        } catch (DuplicateStudentException e) {
            // Unreached
        }

        Student foundId = sm.searchStudent(301);
        List<Student> foundName = sm.searchByName("shraddha");

        assertTrue("Search by ID and Case-Insensitive Name",
                foundId != null &&
                foundId.getId() == 301 &&
                foundName.size() == 1 &&
                foundName.get(0).getId() == 301);
    }

    private static void testStudentUpdateMarks() {
        StudentManager sm = new StudentManager();
        try {
            sm.addStudent(new Student(401, "Test Update", 60, 60, 60));
            sm.updateStudentMarks(401, 85, 90, 95);
            Student updated = sm.searchStudent(401);
            assertTrue("Student Marks Update Verification",
                    updated.getMaths() == 85 &&
                    updated.getJava() == 90 &&
                    updated.getPhysics() == 95);
        } catch (Exception e) {
            assertTrue("Student Marks Update Verification", false);
        }
    }

    private static void testStudentDeletion() {
        StudentManager sm = new StudentManager();
        try {
            sm.addStudent(new Student(501, "To Delete", 70, 70, 70));
            boolean deleted = sm.deleteStudent(501);
            Student searched = sm.searchStudent(501);
            assertTrue("Student Record Deletion", deleted && searched == null && sm.getStudentCount() == 0);
        } catch (Exception e) {
            assertTrue("Student Record Deletion", false);
        }
    }

    private static void testLeaderboardSorting() {
        StudentManager sm = new StudentManager();
        try {
            sm.addStudent(new Student(601, "Lowest", 50, 50, 50));
            sm.addStudent(new Student(602, "Topper", 95, 95, 95));
            sm.addStudent(new Student(603, "Middle", 75, 75, 75));

            List<Student> ranked = sm.getStudentsSortedByRank();
            assertTrue("Leaderboard Descending Rank Order",
                    ranked.get(0).getId() == 602 &&
                    ranked.get(1).getId() == 603 &&
                    ranked.get(2).getId() == 601);
        } catch (Exception e) {
            assertTrue("Leaderboard Descending Rank Order", false);
        }
    }

    private static void testFileStorageRoundtrip() {
        String testPath = "test_students_temp.csv";
        FileStorageService storage = new FileStorageService(testPath);
        StudentManager sm = new StudentManager(storage);

        try {
            sm.addStudent(new Student(701, "File Test A", 88, 92, 79));
            sm.addStudent(new Student(702, "File Test B", 65, 70, 72));
            sm.saveToFile();

            StudentManager loadedSm = new StudentManager(storage);
            int loadedCount = loadedSm.loadFromFile();

            assertTrue("File Storage Save and Load Roundtrip",
                    loadedCount == 2 &&
                    loadedSm.searchStudent(701) != null &&
                    loadedSm.searchStudent(702) != null);
        } catch (Exception e) {
            assertTrue("File Storage Save and Load Roundtrip", false);
        } finally {
            File f = new File(testPath);
            if (f.exists()) f.delete();
        }
    }
}
