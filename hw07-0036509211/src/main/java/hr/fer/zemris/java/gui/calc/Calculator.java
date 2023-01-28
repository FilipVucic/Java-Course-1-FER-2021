package hr.fer.zemris.java.gui.calc;

import hr.fer.zemris.java.gui.calc.model.CalcModelImpl;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Stack;
import java.util.function.DoubleBinaryOperator;

/**
 * Calculator implementation.
 *
 * @author Filip Vucic
 */
public class Calculator extends JFrame {

    /**
     * {@link Calculator} stack.
     */
    private final Stack<Double> calcStack = new Stack<>();

    /**
     * {@link hr.fer.zemris.java.gui.calc.model.CalcModel} implementation.
     */
    private CalcModelImpl calcModel;

    /**
     * Container.
     */
    private Container cp;

    /**
     * Create new {@link Calculator}.
     */
    public Calculator() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Java Calculator v1.0");
        initGUI();
        pack();
    }

    /**
     * Main program.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Calculator().setVisible(true);
        });
    }

    /**
     * Initialize GUI.
     */
    private void initGUI() {
        cp = getContentPane();
        cp.setLayout(new CalcLayout(5));
        calcModel = new CalcModelImpl();
        JLabel display = new JLabel(calcModel.toString(), SwingConstants.RIGHT);
        display.setBackground(Color.YELLOW);
        display.setOpaque(true);
        display.setFont(display.getFont().deriveFont(30f));
        display.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        calcModel.addCalcValueListener(model -> display.setText(model.toString()));
        cp.add(display, "1,1");

        addDigits();
        addInverseFunctionButtons();
        addBinaryButtons();
        addOtherButtons();
    }

    /**
     * Add left buttons.
     */
    private void addOtherButtons() {
        JButton reciprocal = new JButton("1/x");
        reciprocal.addActionListener(e -> calcModel.setValue(1 / calcModel.getValue()));
        cp.add(reciprocal, "2,1");

        JButton equals = new JButton("=");
        equals.addActionListener(e -> {
            calculateBinaryAndSetOperation(null);
            calcModel.clearActiveOperand();
        });
        cp.add(equals, "1,6");

        JButton clr = new JButton("clr");
        clr.addActionListener(e -> calcModel.clear());
        cp.add(clr, "1,7");

        JButton res = new JButton("res");
        res.addActionListener(e -> calcModel.clearAll());
        cp.add(res, "2,7");

        JButton push = new JButton("push");
        push.addActionListener(e -> calcStack.push(calcModel.getValue()));
        cp.add(push, "3,7");

        JButton pop = new JButton("pop");
        pop.addActionListener(e -> calcModel.setValue(calcStack.pop()));
        cp.add(pop, "4,7");

        JButton point = new JButton(".");
        point.addActionListener(e -> calcModel.insertDecimalPoint());
        cp.add(point, "5,5");

        JButton swapSign = new JButton("+/-");
        swapSign.addActionListener(e -> calcModel.swapSign());
        cp.add(swapSign, "5, 4");

    }

    /**
     * Add binary operation buttons.
     */
    private void addBinaryButtons() {
        JButton[] binaryButtons = new JButton[4];
        binaryButtons[0] = new JButton("+");
        binaryButtons[0].addActionListener(e -> calculateBinaryAndSetOperation(Double::sum));
        binaryButtons[1] = new JButton("-");
        binaryButtons[1].addActionListener(e -> calculateBinaryAndSetOperation((x, y) -> x - y));
        binaryButtons[2] = new JButton("*");
        binaryButtons[2].addActionListener(e -> calculateBinaryAndSetOperation((x, y) -> x * y));
        binaryButtons[3] = new JButton("/");
        binaryButtons[3].addActionListener(e -> calculateBinaryAndSetOperation((x, y) -> x / y));

        for (int i = 0; i < binaryButtons.length; i++) {
            cp.add(binaryButtons[i], new RCPosition(5 - i, 6));
        }
    }

    /**
     * Add buttons with inverse functions.
     */
    private void addInverseFunctionButtons() {
        InverseFunctionButton[] inverseFunctionButtons = new InverseFunctionButton[7];

        inverseFunctionButtons[0] = new InverseFunctionButton("log", "10^x", e -> calcModel.setValue(Math.log10(calcModel.getValue())),
                e -> calcModel.setValue(Math.pow(10, calcModel.getValue())));
        inverseFunctionButtons[1] = new InverseFunctionButton("ln", "e^x", e -> calcModel.setValue(Math.log(calcModel.getValue())),
                e -> calcModel.setValue(Math.pow(Math.E, calcModel.getValue())));
        inverseFunctionButtons[2] = new InverseFunctionButton("x^n", "x^(1/n)", e -> calculateBinaryAndSetOperation(Math::pow),
                e -> calculateBinaryAndSetOperation((x, n) -> Math.pow(x, 1 / n)));
        inverseFunctionButtons[3] = new InverseFunctionButton("sin", "arcsin", e -> calcModel.setValue(Math.sin(calcModel.getValue())),
                e -> calcModel.setValue(Math.asin(calcModel.getValue())));
        inverseFunctionButtons[4] = new InverseFunctionButton("cos", "arccos", e -> calcModel.setValue(Math.cos(calcModel.getValue())),
                e -> calcModel.setValue(Math.acos(calcModel.getValue())));
        inverseFunctionButtons[5] = new InverseFunctionButton("tan", "arctan", e -> calcModel.setValue(Math.tan(calcModel.getValue())),
                e -> calcModel.setValue(Math.asin(calcModel.getValue())));
        inverseFunctionButtons[6] = new InverseFunctionButton("ctg", "arcctg", e -> calcModel.setValue(1 / Math.tan(calcModel.getValue())),
                e -> calcModel.setValue(Math.atan(1 / calcModel.getValue())));
        JCheckBox inverse = new JCheckBox("Inv");
        inverse.addItemListener(e -> {
            for (InverseFunctionButton button : inverseFunctionButtons) {
                button.setActionListenerAndText(inverse.isSelected());
            }
        });
        cp.add(inverse, "5,7");

        for (int i = 1; i < inverseFunctionButtons.length + 1; i++) {
            InverseFunctionButton inverseFunctionButton = inverseFunctionButtons[i - 1];
            cp.add(inverseFunctionButton, new RCPosition(2 + i % 4, 1 + i / 4));
        }
    }

    /**
     * Add digits.
     */
    private void addDigits() {
        for (int i = 0; i < 10; i++) {
            JButton digitButton = new JButton(String.valueOf(i));
            int finalI = i;
            digitButton.addActionListener(e -> calcModel.insertDigit(finalI));
            digitButton.setFont(digitButton.getFont().deriveFont(30f));
            if (i == 0) {
                cp.add(digitButton, "5,3");
            } else {
                cp.add(digitButton, new RCPosition(4 - (i - 1) / 3, (i - 1) % 3 + 3));
            }
        }
    }

    /**
     * Calculate binary operation and set new.
     *
     * @param operation New {@link DoubleBinaryOperator}
     */
    private void calculateBinaryAndSetOperation(DoubleBinaryOperator operation) {
        DoubleBinaryOperator pendingOperation = calcModel.getPendingBinaryOperation();
        if (pendingOperation != null) {
            calcModel.setValue(pendingOperation.applyAsDouble(calcModel.getActiveOperand(), calcModel.getValue()));
        }
        calcModel.setActiveOperand(calcModel.getValue());
        calcModel.setPendingBinaryOperation(operation);
    }

    /**
     * Button with inverse functions.
     */
    private static class InverseFunctionButton extends JButton {

        /**
         * Standard {@link ActionListener}.
         */
        private final ActionListener actionListener;

        /**
         * Inverse {@link ActionListener}.
         */
        private final ActionListener inverseActionListener;

        /**
         * Standard button text.
         */
        private final String text;

        /**
         * Inverse button text.
         */
        private final String inverseText;

        /**
         * Create new {@link InverseFunctionButton} with given standard and inverse text, as standard and inversed {@link ActionListener}.
         *
         * @param text                  Button text
         * @param inverseText           Inverse button text
         * @param actionListener        Button {@link ActionListener}
         * @param inverseActionListener Inverse button {@link ActionListener}
         */
        public InverseFunctionButton(String text, String inverseText, ActionListener actionListener, ActionListener inverseActionListener) {
            this.text = text;
            this.inverseText = inverseText;
            this.actionListener = actionListener;
            this.inverseActionListener = inverseActionListener;
            addActionListener(actionListener);
            setText(text);
        }

        /**
         * Set {@link ActionListener} and text of the button.
         *
         * @param inverse True if inverse, false if standard
         */
        public void setActionListenerAndText(boolean inverse) {
            addActionListener(inverse ? inverseActionListener : actionListener);
            setText(inverse ? inverseText : text);
        }

    }
}
