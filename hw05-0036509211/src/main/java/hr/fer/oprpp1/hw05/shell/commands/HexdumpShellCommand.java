package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of hexdump {@link ShellCommand} for {@link hr.fer.oprpp1.hw05.shell.MyShell} program.
 *
 * @author Filip Vucic
 */
public class HexdumpShellCommand implements ShellCommand {

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        List<String> parsedArguments;
        try {
            parsedArguments = Util.parseArguments(arguments);
        } catch (IllegalArgumentException ex) {
            env.writeln(ex.getMessage());
            return ShellStatus.CONTINUE;
        }

        if (parsedArguments.size() != 1) {
            env.writeln("Command hexdump should be used with 1 argument!");
            return ShellStatus.CONTINUE;
        }

        try {
            Path file = Paths.get(parsedArguments.get(0));
            printHexdump(file, env);
        } catch (InvalidPathException ex) {
            env.writeln("Invalid path!");
        }

        return ShellStatus.CONTINUE;
    }

    /**
     * Print hexdump of a file.
     *
     * @param file Path to the file
     * @param env  Environment
     */
    private void printHexdump(Path file, Environment env) {
        try (InputStream is = Files.newInputStream(file)) {
            StringBuilder hexdump = new StringBuilder();
            byte[] buff = new byte[16];
            int currentRow = 0x0;

            while (true) {
                int r = is.read(buff);
                if (r < 1) break;

                hexdump.append(String.format("%08X: ", currentRow));

                for (int i = 0; i < 16; i++) {
                    if (r > i) {
                        hexdump.append(String.format("%02X", buff[i]));
                    } else {
                        hexdump.append("  ");
                    }

                    if (i == 7) {
                        hexdump.append("|");
                    } else {
                        hexdump.append(" ");
                    }
                }

                hexdump.append("| ");

                for (int i = 0; i < r; i++) {
                    if (buff[i] >= 32) {
                        hexdump.append(String.format("%c", buff[i]));
                    } else {
                        hexdump.append('.');
                    }
                }

                hexdump.append("\n");

                currentRow += 0x10;
            }

            env.writeln(hexdump.toString());
        } catch (IOException e) {
            env.writeln("Error while reading file!");
        }
    }

    @Override
    public String getCommandName() {
        return "hexdump";
    }

    @Override
    public List<String> getCommandDescription() {
        return Collections.unmodifiableList(List.of("The hexdump command expects a single argument: file name.",
                "It then produces hex-output of the file."));
    }
}
