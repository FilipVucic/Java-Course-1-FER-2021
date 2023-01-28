package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of mkdir {@link ShellCommand} for {@link hr.fer.oprpp1.hw05.shell.MyShell} program.
 *
 * @author Filip Vucic
 */
public class MkdirShellCommand implements ShellCommand {

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
            env.writeln("Command mkdir should be used with 1 argument!");
            return ShellStatus.CONTINUE;
        }

        try {
            Path path = Paths.get(parsedArguments.get(0));
            Files.createDirectories(path);
            env.writeln("Successfully created directory.");
        } catch (InvalidPathException ex) {
            env.writeln("Invalid path!");
        } catch (IOException e) {
            env.writeln("Error while creating directory!");
        }

        return ShellStatus.CONTINUE;
    }

    @Override
    public String getCommandName() {
        return "mkdir";
    }

    @Override
    public List<String> getCommandDescription() {
        return Collections.unmodifiableList(List.of("The mkdir command takes a single argument: directory name.",
                "Then it creates the appropriate directory structure."));
    }
}
