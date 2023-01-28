package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.util.Collections;
import java.util.List;

/**
 * Implementation of help {@link ShellCommand} for {@link hr.fer.oprpp1.hw05.shell.MyShell} program.
 *
 * @author Filip Vucic
 */
public class HelpShellCommand implements ShellCommand {

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        List<String> parsedArguments;
        try {
            parsedArguments = Util.parseArguments(arguments);
        } catch (IllegalArgumentException ex) {
            env.writeln(ex.getMessage());
            return ShellStatus.CONTINUE;
        }

        if (parsedArguments.size() == 0) {
            for (String command : env.commands().keySet()) {
                env.writeln(command);
            }
        } else if (parsedArguments.size() == 1) {
            ShellCommand command = env.commands().get(parsedArguments.get(0));
            if (command == null) {
                env.writeln("Command " + parsedArguments.get(0) + " does not exist!");
            } else {
                env.writeln("Command name: " + command.getCommandName());
                env.writeln("Command description:");
                for (String line : command.getCommandDescription()) {
                    env.writeln("\t" + line);
                }
            }
        } else {
            env.writeln("Command help should be used with 0 or 1 argument!");
        }

        return ShellStatus.CONTINUE;
    }

    @Override
    public String getCommandName() {
        return "help";
    }

    @Override
    public List<String> getCommandDescription() {
        return Collections.unmodifiableList(List.of("If started without arguments, it lists names of all supported commands",
                "If started with single argument, it prints name and the description of the selected command"));
    }
}
