import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class MatrixSlicer {
    private static final Path OUTPUT_FILE = Path.of("..", "..", "output", "java_slice.csv").normalize();

    public static double[][] slice(double[][] matrix, int rowStart, int rowEnd, int colStart, int colEnd) {
        validateMatrix(matrix);

        if (rowStart < 0 || rowEnd > matrix.length || rowStart >= rowEnd) {
            throw new IllegalArgumentException("Invalid row slice range.");
        }

        if (colStart < 0 || colEnd > matrix[0].length || colStart >= colEnd) {
            throw new IllegalArgumentException("Invalid column slice range.");
        }

        double[][] result = new double[rowEnd - rowStart][colEnd - colStart];

        for (int i = rowStart; i < rowEnd; i++) {
            for (int j = colStart; j < colEnd; j++) {
                result[i - rowStart][j - colStart] = matrix[i][j];
            }
        }

        return result;
    }

    private static void validateMatrix(double[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            throw new IllegalArgumentException("Matrix must not be empty.");
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

    private static int readSliceIndex(Scanner scanner, String prompt, int minimum, int maximum) {
        while (true) {
            System.out.print(prompt);

            if (!scanner.hasNextInt()) {
                scanner.next();
                System.out.println("Please enter a valid integer.");
                continue;
            }

            int value = scanner.nextInt();
            if (value >= minimum && value <= maximum) {
                return value;
            }

            System.out.println("Please enter a value from " + minimum + " to " + maximum + ".");
        }
    }

    private static int[] readSliceRange(Scanner scanner, int size, String label) {
        while (true) {
            int start = readSliceIndex(scanner, label + " start index (inclusive): ", 0, size);
            int end = readSliceIndex(scanner, label + " end index (exclusive): ", 0, size);

            if (start < end) {
                return new int[] {start, end};
            }

            System.out.println("Start index must be smaller than end index.");
        }
    }

    private static double[][] readMatrix(Scanner scanner, int rows, int cols) {
        double[][] matrix = new double[rows][cols];

        System.out.println("Enter values for the matrix:");
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

    private static void saveMatrix(double[][] matrix, Path path) throws IOException {
        Files.createDirectories(path.getParent());

        StringBuilder content = new StringBuilder();
        for (double[] row : matrix) {
            for (int j = 0; j < row.length; j++) {
                content.append(formatNumber(row[j]));
                if (j < row.length - 1) {
                    content.append(",");
                }
            }
            content.append(System.lineSeparator());
        }

        Files.writeString(path, content.toString());
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("2D matrix slicing in Java");

        int rows = readPositiveInt(scanner, "Rows of matrix: ");
        int cols = readPositiveInt(scanner, "Columns of matrix: ");

        scanner.nextLine();
        double[][] matrix = readMatrix(scanner, rows, cols);

        int[] rowRange = readSliceRange(scanner, rows, "Row");
        int[] colRange = readSliceRange(scanner, cols, "Column");

        double[][] result = slice(matrix, rowRange[0], rowRange[1], colRange[0], colRange[1]);

        System.out.println("Sliced result:");
        printMatrix(result);

        saveMatrix(result, OUTPUT_FILE);
        System.out.println("Saved result to " + OUTPUT_FILE);
    }
}
