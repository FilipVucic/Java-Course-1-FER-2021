package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.util.Collections;
import java.util.List;

/**
 * Implementation of exit {@link ShellCommand} for {@link hr.fer.oprpp1.hw05.shell.MyShell} program.
 *
 * @author Filip Vucic
 */
public class ExitShellCommand implements ShellCommand {

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        if (!arguments.isEmpty()) {
            env.writeln("Exit command should be used used without arguments!");
            return ShellStatus.CONTINUE;
        }

        env.writeln("Goodbye.");
        return ShellStatus.TERMINATE;
    }

    @Override
    public String getCommandName() {
        return "exit";
    }

    @Override
    public List<String> getCommandDescription() {
        return Collections.unmodifiableList(List.of("Command used for terminating MyShell.",
                "It is called without arguments."));
    }
}
