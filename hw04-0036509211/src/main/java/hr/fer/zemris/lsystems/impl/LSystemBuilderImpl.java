package hr.fer.zemris.lsystems.impl;

import hr.fer.oprpp1.custom.collections.Dictionary;
import hr.fer.oprpp1.math.Vector2D;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.*;

import java.awt.*;
import java.util.Arrays;

/**
 * Class which represents {@link LSystemBuilder} implementation.
 *
 * @author Filip Vucic
 */
public class LSystemBuilderImpl implements LSystemBuilder {

    /**
     * Registered productions {@link Dictionary}.
     */
    private final Dictionary<Character, String> registeredProductions;

    /**
     * Command {@link Dictionary}.
     */
    private final Dictionary<Character, Command> commands;
    /**
     * Default drawing color of the turtle.
     */
    private final Color DEFAULT_COLOR = Color.BLACK;
    /**
     * Unit length.
     */
    private double unitLength = 0.1;
    /**
     * Degree scaler of the unit length.
     */
    private double unitLengthDegreeScaler = 1;
    /**
     * Origin of the turtle.
     */
    private Vector2D origin;
    /**
     * Angle of the turtle.
     */
    private double angle = 0;
    /**
     * {@link LSystem} axiom.
     */
    private String axiom = "";

    /**
     * Create new {@link LSystemBuilderImpl}.
     */
    public LSystemBuilderImpl() {
        this.registeredProductions = new Dictionary<>();
        this.commands = new Dictionary<>();
        origin = new Vector2D(0, 0);
    }

    /**
     * Set unit length.
     *
     * @param v Unit length
     * @return This {@link LSystemBuilder}
     */
    @Override
    public LSystemBuilder setUnitLength(double v) {
        unitLength = v;
        return this;
    }

    /**
     * Set origin of the turtle.
     *
     * @param v  x coordinate of the turtle
     * @param v1 y coordinate of the turtle
     * @return This {@link LSystemBuilder}
     */
    @Override
    public LSystemBuilder setOrigin(double v, double v1) {
        origin = new Vector2D(v, v1);
        return this;
    }

    /**
     * Set angle of the turtle.
     *
     * @param v Angle of the turtle
     * @return This {@link LSystemBuilder}
     */
    @Override
    public LSystemBuilder setAngle(double v) {
        angle = v * Math.PI / 180;
        return this;
    }

    /**
     * Set axiom of LSystem.
     *
     * @param s Axiom of LSystem
     * @return This {@link LSystemBuilder}
     */
    @Override
    public LSystemBuilder setAxiom(String s) {
        axiom = s;
        return this;
    }

    /**
     * Set unit length degree scaler.
     *
     * @param v Unit length degree scaler
     * @return This {@link LSystemBuilder}
     */
    @Override
    public LSystemBuilder setUnitLengthDegreeScaler(double v) {
        unitLengthDegreeScaler = v;
        return this;
    }

    /**
     * Register new production in the {@link LSystem}.
     *
     * @param c Symbol of the production
     * @param s Production sequence
     * @return This {@link LSystemBuilder}
     */
    @Override
    public LSystemBuilder registerProduction(char c, String s) {
        registeredProductions.put(c, s);
        return this;
    }

    /**
     * Register new command in the {@link LSystem}.
     *
     * @param c Symbol of the command
     * @param s Command
     * @return This {@link LSystemBuilder}
     */
    @Override
    public LSystemBuilder registerCommand(char c, String s) {
        String[] commandParts = s.split("\\s+");
        Command command;

        if (commandParts[0].equalsIgnoreCase("color")) {
            try {
                Color color = Color.decode("#" + commandParts[1]);
                command = new ColorCommand(color);
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException("Invalid color argument!");
            }
        } else if (commandParts.length == 1) {
            if (commandParts[0].equalsIgnoreCase("push")) {
                command = new PushCommand();
            } else if (commandParts[0].equalsIgnoreCase("pop")) {
                command = new PopCommand();
            } else {
                throw new IllegalArgumentException("Invalid command!");
            }
        } else if (commandParts.length == 2) {

            double argument;
            try {
                argument = Double.parseDouble(commandParts[1]);
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException("Invalid " + commandParts[0] + " argument!");
            }

            command = switch (commandParts[0].toLowerCase()) {
                case "draw" -> new DrawCommand(argument);
                case "skip" -> new SkipCommand(argument);
                case "scale" -> new ScaleCommand(argument);
                case "rotate" -> new RotateCommand(argument * Math.PI / 180);
                default -> throw new IllegalArgumentException("Invalid command!");
            };
        } else {
            throw new IllegalArgumentException("Invalid command!");
        }

        commands.put(c, command);
        return this;
    }

