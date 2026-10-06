import java.util.Scanner;

public class MatrixMultiplier {
    public static double[][] multiply(double[][] a, double[][] b) {
        validateMatrix(a, "A");
        validateMatrix(b, "B");

        int rowsA = a.length;
        int colsA = a[0].length;
        int rowsB = b.length;
        int colsB = b[0].length;

        if (colsA != rowsB) {
            throw new IllegalArgumentException(
                    "Matrix multiplication is not possible. The number of columns in matrix A must equal the number of rows in matrix B."
            );
        }

        double[][] result = new double[rowsA][colsB];

        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                double sum = 0;
                for (int k = 0; k < colsA; k++) {
                    sum += a[i][k] * b[k][j];
                }
                result[i][j] = sum;
            }
        }

        return result;
    }

    private static void validateMatrix(double[][] matrix, String name) {
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            throw new IllegalArgumentException("Matrix " + name + " must not be empty.");
        }

        int columns = matrix[0].length;
        for (double[] row : matrix) {
            if (row == null || row.length != columns) {
                throw new IllegalArgumentException("Matrix " + name + " must be rectangular.");
            }
        }
    }

    private static int readPositiveInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);

            if (!scanner.hasNextInt()) {
                scanner.next();
                System.out.println("Please enter a valid integer.");
                continue;
            }

            int value = scanner.nextInt();
            if (value > 0) {
                return value;
            }

            System.out.println("Please enter a positive integer.");
        }
    }

    private static double[][] readMatrix(Scanner scanner, String name, int rows, int cols) {
        double[][] matrix = new double[rows][cols];

        System.out.println("Enter values for matrix " + name + ":");
        for (int i = 0; i < rows; i++) {
            while (true) {
                System.out.print("Row " + (i + 1) + " (" + cols + " numbers): ");
                String[] parts = scanner.nextLine().trim().split("\\s+");

                if (parts.length != cols || parts[0].isEmpty()) {
                    System.out.println("Please enter exactly " + cols + " numbers.");
                    continue;
                }

                try {
                    for (int j = 0; j < cols; j++) {
                        matrix[i][j] = Double.parseDouble(parts[j]);
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Please enter numbers only.");
                }
            }
        }

        return matrix;
    }

    private static void printMatrix(double[][] matrix) {
        for (double[] row : matrix) {
            for (int j = 0; j < row.length; j++) {
                System.out.print(formatNumber(row[j]));
                if (j < row.length - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    private static String formatNumber(double value) {
        if (value == (long) value) {
            return String.valueOf((long) value);
        }
        return String.valueOf(value);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Matrix multiplication in Java");

        int rowsA = readPositiveInt(scanner, "Rows of matrix A: ");
        int colsA = readPositiveInt(scanner, "Columns of matrix A: ");
        int rowsB = readPositiveInt(scanner, "Rows of matrix B: ");
        int colsB = readPositiveInt(scanner, "Columns of matrix B: ");

        if (colsA != rowsB) {
            System.out.println("Matrix multiplication is not possible. The number of columns in matrix A must equal the number of rows in matrix B.");
            return;
        }

        scanner.nextLine();

        double[][] matrixA = readMatrix(scanner, "A", rowsA, colsA);
        double[][] matrixB = readMatrix(scanner, "B", rowsB, colsB);
        double[][] result = multiply(matrixA, matrixB);

        System.out.println("Result:");
        printMatrix(result);
    }
}
