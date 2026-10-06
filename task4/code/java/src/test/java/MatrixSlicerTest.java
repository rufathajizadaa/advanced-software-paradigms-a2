import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MatrixSlicerTest {
    @Test
    void slicesMiddleRowsAndColumns() {
        double[][] matrix = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };

        double[][] expected = {
                {6, 7},
                {10, 11}
        };

        assertMatrixEquals(expected, MatrixSlicer.slice(matrix, 1, 3, 1, 3));
    }

    @Test
    void slicesSingleRow() {
        double[][] matrix = {
                {1, 2, 3},
                {4, 5, 6}
        };

        double[][] expected = {
                {4, 5, 6}
        };

        assertMatrixEquals(expected, MatrixSlicer.slice(matrix, 1, 2, 0, 3));
    }

    @Test
    void rejectsInvalidRowRange() {
        double[][] matrix = {
                {1, 2},
                {3, 4}
        };

        assertThrows(IllegalArgumentException.class, () -> MatrixSlicer.slice(matrix, 1, 1, 0, 2));
    }

    @Test
    void rejectsInvalidColumnRange() {
        double[][] matrix = {
                {1, 2},
                {3, 4}
        };

        assertThrows(IllegalArgumentException.class, () -> MatrixSlicer.slice(matrix, 0, 2, 2, 3));
    }

    private void assertMatrixEquals(double[][] expected, double[][] actual) {
        assertArrayEquals(expected, actual);
    }
}
