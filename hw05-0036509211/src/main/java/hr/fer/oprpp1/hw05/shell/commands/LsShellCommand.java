package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Implementation of ls {@link ShellCommand} for {@link hr.fer.oprpp1.hw05.shell.MyShell} program.
 *
 * @author Filip Vucic
 */
public class LsShellCommand implements ShellCommand {

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
            env.writeln("Command ls should be used with 1 argument!");
            return ShellStatus.CONTINUE;
        }

        try {
            Path path = Paths.get(parsedArguments.get(0));
            if (!Files.isDirectory(path)) {
                env.writeln("ls argument must a be directory!");
            } else {
                print(path, env);
            }
        } catch (InvalidPathException ex) {
            env.writeln("Invalid path!");
        }

        return ShellStatus.CONTINUE;
    }

    /**
     * Print contents of a directory.
     *
     * @param path Path to the directory
     * @param env  Environment
     */
    private void print(Path path, Environment env) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StringBuilder lsPrint = new StringBuilder();

        try {
            Files.list(path).forEach(f -> {
                lsPrint.append(Files.isDirectory(f) ? "d" : "-");
                lsPrint.append(Files.isWritable(f) ? "r" : "-");
                lsPrint.append(Files.isReadable(f) ? "w" : "-");
                lsPrint.append(Files.isExecutable(f) ? "x" : "-");

                lsPrint.append(" ");

                try {
                    lsPrint.append(String.format("%10d", Files.size(f)));
                } catch (IOException e) {
                    env.writeln("Error while getting file size: " + f.getFileName());
                    return;
                }

                lsPrint.append(" ");

                BasicFileAttributeView faView = Files.getFileAttributeView(
                        f, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
                BasicFileAttributes attributes;
                try {
                    attributes = faView.readAttributes();
                } catch (IOException e) {
                    env.writeln("Error while reading file attributes: " + f.getFileName());
                    return;
                }
                FileTime fileTime = attributes.creationTime();
                String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
                lsPrint.append(formattedDateTime);

                lsPrint.append(" ");

                lsPrint.append(f.getFileName());

                lsPrint.append("\n");
            });

            env.writeln(lsPrint.toString());
        } catch (IOException e) {
            env.writeln("Error while listing directory!");
        }
    }

    @Override
    public String getCommandName() {
        return "ls";
    }

    @Override
    public List<String> getCommandDescription() {
        return Collections.unmodifiableList(List.of("Command ls takes a single argument – directory",
                "It then writes a directory listing (not recursive)."));
    }
}
