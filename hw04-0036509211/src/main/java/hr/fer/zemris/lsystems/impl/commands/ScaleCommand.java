package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Class which represents command for scaling the turtle offset.
 * It implements {@link Command} interface.
 *
 * @author Filip Vucic
 */
public class ScaleCommand implements Command {

    /**
     * Factor of turtle offset scaling.
     */
    private final double factor;

    /**
     * Create new {@link ScaleCommand} with given turtle offset scaling factor.
     *
     * @param factor Turtle offset scaling factor
     */
    public ScaleCommand(double factor) {
        this.factor = factor;
    }

    @Override
    public void execute(Context ctx, Painter painter) {
        ctx.getCurrentState().setOffset(ctx.getCurrentState().getOffset() * factor);
    }
}
