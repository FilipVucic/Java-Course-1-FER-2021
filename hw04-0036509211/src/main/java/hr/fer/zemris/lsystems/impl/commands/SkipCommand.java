package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.oprpp1.math.Vector2D;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Class which represents command for moving the turtle for given step without drawing the line.
 * It implements {@link Command} interface.
 *
 * @author Filip Vucic
 */
public class SkipCommand implements Command {

    /**
     * Turtle moving step.
     */
    private final double step;

    /**
     * Create new {@link SkipCommand} with given turtle moving step.
     *
     * @param step Turtle moving step
     */
    public SkipCommand(double step) {
        this.step = step;
    }

    @Override
    public void execute(Context ctx, Painter painter) {
        TurtleState currentState = ctx.getCurrentState();

        Vector2D startPosition = currentState.getTurtlePosition();
        Vector2D endPosition = startPosition.added(currentState.getTurtleDirection().scaled(currentState.getOffset() * step));

        currentState.setTurtlePosition(endPosition);
    }
}
