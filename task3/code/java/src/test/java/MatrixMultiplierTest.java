import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MatrixMultiplierTest {
    @Test
    void multipliesRectangularMatrices() {
        double[][] a = {
                {1, 2},
                {3, 4},
                {4, 5}
        };
        double[][] b = {
                {1, 2, 3},
                {4, 5, 6}
        };

        double[][] expected = {
                {9, 12, 15},
                {19, 26, 33},
                {24, 33, 42}
        };

        assertMatrixEquals(expected, MatrixMultiplier.multiply(a, b));
    }

    @Test
    void multipliesSquareMatrices() {
        double[][] a = {
                {1, 2},
                {3, 4}
        };
        double[][] b = {
                {5, 6},
                {7, 8}
        };

        double[][] expected = {
                {19, 22},
                {43, 50}
        };

        assertMatrixEquals(expected, MatrixMultiplier.multiply(a, b));
    }

    @Test
    void rejectsIncompatibleMatrices() {
        double[][] a = {
                {1, 2, 3},
                {4, 5, 6}
        };
        double[][] b = {
                {1, 2},
                {3, 4}
        };

        assertThrows(IllegalArgumentException.class, () -> MatrixMultiplier.multiply(a, b));
    }

    @Test
    void rejectsEmptyMatrix() {
        double[][] empty = {};
        double[][] b = {
                {1}
        };

        assertThrows(IllegalArgumentException.class, () -> MatrixMultiplier.multiply(empty, b));
    }

    @Test
    void rejectsNonRectangularMatrix() {
        double[][] a = {
                {1, 2},
                {3}
        };
        double[][] b = {
                {1},
                {2}
        };

        assertThrows(IllegalArgumentException.class, () -> MatrixMultiplier.multiply(a, b));
    }

    private void assertMatrixEquals(double[][] expected, double[][] actual) {
        assertArrayEquals(expected, actual);
    }
}
