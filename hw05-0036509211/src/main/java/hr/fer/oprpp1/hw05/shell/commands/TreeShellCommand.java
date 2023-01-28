package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of tree {@link ShellCommand} for {@link hr.fer.oprpp1.hw05.shell.MyShell} program.
 *
 * @author Filip Vucic
 */
public class TreeShellCommand implements ShellCommand {

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
            env.writeln("Command tree should be used with 1 argument!");
            return ShellStatus.CONTINUE;
        }

        try {
            Path path = Paths.get(parsedArguments.get(0));
            if (!Files.isDirectory(path)) {
                env.writeln("tree argument must a be directory!");
            } else {
                printTree(path, env);
            }
        } catch (InvalidPathException ex) {
            env.writeln("Invalid path!");
        }

        return ShellStatus.CONTINUE;
    }

    /**
     * Print tree of the directory.
     *
     * @param path Path to the directory
     * @param env  Environment
     */
    private void printTree(Path path, Environment env) {
        try {
            Files.walkFileTree(path, new SimpleFileVisitor<>() {
                private int level;

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                    env.writeln("  ".repeat(level) + dir.getFileName());
                    level++;
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                    level--;
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    env.writeln("  ".repeat(level) + file.getFileName());
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (InvalidPathException ex) {
            env.writeln("Invalid path!");
        } catch (IOException e) {
            env.writeln("Error occurred while walking file tree!");
        }
    }

    @Override
    public String getCommandName() {
        return "tree";
    }

    @Override
    public List<String> getCommandDescription() {
        return Collections.unmodifiableList(List.of("The tree command expects a single argument: directory name and prints a tree",
                "Each directory level shifts output two characters to the right"));
    }
}
