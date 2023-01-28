package hr.fer.oprpp1.hw05.shell.commands;

import java.util.ArrayList;
import java.util.List;

/**
 * Util class for {@link hr.fer.oprpp1.hw05.shell.ShellCommand}.
 *
 * @author Filip Vucic
 */
public class Util {

    /**
     * Parse arguments of the command.
     *
     * @param arguments Arguments
     * @return Parsed arguments
     */
    public static List<String> parseArguments(String arguments) {
        char[] data = arguments.toCharArray();
        int currentIndex = 0;
        List<String> parsedArguments = new ArrayList<>();

        while (currentIndex < data.length) {
            StringBuilder argument = new StringBuilder();
            if (data[currentIndex] == ' ') {
                currentIndex++;
                continue;
            }

            if (data[currentIndex] == '\"') {
                currentIndex++;
                if (currentIndex == data.length) {
                    throw new IllegalArgumentException("You must close the literals!");
                }

                while (data[currentIndex] != '\"') {
                    if (data[currentIndex] == '\\') {
                        currentIndex++;
                        if (currentIndex == data.length) {
                            throw new IllegalArgumentException("You must close the literals!");
                        }
                        if (data[currentIndex] == '\"' || data[currentIndex] == '\\') {
                            argument.append(data[currentIndex++]);
                        } else {
                            argument.append("\\").append(data[currentIndex++]);
                        }
                    } else {
                        argument.append(data[currentIndex++]);
                    }

                    if (currentIndex == data.length) {
                        throw new IllegalArgumentException("You must close the literals!");
                    }
                }
                currentIndex++;
                if (currentIndex < data.length && data[currentIndex] != ' ') {
                    throw new IllegalArgumentException("After the literals you should put a space!");
                }
            } else {
                while (data[currentIndex] != ' ') {
                    argument.append(data[currentIndex++]);
                    if (currentIndex == data.length) {
                        break;
                    }
                }
            }

            parsedArguments.add(argument.toString());
        }

        return parsedArguments;
    }
}
