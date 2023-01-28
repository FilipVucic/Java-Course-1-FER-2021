package hr.fer.oprpp1.hw04.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

/**
 * StudentDB main program.
 *
 * @author Filip Vucic
 */
public class StudentDB {

    /**
     * Main method for querying the student database. Type "exit" if done.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        StudentDatabase db = null;
        try {
            db = new StudentDatabase(Files.readAllLines(
                    Paths.get("src/main/resources/database.txt"),
                    StandardCharsets.UTF_8
            ));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String query = sc.nextLine();
            if (query.equalsIgnoreCase("exit")) {
                System.out.println("Goodbye!");
                break;
            }
            if (!query.contains("query")) {
                System.out.println("No query keyword!");
                continue;
            }
            QueryParser parser;
            try {
                parser = new QueryParser(query);
            } catch (QueryParserException ex) {
                System.out.println(ex.getMessage());
                continue;
            }

            if (parser.isDirectQuery()) {
                StudentRecord r = db.forJMBAG(parser.getQueriedJMBAG());
                System.out.println("Using index for record retrieval.");
                printQueryResults(List.of(r));
            } else {
                try {
                    printQueryResults(db.filter(new QueryFilter(parser.getQuery())));
                } catch (IllegalArgumentException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }

        sc.close();
    }

    /**
     * Print query results.
     *
     * @param students List of {@link StudentRecord} instances.
     */
    private static void printQueryResults(List<StudentRecord> students) {
        int jmbagLength = 10;
        int longestFirstName = 0;
        int longestLastName = 0;
        int gradeLength = 1;
        for (StudentRecord student : students) {
            if (student.getLastName().length() > longestLastName) {
                longestLastName = student.getLastName().length();
            }

            if (student.getFirstName().length() > longestFirstName) {
                longestFirstName = student.getFirstName().length();
            }
        }

        StringBuilder queryResults = new StringBuilder();
        for (StudentRecord student : students) {
            queryResults.append("| ").append(student.getJmbag());
            queryResults.append(" | ").append(student.getLastName())
                    .append(" ".repeat(longestLastName - student.getLastName().length()));
            queryResults.append(" | ").append(student.getFirstName())
                    .append(" ".repeat(longestFirstName - student.getFirstName().length()));
            queryResults.append(" | ").append(student.getGrade()).append(" |");
            queryResults.append("\n");
        }

        StringBuilder border = new StringBuilder();
        border.append("+").append("=".repeat(jmbagLength + 2));
        border.append("+").append("=".repeat(longestLastName + 2));
        border.append("+").append("=".repeat(longestFirstName + 2));
        border.append("+").append("=".repeat(gradeLength + 2)).append("+");

        if (students.size() > 0) {
            System.out.println(border);
            System.out.print(queryResults);
            System.out.println(border);
        }

        System.out.println("Records selected: " + students.size());
    }
}
