package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of copy {@link ShellCommand} for {@link hr.fer.oprpp1.hw05.shell.MyShell} program.
 *
 * @author Filip Vucic
 */
public class CopyShellCommand implements ShellCommand {

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        List<String> parsedArguments;
        try {
            parsedArguments = Util.parseArguments(arguments);
        } catch (IllegalArgumentException ex) {
            env.writeln(ex.getMessage());
            return ShellStatus.CONTINUE;
        }

        if (parsedArguments.size() != 2) {
            env.writeln("Command copy should be used with 2 arguments!");
            return ShellStatus.CONTINUE;
        }

        try {
            Path src = Paths.get(parsedArguments.get(0));
            Path dest = Paths.get(parsedArguments.get(1));
            copy(src, dest, env);
        } catch (InvalidPathException ex) {
            env.writeln("Invalid path!");
        }

        return ShellStatus.CONTINUE;
    }

    /**
     * Copy one file to another.
     *
     * @param src  Source file
     * @param dest Destination file
     * @param env  Environment
     */
    private void copy(Path src, Path dest, Environment env) {
        if (Files.isDirectory(src)) {
            env.writeln("Source must be file!");
            return;
        }

        if (Files.isDirectory(dest)) {
            dest = Paths.get(dest.toString(), src.getFileName().toString());
        }

        if (Files.exists(dest)) {
            do {
                env.writeln("File already exists. Do you want to overwrite? (Y/N)");
                String answer = env.readLine();
                if (answer.toUpperCase().equals("N")) {
                    env.writeln("Copying aborted.");
                    return;
                } else if (answer.toUpperCase().equals("Y")) {
                    env.writeln("Overwriting.");
                    break;
                }
            } while (true);
        }

        try (InputStream is = Files.newInputStream(src); OutputStream os =
                Files.newOutputStream(dest)) {
            byte[] buff = new byte[1024];

            while (true) {
                int r = is.read(buff);
                if (r < 1) break;

                os.write(buff, 0, r);
            }

            env.writeln("File successfully copied.");
        } catch (IOException e) {
            env.writeln("Error while copying files!");
        }
    }

    @Override
    public String getCommandName() {
        return "copy";
    }

    @Override
    public List<String> getCommandDescription() {
        return Collections.unmodifiableList(List.of("The copy command expects two arguments: source file name and destination file name (i.e. paths and names)",
                "If the destination file exists, the user is asked if he wants to overwrite it."));
    }
}
