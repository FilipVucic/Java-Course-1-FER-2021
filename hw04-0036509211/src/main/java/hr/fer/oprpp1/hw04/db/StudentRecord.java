package hr.fer.oprpp1.hw04.db;

import java.util.Objects;

/**
 * Class which represents one student record.
 *
 * @author Filip Vucic
 */
public class StudentRecord {

    /**
     * JMBAG of the student.
     */
    private final String jmbag;

    /**
     * Last name of the student.
     */
    private final String lastName;

    /**
     * First name of the student.
     */
    private final String firstName;

    /**
     * Grade of the student.
     */
    private final int grade;

    /**
     * Create new {@link StudentRecord} with given student jmbag, last name, first name
     * and grade.
     *
     * @param jmbag     JMBAG of the student
     * @param lastName  Last name of the student
     * @param firstName First name of the student
     * @param grade     Grade of the student
     */
    public StudentRecord(String jmbag, String lastName, String firstName, int grade) {
        Objects.requireNonNull(jmbag, "JMBAG can not be null!");
        Objects.requireNonNull(lastName, "Last name can not be null!");
        Objects.requireNonNull(firstName, "First name can not be null!");

        this.jmbag = jmbag;
        this.lastName = lastName;
        this.firstName = firstName;
        this.grade = grade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentRecord that = (StudentRecord) o;
        return jmbag.equals(that.jmbag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jmbag);
    }

    /**
     * Get JMBAG of the student
     *
     * @return JMBAG of the student
     */
    public String getJmbag() {
        return jmbag;
    }

    /**
     * Get last name of the student
     *
     * @return Last name of the student
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Get first name of the student
     *
     * @return First name of the student
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Get grade of the student
     *
     * @return Grade of the student
     */
    public int getGrade() {
        return grade;
    }
}
