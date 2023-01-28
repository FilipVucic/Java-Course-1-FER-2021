package hr.fer.oprpp1.hw05.shell;

import java.util.List;

/**
 * Shell command.
 *
 * @author Filip Vucic
 */
public interface ShellCommand {

    /**
     * Execute the shell command.
     *
     * @param env       Environment
     * @param arguments Arguments
     * @return {@link ShellStatus} as result of the command
     */
    ShellStatus executeCommand(Environment env, String arguments);

    /**
     * Get command name.
     *
     * @return Command name
     */
    String getCommandName();

    /**
     * Get command description.
     *
     * @return Command description
     */
    List<String> getCommandDescription();
}
