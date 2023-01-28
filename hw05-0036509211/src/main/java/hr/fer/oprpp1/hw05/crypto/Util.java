package hr.fer.oprpp1.hw05.crypto;

/**
 * Util class with useful methods for decryption.
 *
 * @author Filip Vucic
 */
public class Util {

    /**
     * Hex chars.
     */
    private static final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();

    /**
     * Convert hex string to byte array.
     *
     * @param keyText Hex string
     * @return Byte array
     */
    public static byte[] hextobyte(String keyText) {
        int len = keyText.length();
        if (len % 2 != 0) {
            throw new IllegalArgumentException("Key length must be even!");
        }

        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((hexToBin(keyText.charAt(i)) << 4) + hexToBin(keyText.charAt(i + 1)));
        }

        return data;
    }

    /**
     * Convert hex number to binary number.
     *
     * @param ch Hex number
     * @return Binary number
     */
    private static int hexToBin(char ch) {
        if ('0' <= ch && ch <= '9') {
            return ch - '0';
        } else if ('a' <= ch && ch <= 'f') {
            return ch - 'a' + 10;
        } else if ('A' <= ch && ch <= 'F') {
            return ch - 'A' + 10;
        } else {
            throw new IllegalArgumentException("Key text contains non-hex character: " + ch);
        }
    }

    /**
     * Convert byte array to hex string.
     *
     * @param bytearray Byte array
     * @return Hex string
     */
    public static String bytetohex(byte[] bytearray) {
        char[] hexChars = new char[bytearray.length * 2];
        for (int j = 0; j < bytearray.length; j++) {
            int v = bytearray[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }

        return new String(hexChars);
    }
}
