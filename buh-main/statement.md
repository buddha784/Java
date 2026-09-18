# Problem Statement & Scope Specification

**Course Evaluation**: VITyarthi - Build Your Own Project (Flipped Course Evaluation)  
**Student Name**: BUDDHA S  
**Registration Number**: 25BAI11592  
**Program**: B.Tech in Artificial Intelligence & Machine Learning / Computer Science  
**Institution**: Vellore Institute of Technology (VIT), Bhopal  
**Project Title**: Student Result Management System (SRMS)

---

## 1. Problem Statement
Academic institutions handle vast amounts of student evaluation data every semester. In many secondary schools, colleges, and departmental setups, grading and result compilation are still conducted manually or via disconnected spreadsheets. This traditional workflow presents numerous challenges:
- **Human Error**: Computational mistakes in aggregate percentages, CGPA/GPA rounding, and letter grade determinations.
- **Data Inconsistencies**: Absence of strict input boundary validation allows corrupt inputs (e.g., negative marks, scores exceeding the maximum limit, duplicate student identification numbers).
- **Inefficient Analytics**: Calculating class averages, subject-specific trends, and identifying academic outliers (toppers and students requiring remedial assistance) requires tedious manual recalculations.
- **Lack of Persistence & Auditability**: Volatile in-memory tools lose critical academic records upon session termination, leading to unrecoverable data loss.

There is a critical need for an automated, robust, object-oriented software solution that guarantees mathematical correctness, enforces relational and domain integrity constraints, provides actionable statistical insights, and persists data reliably across sessions.

---

## 2. Scope of the Project
The **Student Result Management System (SRMS)** is designed as an enterprise-grade academic record processing and analytics engine.

### In-Scope:
- **Comprehensive Lifecycle Management (CRUD)**: Complete lifecycle control over student records, including validation-enforced registration, record updates, searching, and deletions.
- **Automated Result Computation**: Standardized 10-point GPA scale and Letter Grade generation (A+ through F) based on institutional evaluation standards.
- **Multi-Subject Performance Tracking**: Granular tracking across core technical disciplines (Mathematics, Java Programming, Physics).
- **Statistical Dashboard & Trend Analytics**: Real-time batch-wide analytics including overall pass rates, class average percentages, subject-wise extrema and averages, and visual ASCII grade distribution curves.
- **Persistent Storage**: Automated comma-separated values (CSV) file serialization and deserialization ensuring durability.
- **Fault-Tolerant CLI Interface**: Defensive terminal interface providing descriptive error prompts without application crashes.

### Out-of-Scope (Future Enhancements):
- Multi-user role-based authentication (Admin vs. Faculty vs. Student portals).
- Distributed client-server networking or RESTful API layer.
- Relational SQL database clustering (e.g., PostgreSQL / MySQL connection pools).

---

## 3. Target Users
1. **Academic Instructors & Faculty**: Enables teachers to record marks, inspect individual student cards, update scores post-re-evaluation, and identify at-risk students.
2. **Departmental Academic Administrators / Deans**: Enables academic leadership to examine batch statistics, track course-wide pass percentages, and review distribution curves.
3. **Examination Cell / Registrar Office**: Facilitates automated merit list generation, class topper recognition, and verifiable file backups.

---

## 4. High-Level Features
- **Strict Data Validation Engine**: Zero tolerance for invalid IDs, blank names, and out-of-range marks ($[0.0, 100.0]$).
- **Duplicate Prevention System**: Uniqueness guarantees enforced at the service layer via custom exception handling (`DuplicateStudentException`).
- **Standardized University Grading Scale**: Automated mapping to institutional grades (A+, A, B, C, D, E, F) and 10.0 scale Grade Points.
- **Class Merit List & Leaderboard**: Real-time ranking of students sorted by descending academic aggregate.
- **Dual Search Functionality**: Query records instantly by unique Student ID or partial case-insensitive student names.
- **Formatted Tabular Reporting**: Professional ASCII grid layout with header columns, dividers, and fixed-width formatting.
- **CSV Data Persistence**: Clean import and export mechanism with corrupt line recovery and data integrity preservation.
- **Quick Evaluation Seeder**: Pre-loaded mock dataset representing diverse academic scenarios for rapid demonstration during academic evaluations and vivas.
