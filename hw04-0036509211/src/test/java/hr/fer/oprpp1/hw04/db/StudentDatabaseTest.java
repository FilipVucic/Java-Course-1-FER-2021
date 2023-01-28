// 2018/2019 opjj test modified, @author Filip Vucic
package hr.fer.oprpp1.hw04.db;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentDatabaseTest {

    List<String> studentRecords;
    StudentDatabase studentDatabase;

    StudentDatabaseTest() throws IOException {
        studentRecords = Files.readAllLines(Paths.get("database.txt"));
        studentDatabase = new StudentDatabase(studentRecords);
    }

    @Test
    void forJMBAG() {
        assertEquals("Jakobušić", studentDatabase.forJMBAG("0000000021").getLastName());
    }

    @Test
    void filter() {
        List<StudentRecord> emptyFilteredList = studentDatabase.filter(record -> false);
        assertEquals(0, emptyFilteredList.size());
        List<StudentRecord> fullFilteredList = studentDatabase.filter(record -> true);
        assertEquals(fullFilteredList.size(), studentRecords.size());
    }

}