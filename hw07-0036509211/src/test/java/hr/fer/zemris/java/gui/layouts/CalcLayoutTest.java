package hr.fer.zemris.java.gui.layouts;

import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class CalcLayoutTest {

    @Test
    void addLayoutComponent() {
        JPanel p = new JPanel(new CalcLayout(4));
        p.setBorder(BorderFactory.createLineBorder(Color.BLUE, 50));
        p.add(new JLabel("x"), "1,1");
        assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("x"), "0,1"));
        assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("x"), "1,0"));
        assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("x"), "6,1"));
        assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("x"), "1,8"));
        assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("x"), "1,2"));
        assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("x"), "1,1"));



    }

    @Test
    void preferredSize() {
        JPanel p = new JPanel(new CalcLayout(2));
        JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(10,30));
        JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(20,15));
        p.add(l1, new RCPosition(2,2));
        p.add(l2, new RCPosition(3,3));
        Dimension dim = p.getPreferredSize();
        assertEquals(152, dim.width);
        assertEquals(158, dim.height);
    }

    @Test
    void preferredSize2() {
        JPanel p = new JPanel(new CalcLayout(2));
        JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(108,15));
        JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(16,30));
        p.add(l1, new RCPosition(1,1));
        p.add(l2, new RCPosition(3,3));
        Dimension dim = p.getPreferredSize();
        assertEquals(152, dim.width);
        assertEquals(158, dim.height);
    }
}