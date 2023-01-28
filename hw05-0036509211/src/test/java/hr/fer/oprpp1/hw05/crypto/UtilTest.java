//opjj tests 2018/19 modified
package hr.fer.oprpp1.hw05.crypto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilTest {

    @Test
    void hextobyte() {
        byte[] byteArray = Util.hextobyte("01aE22");

        assertEquals(byteArray[0], 1);
        assertEquals(byteArray[1], -82);
        assertEquals(byteArray[2], 34);
    }

    @Test
    void hextobyteempty() {
        byte[] byteArray = Util.hextobyte("");

        assertEquals(byteArray.length, 0);
    }

    @Test
    void bytetohex() {
        String stringHex = Util.bytetohex(new byte[] {1, -82, 34});

        assertEquals(stringHex, "01ae22");
    }

    @Test
    void bytetohexempty() {
        String stringHex = Util.bytetohex(new byte[] {});

        assertEquals(stringHex, "");
    }
}