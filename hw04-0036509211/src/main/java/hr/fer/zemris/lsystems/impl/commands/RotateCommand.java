package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Class which represents command for rotating the turtle for given angle.
 * It implements {@link Command} interface.
 *
 * @author Filip Vucic
 */
public class RotateCommand implements Command {

    /**
     * Turtle rotating angle.
     */
    private final double angle;

    /**
     * Create new {@link RotateCommand} with given angle.
     *
     * @param angle Turtle rotating angle
     */
    public RotateCommand(double angle) {
        this.angle = angle;
    }

    @Override
    public void execute(Context ctx, Painter painter) {
        ctx.getCurrentState().getTurtleDirection().rotate(angle);
    }
}
