package hr.fer.zemris.java.gui.calc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

/**
 * {@link CalcModel} implementation.
 *
 * @author Filip Vucic
 */
public class CalcModelImpl implements CalcModel {

    /**
     * {@link CalcValueListener} listeners.
     */
    private final List<CalcValueListener> listeners = new ArrayList<>();

    /**
     * Currently pending operation.
     */
    DoubleBinaryOperator pendingOperation = null;

    /**
     * Flag if active operand is set.
     */
    private boolean activeOperandSet;

    /**
     * Active operand.
     */
    private double activeOperand;

    /**
     * Editable flag.
     */
    private boolean editable = true;

    /**
     * Positive number flag.
     */
    private boolean positive = true;

    /**
     * Calculator input.
     */
    private String input = "";

    /**
     * Calculator decimal input.
     */
    private double decimalInput = 0;

    /**
     * Calculator output.
     */
    private String output = null;

    @Override
    public void addCalcValueListener(CalcValueListener l) {
        listeners.add(l);
    }

    @Override
    public void removeCalcValueListener(CalcValueListener l) {
        listeners.remove(l);
    }

    @Override
    public double getValue() {
        return positive ? decimalInput : -decimalInput;
    }

    @Override
    public void setValue(double value) {
        decimalInput = Math.abs(value);
        input = String.valueOf(decimalInput);
        editable = false;
        positive = value >= 0;
        notifyAllListeners();
    }

    @Override
    public boolean isEditable() {
        return editable;
    }

    @Override
    public void clear() {
        output = null;
        input = "";
        decimalInput = 0;
        editable = true;
        notifyAllListeners();
    }

    @Override
    public void clearAll() {
        output = null;
        activeOperandSet = false;
        pendingOperation = null;
        notifyAllListeners();
    }

    @Override
    public void swapSign() throws CalculatorInputException {
        output = null;
        if (!isEditable()) {
            throw new CalculatorInputException("CalcModel is not editable!");
        }
        positive = !positive;
        notifyAllListeners();
    }

    @Override
    public void insertDecimalPoint() throws CalculatorInputException {
        output = null;
        if (!isEditable()) {
            throw new CalculatorInputException("CalcModel is not editable!");
        }
        if (input.equals("")) {
            throw new CalculatorInputException("You can't insert decimal point without number!");
        }
        if (!input.contains(".")) {
            input += ".";
        } else {
            throw new CalculatorInputException("You already have decimal point!");
        }
        notifyAllListeners();
    }

    @Override
    public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
        output = null;
        if (!isEditable()) {
            throw new CalculatorInputException("CalcModel is not editable!");
        }
        if (input.equals("0")) {
            if (digit == 0) {
                return;
            } else {
                input = "";
            }
        }
        String newInput = input + digit;

        double d = Double.parseDouble(newInput);
        if (Double.isFinite(d)) {
            input = newInput;
            decimalInput = d;
        } else {
            throw new CalculatorInputException("Input is not finite!");
        }
        notifyAllListeners();
    }

    @Override
    public boolean isActiveOperandSet() {
        return activeOperandSet;
    }

    @Override
    public double getActiveOperand() throws IllegalStateException {
        if (!activeOperandSet) {
            throw new IllegalStateException("Active operand is not set!");
        }

        return activeOperand;
    }

    @Override
    public void setActiveOperand(double activeOperand) {
        this.activeOperand = activeOperand;
        activeOperandSet = true;
    }

    @Override
    public void clearActiveOperand() {
        activeOperandSet = false;
    }

    @Override
    public DoubleBinaryOperator getPendingBinaryOperation() {
        return pendingOperation;
    }

    @Override
    public void setPendingBinaryOperation(DoubleBinaryOperator op) {
        pendingOperation = op;
        if (op != null) {
            freezeValue(input);
        }
    }

    @Override
    public void freezeValue(String value) {
        input = "";
        output = value;
        editable = true;
    }

    @Override
    public boolean hasFrozenValue() {
        return output != null;
    }

    @Override
    public String toString() {
        if (hasFrozenValue()) {
            return output;
        }

        if (input.equals("")) {
            return positive ? "0" : "-0";
        } else {
            if (Double.isNaN(decimalInput)) {
                input = "NaN";
            } else if (Double.isInfinite(decimalInput)) {
                input = "Infinity";
            }
            return positive ? input : '-' + input;
        }
    }

    /**
     * Notify all listeners that the value was changed.
     */
    private void notifyAllListeners() {
        listeners.forEach(l -> l.valueChanged(this));
    }
}
