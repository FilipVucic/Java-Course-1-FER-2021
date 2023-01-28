package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

import java.awt.*;

/**
 * Class which represents command for setting turtle drawing color.
 * It implements {@link Command} interface.
 *
 * @author Filip Vucic
 */
public class ColorCommand implements Command {

    /**
     * Turtle drawing color.
     */
    private final Color color;

    /**
     * Create new {@link ColorCommand} with given color.
     *
     * @param color Turtle drawing color
     */
    public ColorCommand(Color color) {
        this.color = color;
    }

    @Override
    public void execute(Context ctx, Painter painter) {
        ctx.getCurrentState().setTurtleDrawingColor(color);
    }
}
