package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class which represents database of {@link StudentRecord} instances.
 *
 * @author Filip Vucic
 */
public class StudentDatabase {

    /**
     * Students.
     */
    List<StudentRecord> students;

    /**
     * Map of students keyed by JMBAG.
     */
    Map<String, StudentRecord> studentsMap;

    /**
     * Create new {@link StudentDatabase} with given database file.
     *
     * @param dbFile Database file
     */
    public StudentDatabase(List<String> dbFile) {
        students = new ArrayList<>();
        studentsMap = new HashMap<>();

        parseStudents(dbFile);
    }

    /**
     * Get {@link StudentRecord} from the map for given JMBAG.
     *
     * @param jmbag Given JMBAG
     * @return null if no student with given JMBAG, {@link StudentRecord} of the student otherwise
     */
    public StudentRecord forJMBAG(String jmbag) {
        return studentsMap.get(jmbag);
    }

    /**
     * Get {@link StudentRecord} list filtered with given filter.
     *
     * @param filter Given filter
     * @return Filtered list
     */
    public List<StudentRecord> filter(IFilter filter) {
        List<StudentRecord> filteredList = new ArrayList<>();

        for (StudentRecord studentRecord : students) {
            if (filter.accepts(studentRecord)) {
                filteredList.add(studentRecord);
            }
        }

        return filteredList;
    }

    /**
     * Parse students database file.
     *
     * @param dbFile Database file
     */
    private void parseStudents(List<String> dbFile) {
        for (String record : dbFile) {
            String[] elements = record.split("\\t+");

            if (elements.length != 4) {
                throw new IllegalArgumentException(record + " should have 4 elements!");
            }

            int grade = 0;
            try {
                grade = Integer.parseInt(elements[3]);
            } catch (NumberFormatException ex) {
                System.err.println(record + " Grade should be int!");
                System.exit(1);
            }

            if (grade < 0 || grade > 5) {
                throw new IllegalArgumentException(record + " has illegal grade!");
            }
            StudentRecord student = new StudentRecord(elements[0], elements[1], elements[2], grade);

            if (studentsMap.containsKey(elements[0])) {
                throw new IllegalArgumentException(record + " JMBAG already in scope!");
            }

            students.add(student);
            studentsMap.put(elements[0], student);
        }
    }

}
