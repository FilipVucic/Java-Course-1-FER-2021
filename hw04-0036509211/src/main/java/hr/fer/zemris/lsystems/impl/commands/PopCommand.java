package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Class which represents command for popping one {@link hr.fer.zemris.lsystems.impl.TurtleState}
 * from the {@link Context}.
 * It implements {@link Command} interface.
 *
 * @author Filip Vucic
 */
public class PopCommand implements Command {

    @Override
    public void execute(Context ctx, Painter painter) {
        ctx.popState();
    }
}
