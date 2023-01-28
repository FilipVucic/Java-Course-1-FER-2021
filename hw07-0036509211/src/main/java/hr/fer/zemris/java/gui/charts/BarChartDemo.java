package hr.fer.zemris.java.gui.charts;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * {@link BarChart} demo.
 *
 * @author Filip Vucic
 */
public class BarChartDemo extends JFrame {

    /**
     * {@link BarChart} model.
     */
    BarChart barChart;

    /**
     * File path.
     */
    String filePath;

    /**
     * Create new {@link BarChartDemo} with given {@link BarChart} model and file path
     *
     * @param barChart {@link BarChart} model
     * @param filePath Path to the file
     */
    public BarChartDemo(BarChart barChart, String filePath) {
        this.filePath = filePath;
        this.barChart = barChart;
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        initGUI();
        setSize(1000, 700);
    }

    /**
     * Main program.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Argument expected!");
            return;
        }

        Path path = Paths.get(args[0]).toAbsolutePath().normalize();
        BarChart barChart = null;
        try {
            barChart = parseBarChartFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        BarChart finalBarChart = barChart;
        SwingUtilities.invokeLater(() -> {
            new BarChartDemo(finalBarChart, path.toString()).setVisible(true);
        });
    }

    /**
     * Parse given file.
     *
     * @param file File
     * @return Parsed {@link BarChart} model
     * @throws IOException if can't read file
     */
    private static BarChart parseBarChartFile(Path file) throws IOException {
        List<String> lines = Files.readAllLines(file);
        if (lines.size() < 6) {
            throw new IllegalArgumentException("Not enough lines in file.");
        }

        List<XYValue> values = Arrays.stream(lines.get(2).split("\\s+"))
                .map(s -> {
                    String[] stringArray = s.split(",");
                    if (stringArray.length != 2) {
                        throw new IllegalArgumentException("Point must have x and y! " + s);
                    }
                    return new XYValue(Integer.parseInt(stringArray[0]),
                            Integer.parseInt(stringArray[1]));
                }).collect(Collectors.toList());


        return new BarChart(values, lines.get(0), lines.get(1), Integer.parseInt(lines.get(3)),
                Integer.parseInt(lines.get(4)), Integer.parseInt(lines.get(5)));
    }

    /**
     * Initialize GUI.
     */
    private void initGUI() {
        Container cp = getContentPane();
        cp.add(new BarChartComponent(barChart));
        cp.add(new JLabel(filePath, SwingConstants.CENTER), BorderLayout.PAGE_START);
    }
}
