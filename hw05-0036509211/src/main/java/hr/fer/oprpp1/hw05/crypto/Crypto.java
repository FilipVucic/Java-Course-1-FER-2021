package hr.fer.oprpp1.hw05.crypto;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

/**
 * Crypto program used for checking digest, encrypting and decrypting files.
 *
 * @author Filip Vucic
 */
public class Crypto {

    /**
     * Main method.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        if (args.length == 0) {
            System.out.println("Please enter program arguments.");
            System.exit(1);
        }

        boolean encrypt = true;
        switch (args[0]) {
            case "checksha":
                if (args.length != 2) {
                    System.out.println("checksha should have only 1 argument!");
                }

                Path digestPath = Paths.get(args[1]);
                try (InputStream is = Files.newInputStream(digestPath)) {
                    MessageDigest shaMessageDigest = MessageDigest.getInstance("SHA-256");

                    byte[] buff = new byte[1024];
                    while (true) {
                        int r = is.read(buff);
                        if (r < 1) break;

                        shaMessageDigest.update(buff, 0, r);
                    }

                    String actualDigest = Util.bytetohex(shaMessageDigest.digest());

                    System.out.println("Please provide expected sha-256 digest for " + digestPath.getFileName());
                    System.out.print("> ");
                    String expectedDigest = sc.next();
                    if (expectedDigest.equals(actualDigest)) {
                        System.out.println("Digesting completed. Digest of " + digestPath.getFileName() + " matches expected digest.");
                    } else {
                        System.out.println("Digesting completed. Digest of " + digestPath.getFileName() + " does not match the expected" +
                                " digest. Digest was: " + actualDigest);
                    }
                } catch (IOException | NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                break;
            case "decrypt":
                encrypt = false;
            case "encrypt":
                if (args.length != 3) {
                    String message = encrypt ? "encrypt" : "decrypt";
                    throw new IllegalArgumentException(message + " should have 2 arguments!");
                }

                Path inputPath = Paths.get(args[1]);
                Path outputPath = Paths.get(args[2]);
                try (InputStream is = Files.newInputStream(inputPath);
                     OutputStream os = Files.newOutputStream(outputPath)) {
                    System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
                    System.out.print("> ");
                    String keyText = sc.next();
                    SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");

                    System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
                    System.out.print("> ");
                    String ivText = sc.next();
                    AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));

                    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                    cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);

                    byte[] buff = new byte[1024];
                    while (true) {
                        int r = is.read(buff);
                        if (r < 1) break;

                        os.write(cipher.update(buff, 0, r));
                    }

                    os.write(cipher.doFinal());
                } catch (IOException | BadPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException |
                        InvalidKeyException | InvalidAlgorithmParameterException | NoSuchPaddingException e) {
                    e.printStackTrace();
                }

                String message = encrypt ? "Encryption" : "Decryption";
                System.out.println(message + " completed. Generated file " + outputPath.getFileName() + " based on file " + inputPath.getFileName());
                break;
            default:
                System.out.println("Invalid command.");
        }

        sc.close();
    }
}
