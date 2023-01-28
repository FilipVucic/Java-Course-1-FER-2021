package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.oprpp1.math.Vector2D;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Class which represents command for moving the turtle for given step and drawing the line.
 * It implements {@link Command} interface.
 *
 * @author Filip Vucic
 */
public class DrawCommand implements Command {

    /**
     * Turtle drawing step.
     */
    private final double step;

    /**
     * Create new {@link DrawCommand} with given turtle drawing step.
     *
     * @param step Turtle drawing step
     */
    public DrawCommand(double step) {
        this.step = step;
    }

    @Override
    public void execute(Context ctx, Painter painter) {
        TurtleState currentState = ctx.getCurrentState();

        Vector2D startPosition = currentState.getTurtlePosition();
        Vector2D endPosition = startPosition.added(currentState.getTurtleDirection().scaled(currentState.getOffset() * step));

        painter.drawLine(startPosition.getX(), startPosition.getY(), endPosition.getX(), endPosition.getY(),
                currentState.getTurtleDrawingColor(), 1f);

        currentState.setTurtlePosition(endPosition);
    }
}