    /**
     * Configure this {@link LSystemBuilder} from text.
     *
     * @param strings Text
     * @return This {@link LSystemBuilder}
     */
    @Override
    public LSystemBuilder configureFromText(String[] strings) {
        for (String string : strings) {
            if (string.equals("")) {
                continue;
            }

            String[] parts = string.split("\\s+");

            switch (parts[0].toLowerCase()) {
                case "origin" -> {
                    if (parts.length != 3) {
                        throw new IllegalArgumentException("Origin should have 2 arguments!");
                    }
                    try {
                        origin = new Vector2D(Double.parseDouble(parts[1]), Double.parseDouble(parts[2]));
                    } catch (NumberFormatException ex) {
                        throw new IllegalArgumentException("Origin expects double arguments!");
                    }
                }
                case "angle" -> {
                    if (parts.length != 2) {
                        throw new IllegalArgumentException("Angle should have 1 argument!");
                    }
                    try {
                        angle = Double.parseDouble(parts[1]) * Math.PI / 180;
                    } catch (NumberFormatException ex) {
                        throw new IllegalArgumentException("Angle expects double argument!");
                    }
                }
                case "unitlength" -> {
                    if (parts.length != 2) {
                        throw new IllegalArgumentException("Unit length should have 1 argument!");
                    }
                    try {
                        unitLength = Double.parseDouble(parts[1]);
                    } catch (NumberFormatException ex) {
                        throw new IllegalArgumentException("Unit length expects double argument!");
                    }
                }
                case "unitlengthdegreescaler" -> {
                    String[] argumentsArray = Arrays.copyOfRange(parts, 1, parts.length);
                    String arguments = String.join("", argumentsArray);
                    if (arguments.contains("/")) {
                        String[] fraction = arguments.split("/");
                        try {
                            unitLengthDegreeScaler = Double.parseDouble(fraction[0]) / Double.parseDouble(fraction[1]);
                        } catch (NumberFormatException ex) {
                            throw new IllegalArgumentException("Unit length degree scaler expects double arguments!");
                        }
                    } else {
                        try {
                            unitLengthDegreeScaler = Double.parseDouble(arguments);
                        } catch (NumberFormatException ex) {
                            throw new IllegalArgumentException("Unit length degree scaler expects double arguments!");
                        }
                    }
                }
                case "command" -> {
                    if (parts[1].length() != 1) {
                        throw new IllegalArgumentException("Command must have symbol as the first argument!");
                    }
                    char commandChar = parts[1].charAt(0);
                    if (commands.get(commandChar) != null) {
                        throw new IllegalArgumentException("Command character " + commandChar + " already defined in scope!");
                    }
                    String[] commandArray = Arrays.copyOfRange(parts, 2, parts.length);
                    String command = String.join(" ", commandArray);
                    registerCommand(commandChar, command);
                }
                case "axiom" -> {
                    if (parts.length != 2) {
                        throw new IllegalArgumentException("Axiom should have 1 argument!");
                    }
                    axiom = parts[1];
                }
                case "production" -> {
                    if (parts.length != 3) {
                        throw new IllegalArgumentException("Production should have 2 arguments!");
                    }
                    if (parts[1].length() != 1) {
                        throw new IllegalArgumentException("Production must have symbol as the first argument!");
                    }
                    char productionChar = parts[1].charAt(0);
                    if (registeredProductions.get(productionChar) != null) {
                        throw new IllegalArgumentException("Production character " + productionChar + " already defined in scope!");
                    }
                    registeredProductions.put(productionChar, parts[2]);
                }
                default -> {
                    throw new IllegalArgumentException("Invalid text row: " + string);
                }
            }
        }

        return this;
    }

    /**
     * Build the {@link LSystem}.
     *
     * @return Built {@link LSystem}
     */
    @Override
    public LSystem build() {

        return new LSystem() {

            @Override
            public String generate(int i) {
                StringBuilder sequence = new StringBuilder(axiom);

                for (int j = 0; j < i; j++) {
                    for (int z = 0; z < sequence.length(); z++) {
                        String sequencePart = registeredProductions.get(sequence.charAt(z));
                        if (sequencePart != null) {
                            sequence.deleteCharAt(z);
                            sequence.insert(z, sequencePart);
                            z += sequencePart.length() - 1;
                        }
                    }
                }

                return sequence.toString();
            }

            @Override
            public void draw(int i, Painter painter) {
                Context context = new Context();

                TurtleState turtleState = new TurtleState(origin, new Vector2D(1, 0).rotated(angle), DEFAULT_COLOR,
                        unitLength * Math.pow(unitLengthDegreeScaler, i));
                context.pushState(turtleState);

                String sequence = generate(i);
                for (char c : sequence.toCharArray()) {
                    Command command = commands.get(c);
                    if (command != null) {
                        command.execute(context, painter);
                    }
                }
            }
        };
    }
}
