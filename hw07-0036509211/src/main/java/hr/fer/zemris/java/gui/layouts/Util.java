package hr.fer.zemris.java.gui.layouts;

/**
 * Utility class.
 *
 * @author Filip Vucic
 */
public class Util {

    /**
     * Method used for uniformly distributing length in the array.
     *
     * @param length             Length
     * @param numberOfComponents Number of components
     * @return Balanced array
     */
    public static int[] balanceDimension(int length, int numberOfComponents) {
        int componentLength = length / numberOfComponents;
        int leftOver = length - numberOfComponents * componentLength;

        int[] componentsLengthArray = new int[numberOfComponents];
        int leftOverCopy = leftOver;

        if (leftOver > numberOfComponents / 2) {
            leftOver = numberOfComponents - leftOver;
            leftOverCopy = numberOfComponents - leftOverCopy;
            int brojac = 0;
            for (int i = numberOfComponents / 2; i >= 0; i--) {
                if (leftOver % 2 == 0) {
                    if (brojac % 2 == 1 && leftOverCopy > 0) {
                        componentsLengthArray[i] = componentLength;
                        leftOverCopy--;
                        if (leftOverCopy > 0 && i != numberOfComponents - 1 - i) {
                            componentsLengthArray[numberOfComponents - 1 - i] = componentLength;
                            leftOverCopy--;
                        }
                    } else {
                        componentsLengthArray[i] = componentLength + 1;
                        componentsLengthArray[numberOfComponents - 1 - i] = componentLength + 1;
                    }
                } else {
                    if (brojac % 2 == 0 && leftOverCopy > 0) {
                        componentsLengthArray[i] = componentLength;
                        leftOverCopy--;
                        if (leftOverCopy > 0 && i != numberOfComponents - 1 - i) {
                            componentsLengthArray[numberOfComponents - 1 - i] = componentLength;
                            leftOverCopy--;
                        }
                    } else {
                        componentsLengthArray[i] = componentLength + 1;
                        componentsLengthArray[numberOfComponents - 1 - i] = componentLength + 1;
                    }
                }
                brojac++;
            }
        } else {
            int brojac = 0;
            for (int i = numberOfComponents / 2; i >= 0; i--) {
                if (leftOver % 2 == 0) {
                    if (brojac % 2 == 1 && leftOverCopy > 0) {
                        componentsLengthArray[i] = componentLength + 1;
                        leftOverCopy--;
                        if (leftOverCopy > 0 && i != numberOfComponents - 1 - i) {
                            componentsLengthArray[numberOfComponents - 1 - i] = componentLength + 1;
                            leftOverCopy--;
                        }
                    } else {
                        componentsLengthArray[i] = componentLength;
                        componentsLengthArray[numberOfComponents - 1 - i] = componentLength;
                    }
                } else {
                    if (brojac % 2 == 0 && leftOverCopy > 0) {
                        componentsLengthArray[i] = componentLength + 1;
                        leftOverCopy--;
                        if (leftOverCopy > 0 && i != numberOfComponents - 1 - i) {
                            componentsLengthArray[numberOfComponents - 1 - i] = componentLength + 1;
                            leftOverCopy--;
                        }
                    } else {
                        componentsLengthArray[i] = componentLength;
                        componentsLengthArray[numberOfComponents - 1 - i] = componentLength;
                    }
                }
                brojac++;

            }
        }

        return componentsLengthArray;
    }
}
