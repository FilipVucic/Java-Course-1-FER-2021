package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of cat {@link ShellCommand} for {@link hr.fer.oprpp1.hw05.shell.MyShell} program.
 *
 * @author Filip Vucic
 */
public class CatShellCommand implements ShellCommand {

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        List<String> parsedArguments;
        try {
            parsedArguments = Util.parseArguments(arguments);
        } catch (IllegalArgumentException ex) {
            env.writeln(ex.getMessage());
            return ShellStatus.CONTINUE;
        }

        if (parsedArguments.size() != 1 && parsedArguments.size() != 2) {
            env.writeln("Command cat should be used with 1 or 2 arguments!");
            return ShellStatus.CONTINUE;
        }

        try {
            Path path = Paths.get(parsedArguments.get(0));
            Charset charset = parsedArguments.size() == 1 ? Charset.defaultCharset() : Charset.forName(parsedArguments.get(1));
            print(env, path, charset);
        } catch (InvalidPathException ex) {
            env.writeln("Invalid path!");
        }


        return ShellStatus.CONTINUE;
    }

    /**
     * Print the file content.
     *
     * @param env     Environment
     * @param path    Path to the file
     * @param charset Charset
     */
    private void print(Environment env, Path path, Charset charset) {
        try (InputStream is = Files.newInputStream(path)) {
            byte[] buff = new byte[1024];

            while (true) {
                int r = is.read(buff);
                if (r < 1) break;

                env.write(new String(Arrays.copyOf(buff, r), charset));
            }

            env.writeln("");
        } catch (IOException e) {
            env.writeln("Can't open file!");
        }
    }

    @Override
    public String getCommandName() {
        return "cat";
    }

    @Override
    public List<String> getCommandDescription() {
        return Collections.unmodifiableList(List.of("Command cat takes one or two arguments.",
                "The first argument is path to some file and is mandatory.",
                "The second argument is charset name that should be used to interpret chars from bytes.",
                "If not provided, a default platform charset should be used.",
                "This command opens given file and writes its content to console."));
    }
}
