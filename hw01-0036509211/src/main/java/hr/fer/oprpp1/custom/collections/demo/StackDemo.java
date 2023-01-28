package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.EmptyStackException;
import hr.fer.oprpp1.custom.collections.ObjectStack;

/**
 * @author Filip Vucic
 * This should be command-line application which accepts a single command-line argument: expression
 * which should be evaluated. Expression must be in postfix representation. When starting program from
 * console, you will enclose whole expression into quotation marks, so that your program always gets
 * just one argument (args.length should be 1 and the args[0] should be the whole expression).
 */
public class StackDemo {

    /**
     * Main method.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Number of arguments should be 1.");
            System.exit(1);
        }

        String[] expressionElements = args[0].split("\\s+");
        ObjectStack calculationStack = new ObjectStack();

        for (String element : expressionElements) {
            try {
                calculationStack.push(Integer.parseInt(element));
            } catch (NumberFormatException ex) {
                try {
                    int secondNumber = (int) calculationStack.pop();
                    int firstNumber = (int) calculationStack.pop();

                    calculationStack.push(calculateExpression(firstNumber, secondNumber, element));
                } catch (EmptyStackException exc) {
                    System.err.println("Invalid expression!");
                    System.exit(1);
                }
            }
        }

        if (calculationStack.size() != 1) {
            System.err.println("Expression error!");
        } else {
            System.out.println("Expression evaluates to " + calculationStack.pop() + ".");
        }
    }

    /**
     * Private method used for calculating the expression.
     *
     * @param firstNumber  First number from expression
     * @param secondNumber Second number from expression
     * @param operator     Operator
     * @return Calculated expression
     */
    private static int calculateExpression(int firstNumber, int secondNumber, String operator) {
        switch (operator) {
            case "+":
                return firstNumber + secondNumber;
            case "-":
                return firstNumber - secondNumber;
            case "/":
                if (secondNumber == 0) {
                    System.err.println("Division by zero!");
                    System.exit(1);
                }
                return firstNumber / secondNumber;
            case "*":
                return firstNumber * secondNumber;
            case "%":
                return firstNumber % secondNumber;
            default:
                System.err.println("Invalid operator!");
                System.exit(1);
                return -1;
        }
    }


}
