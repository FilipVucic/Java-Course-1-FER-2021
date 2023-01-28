//opjj tests 2018/19 modified
package hr.fer.oprpp1.hw05.shell.commands;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UtilTest {

    @Test
    void parseArguments() {
        List<String> parsedArguments = Util.parseArguments("\"C:\\Documents and Settings\\Users\\javko\" \"C:\"");
        assertEquals("C:\\Documents and Settings\\Users\\javko", parsedArguments.get(0));
        assertEquals("C:", parsedArguments.get(1));
        assertThrows(IllegalArgumentException.class, () -> Util.parseArguments("\"C:\\fi le\".txt"));
    }
}