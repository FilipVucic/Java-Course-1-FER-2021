package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * Class which represents turtle command.
 *
 * @author Filip Vucic
 */
public interface Command {

    /**
     * Execute the turtle command.
     *
     * @param ctx     Context
     * @param painter Painter
     */
    void execute(Context ctx, Painter painter);
}
