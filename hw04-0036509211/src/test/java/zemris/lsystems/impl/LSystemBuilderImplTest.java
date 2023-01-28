// 2018/2019 opjj test modified, @author Filip Vucic
package zemris.lsystems.impl;

import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LSystemBuilderImplTest {

    @Test
    void build() {
        LSystemBuilderImpl impl = new LSystemBuilderImpl();
        impl.setAxiom("F");

        impl.registerProduction('F', "F+F--F+F");

        assertEquals("F", impl.build().generate(0));
        assertEquals("F+F--F+F", impl.build().generate(1));
        assertEquals("F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F", impl.build().generate(2));
    }
}