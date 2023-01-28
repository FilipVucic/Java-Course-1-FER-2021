package hr.fer.oprpp1.hw05.shell;

import hr.fer.oprpp1.hw05.shell.commands.*;

import java.util.Collections;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Command-line program MyShell.
 *
 * @author Filip Vucic
 */
public class MyShell {

    /**
     * Main method
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Welcome to MyShell v 1.0");

        Environment environment = new MyShellEnvironment();
        ShellStatus status = ShellStatus.CONTINUE;
        do {
            String[] lineArray = readLineOrLines(environment).trim().split("\\s+", 2);
            String commandName = lineArray[0];
            String arguments = lineArray.length == 1 ? "" : lineArray[1];
            ShellCommand command = environment.commands().get(commandName);
            if (command == null) {
                environment.writeln("Invalid command!");
                continue;
            }

            status = command.executeCommand(environment, arguments);
        } while (status != ShellStatus.TERMINATE);
    }

    /**
     * Read line or lines from the console.
     *
     * @param environment Environment
     * @return Line if line, merged lines to line if lines
     */
    private static String readLineOrLines(Environment environment) {
        environment.write(environment.getPromptSymbol() + " ");

        StringBuilder reader = new StringBuilder();
        while (true) {
            String line = environment.readLine();
            if (line.endsWith(environment.getMorelinesSymbol().toString())) {
                reader.append(line, 0, line.length() - 1);
                environment.write(environment.getMultilineSymbol() + " ");
            } else {
                reader.append(line);
                break;
            }
        }

        return reader.toString();
    }

    /**
     * Implementation of {@link Environment}.
     *
     * @author Filip Vucic
     */
    private static class MyShellEnvironment implements Environment {

        /**
         * {@link MyShellEnvironment} commands.
         */
        private final SortedMap<String, ShellCommand> commands = new TreeMap<>();

        /**
         * Scanner for input.
         */
        Scanner sc;

        /**
         * Prompt symbol.
         */
        private Character promptSymbol;

        /**
         * Morelines symbol.
         */
        private Character morelinesSymbol;

        /**
         * Multiline symbol.
         */
        private Character multilineSymbol;

        /**
         * Create new {@link MyShellEnvironment}.
         */
        public MyShellEnvironment() {
            commands.put("cat", new CatShellCommand());
            commands.put("charsets", new CharsetsShellCommand());
            commands.put("copy", new CopyShellCommand());
            commands.put("exit", new ExitShellCommand());
            commands.put("help", new HelpShellCommand());
            commands.put("hexdump", new HexdumpShellCommand());
            commands.put("ls", new LsShellCommand());
            commands.put("mkdir", new MkdirShellCommand());
            commands.put("symbol", new SymbolShellCommand());
            commands.put("tree", new TreeShellCommand());

            sc = new Scanner(System.in);
            promptSymbol = '>';
            morelinesSymbol = '\\';
            multilineSymbol = '|';
        }

        @Override
        public String readLine() throws ShellIOException {
            try {
                return sc.nextLine();
            } catch (Exception ex) {
                throw new ShellIOException("Can't read the line from console!");
            }
        }

        @Override
        public void write(String text) throws ShellIOException {
            try {
                System.out.print(text);
            } catch (Exception ex) {
                throw new ShellIOException("Can't write to console!");
            }
        }

        @Override
        public void writeln(String text) throws ShellIOException {
            try {
                System.out.println(text);
            } catch (Exception ex) {
                throw new ShellIOException("Can't write to console!");
            }
        }

        @Override
        public SortedMap<String, ShellCommand> commands() {
            return Collections.unmodifiableSortedMap(commands);
        }

        @Override
        public Character getMultilineSymbol() {
            return multilineSymbol;
        }

        @Override
        public void setMultilineSymbol(Character symbol) {
            multilineSymbol = symbol;
        }

        @Override
        public Character getPromptSymbol() {
            return promptSymbol;
        }

        @Override
        public void setPromptSymbol(Character symbol) {
            promptSymbol = symbol;
        }

        @Override
        public Character getMorelinesSymbol() {
            return morelinesSymbol;
        }

        @Override
        public void setMorelinesSymbol(Character symbol) {
            morelinesSymbol = symbol;
        }
    }
}
