// 2018/2019 opjj test modified, @author Filip Vucic

package hr.fer.oprpp1.custom.scripting.parser;

import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.nodes.EchoNode;

import hr.fer.oprpp1.custom.scripting.nodes.TextNode;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class SmartScriptParserTest {

    @Test
    void testFirst() {
       String text = readExample(1);
       SmartScriptParser parser = new SmartScriptParser(text);
       assertEquals(1, parser.getDocumentNode().numberOfChildren());
       assertTrue(parser.getDocumentNode().getChild(0) instanceof TextNode);
    }

    @Test
    void testSecond() {
        String text = readExample(2);
        SmartScriptParser parser = new SmartScriptParser(text);
        assertEquals(1, parser.getDocumentNode().numberOfChildren());
        assertTrue(parser.getDocumentNode().getChild(0) instanceof TextNode);
    }

    @Test
    void testThird() {
        String text = readExample(3);
        SmartScriptParser parser = new SmartScriptParser(text);
        assertEquals(1, parser.getDocumentNode().numberOfChildren());
        assertTrue(parser.getDocumentNode().getChild(0) instanceof TextNode);
    }

    @Test
    void testFourth() {
        String text = readExample(4);
        assertThrows(SmartScriptParserException.class,() -> new SmartScriptParser(text));
    }

    @Test
    void testFifth() {
        String text = readExample(5);
        assertThrows(SmartScriptParserException.class,() -> new SmartScriptParser(text));
    }

    @Test
    void testSixth() {
        String text = readExample(6);
        SmartScriptParser parser = new SmartScriptParser(text);
        assertEquals(2, parser.getDocumentNode().numberOfChildren());
        assertTrue(parser.getDocumentNode().getChild(0) instanceof TextNode);
        assertTrue(parser.getDocumentNode().getChild(1) instanceof EchoNode);
    }

    @Test
    void testSeventh() {
        String text = readExample(7);
        SmartScriptParser parser = new SmartScriptParser(text);
        assertEquals(2, parser.getDocumentNode().numberOfChildren());
        assertTrue(parser.getDocumentNode().getChild(0) instanceof TextNode);
        assertTrue(parser.getDocumentNode().getChild(1) instanceof EchoNode);
    }

    @Test
    void testEighth() {
        String text = readExample(8);
        assertThrows(SmartScriptParserException.class,() -> new SmartScriptParser(text));
    }

    @Test
    void testNinth() {
        String text = readExample(9);
        assertThrows(SmartScriptParserException.class,() -> new SmartScriptParser(text));
    }


    private String readExample(int n) {
        try(InputStream is = this.getClass().getClassLoader().getResourceAsStream("extra/primjer"+n+".txt")) {
            if(is==null) throw new RuntimeException("Datoteka extra/primjer"+n+".txt je nedostupna.");
            byte[] data = is.readAllBytes();
            String text = new String(data, StandardCharsets.UTF_8);
            return text;
        } catch(IOException ex) {
            throw new RuntimeException("Greška pri čitanju datoteke.", ex);
        }
    }

    @Test
    void parseSpojeniFor() {
        String text = "{$ FOR i-1.35bbb\"1\" $}{$END$}";
        SmartScriptParser parser1 = null;
        try {
            parser1 = new SmartScriptParser(text);
        } catch (SmartScriptParserException e) {
            System.out.println("Unable to parse document!");
            System.exit(-1);
        } catch (Exception e) {
            System.out.println("If this line ever executes, you have failed this class!");
            System.exit(-1);
        }

        DocumentNode document1 = parser1.getDocumentNode();
        String originalDocumentBody = document1.toString();

        SmartScriptParser parser2 = null;
        try {
            parser2 = new SmartScriptParser(originalDocumentBody);
        } catch (SmartScriptParserException e) {
            System.out.println("Unable to parse document!");
            System.out.println(e.getMessage());
            System.exit(-1);
        } catch (Exception e) {
            System.out.println("If this line ever executes, you have failed this class!");
            System.exit(-1);
        }

        DocumentNode document2 = parser2.getDocumentNode();
        String originalDocumentBody2 = document2.toString();


        assertEquals(originalDocumentBody, originalDocumentBody2);

    }

    @Test
    void parseOsnovni() {
        String text = "This is sample text.\r\n" +
                "{$ FOR i 1 10 1 $}\n" +
                " This is {$= i $}-th time this message is generated.\n" +
                "{$END$}\n" +
                "{$FOR i 0 10 2 $}\n" +
                " sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}\n" +
                "{$END$}";

        SmartScriptParser parser1 = null;
        try {
            parser1 = new SmartScriptParser(text);
        } catch (SmartScriptParserException e) {
            System.out.println("Unable to parse document!");
            System.exit(-1);
        } catch (Exception e) {
            System.out.println("If this line ever executes, you have failed this class!");
            System.exit(-1);
        }
        DocumentNode document1 = parser1.getDocumentNode();
        String originalDocumentBody = document1.toString();

        SmartScriptParser parser2 = null;
        try {
            parser2 = new SmartScriptParser(originalDocumentBody);
        } catch (SmartScriptParserException e) {
            System.out.println("Unable to parse document!");
            System.out.println(e.getMessage());
            System.exit(-1);
        } catch (Exception e) {
            System.out.println("If this line ever executes, you have failed this class!");
            System.exit(-1);
        }

        DocumentNode document2 = parser2.getDocumentNode();
        String originalDocumentBody2 = document2.toString();


        assertEquals(originalDocumentBody, originalDocumentBody2);
    }

    @Test
    void parseBrojPoziva() {
        String text = "{$= \"text/plain\" @setMimeType $}\n" +
                "Ovaj dokument pozvan je sljedeći broj puta:\n" +
                "{$= \"brojPoziva\" \"1\" @pparamGet @dup 1 + \"brojPoziva\" @pparamSet $}";

        SmartScriptParser parser1 = null;
        try {
            parser1 = new SmartScriptParser(text);
        } catch (SmartScriptParserException e) {
            System.out.println("Unable to parse document!");
            System.exit(-1);
        } catch (Exception e) {
            System.out.println("If this line ever executes, you have failed this class!");
            System.exit(-1);
        }
        DocumentNode document1 = parser1.getDocumentNode();
        String originalDocumentBody = document1.toString();

        SmartScriptParser parser2 = null;
        try {
            parser2 = new SmartScriptParser(originalDocumentBody);
        } catch (SmartScriptParserException e) {
            System.out.println("Unable to parse document!");
            System.out.println(e.getMessage());
            System.exit(-1);
        } catch (Exception e) {
            System.out.println("If this line ever executes, you have failed this class!");
            System.exit(-1);
        }

        DocumentNode document2 = parser2.getDocumentNode();
        String originalDocumentBody2 = document2.toString();


        assertEquals(originalDocumentBody, originalDocumentBody2);
    }

    @Test
    void parseZbrajanje() {
        String text = "{$= \"text/plain\" @setMimeType $}\n" +
                "Računam sumu brojeva:\n" +
                "{$= \"a=\" \"a\" 0 @paramGet \", b=\" \"b\" 0 @paramGet \", rezultat=\" \"a\" 0\n" +
                "@paramGet \"b\" 0 @paramGet + $}";

        SmartScriptParser parser1 = null;
        try {
            parser1 = new SmartScriptParser(text);
        } catch (SmartScriptParserException e) {
            System.out.println("Unable to parse document!");
            System.exit(-1);
        } catch (Exception e) {
            System.out.println("If this line ever executes, you have failed this class!");
            System.exit(-1);
        }
        DocumentNode document1 = parser1.getDocumentNode();
        String originalDocumentBody = document1.toString();

        SmartScriptParser parser2 = null;
        try {
            parser2 = new SmartScriptParser(originalDocumentBody);
        } catch (SmartScriptParserException e) {
            System.out.println("Unable to parse document!");
            System.out.println(e.getMessage());
            System.exit(-1);
        } catch (Exception e) {
            System.out.println("If this line ever executes, you have failed this class!");
            System.exit(-1);
        }

        DocumentNode document2 = parser2.getDocumentNode();
        String originalDocumentBody2 = document2.toString();


        assertEquals(originalDocumentBody, originalDocumentBody2);
    }

    @Test
    void parseTooManyEND() {
        String text = "This is sample text.\r\n" +
                "{$ FOR i 1 10 1 $}\n" +
                " This is {$= i $}-th time this message is generated.\n" +
                "{$END$}\n" +
                "{$FOR i 0 10 2 $}\n" +
                " sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}\n" +
                "{$END$}{$END$}";


        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
    }

    @Test
    void parseWrongEscaping() {
        String text = "{$= \"te\\xt/plain\" @setMimeType $}\n" +
                "Računam sumu brojeva:\n";


        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
    }
}