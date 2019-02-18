package render;

//import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

//FIXME Caveat: currently all tests involve 3 dimensional vectors;
// class should theoretically work with n-dimensional vectors
class VectorFTest {
    private static Random rand = new Random();
    private VectorF a;
    private VectorF b;
    private float[] aComp;
    private float[] bComp;

    @BeforeEach
    void setUp() {
        a = new VectorF(3, rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
        b = new VectorF(3, rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
        aComp = a.getComponents();
        bComp = b.getComponents();
    }

//    @AfterEach
//    void tearDown() {
//    }

    @Test
    void equals() {
        assertNotEquals(a, b);
        assertEquals(b, new VectorF(3, b.getComponents()));
    }

    @Test
    void add() {
        var result = a.add(b);
        var resultComp = new float[3];

        for (int i = 0; i < resultComp.length; i++) {
            resultComp[i] = aComp[i] + bComp[i];
        }

        assertEquals(result, new VectorF(3, resultComp));
    }

    @Test
    void scalarMultiply() {
        var scalar = -3;
        var resultComp = aComp;

        for (int i = 0; i < resultComp.length; i++) {
            resultComp[i] *= scalar;
        }
    }

    @Test
    void subtract() {
        var result = a.subtract(b);
        var rComp = new float[3];

        for (int i = 0; i < 3; i++) {
            rComp[i] = aComp[i] - bComp[i];
        }

        assertEquals(result, new VectorF(3, rComp));
    }

    @Test
    void dotProduct() {
        float result = a.dotProduct(b);
        float expected =
                (aComp[0] * bComp[0]) + (aComp[1] * bComp[1]) + (aComp[2] * bComp[2]);
        assertEquals(result, expected);
    }

    @Test
    void magnitude() {
        var result = a.magnitude();
        var expected = Math.sqrt(
                (aComp[0] * aComp[0]) + (aComp[1] * aComp[1]) + (aComp[2] * aComp[2]));
       assertEquals(Math.round(result), Math.round(expected));
    }

    @Test
    void compareTo() {
        var aMag = a.magnitude();
        var bMag = b.magnitude();

        var expected = aMag > bMag ? 1 : -1;
        assertEquals(a.compareTo(b), expected);
    }

}