package hr.fer.zemris.java.gui.prim;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Prim list model.
 *
 * @author Filip Vucic
 */
public class PrimListModel implements ListModel<Integer> {

    /**
     * {@link PrimListModel} listeners.
     */
    private final List<ListDataListener> listeners;

    /**
     * {@link PrimListModel} storage.
     */
    private final List<Integer> primeNumbers;

    /**
     * Create new {@link PrimListModel}.
     */
    public PrimListModel() {
        primeNumbers = new ArrayList<>();
        primeNumbers.add(1);
        listeners = new ArrayList<>();
    }

    /**
     * Check if the number is prime.
     *
     * @param n Number
     * @return True if prime, false otherwise
     */
    public static boolean isPrime(int n) {
        for (int i = 2; i < n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int getSize() {
        return primeNumbers.size();
    }

    @Override
    public Integer getElementAt(int index) {
        return primeNumbers.get(index);
    }

    @Override
    public void addListDataListener(ListDataListener l) {
        listeners.add(l);
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
        listeners.remove(l);
    }

    /**
     * Get next prime.
     */
    public void next() {
        int n = primeNumbers.get(primeNumbers.size() - 1) + 1;
        while (!isPrime(n)) {
            n++;
        }
        primeNumbers.add(n);
        ListDataEvent e = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, getSize(), getSize());
        listeners.forEach(l -> l.intervalAdded(e));
    }
}
