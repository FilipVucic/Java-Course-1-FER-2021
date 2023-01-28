package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of charsets {@link ShellCommand} for {@link hr.fer.oprpp1.hw05.shell.MyShell} program.
 *
 * @author Filip Vucic
 */
public class CharsetsShellCommand implements ShellCommand {

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        if (!arguments.isEmpty()) {
            env.writeln("Command charsets should be used without arguments!");
        } else {
            for (String charsetName : Charset.availableCharsets().keySet()) {
                env.writeln(charsetName);
            }
        }

        return ShellStatus.CONTINUE;
    }

    @Override
    public String getCommandName() {
        return "charsets";
    }

    @Override
    public List<String> getCommandDescription() {
        return Collections.unmodifiableList(List.of("Command charsets takes no arguments.",
                "It lists names of supported charsets for your Java platform.",
                "A single charset name is written per line."));
    }
}
