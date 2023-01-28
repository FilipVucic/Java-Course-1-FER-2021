package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.util.Collections;
import java.util.List;

/**
 * Implementation of symbol {@link ShellCommand} for {@link hr.fer.oprpp1.hw05.shell.MyShell} program.
 *
 * @author Filip Vucic
 */
public class SymbolShellCommand implements ShellCommand {

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
            env.writeln("Command symbol should be used with 1 or 2 arguments!");
            return ShellStatus.CONTINUE;
        }

        switch (parsedArguments.get(0).toUpperCase()) {
            case "PROMPT" -> {
                Character promptSymbol = env.getPromptSymbol();
                if (parsedArguments.size() == 1) {
                    env.writeln("Symbol for PROMPT is '" + promptSymbol + "'");
                } else {
                    env.setPromptSymbol(parsedArguments.get(1).charAt(0));
                    env.writeln("Symbol for PROMPT changed from '" + promptSymbol +
                            "' to '" + env.getPromptSymbol() + "'");
                }
            }
            case "MORELINES" -> {
                Character morelinesSymbol = env.getMorelinesSymbol();
                if (parsedArguments.size() == 1) {
                    env.writeln("Symbol for MORELINES is '" + morelinesSymbol + "'");
                } else {
                    env.setMorelinesSymbol(parsedArguments.get(1).charAt(0));
                    env.writeln("Symbol for MORELINES changed from '" + morelinesSymbol +
                            "' to '" + env.getMorelinesSymbol() + "'");
                }
            }
            case "MULTILINE" -> {
                Character multilineSymbol = env.getMultilineSymbol();
                if (parsedArguments.size() == 1) {
                    env.writeln("Symbol for MULTILINE is '" + multilineSymbol + "'");
                } else {
                    env.setMultilineSymbol(parsedArguments.get(1).charAt(0));
                    env.writeln("Symbol for MULTILINE changed from '" + multilineSymbol +
                            "' to '" + env.getMultilineSymbol() + "'");
                }
            }
            default -> env.writeln("Invalid symbol name!");
        }

        return ShellStatus.CONTINUE;
    }

    @Override
    public String getCommandName() {
        return "symbol";
    }

    @Override
    public List<String> getCommandDescription() {
        return Collections.unmodifiableList(List.of("Command symbol takes 1 or 2 arguments.",
                "First argument is mandatory and can be one of the following: PROMPT/MORELINES/MULTILINE.",
                "When there is only 1 argument entered, environment prints the current symbol.",
                "If you want to change the current symbol, give the new symbol as second argument."));
    }
}
