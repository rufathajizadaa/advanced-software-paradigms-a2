import numpy as np


def read_positive_int(prompt):
    while True:
        try:
            value = int(input(prompt))
            if value > 0:
                return value
            print("Please enter a positive integer.")
        except ValueError:
            print("Please enter a valid integer.")


def read_matrix(name, rows, cols):
    print(f"Enter values for matrix {name}:")
    values = []

    for i in range(rows):
        while True:
            row_input = input(f"Row {i + 1} ({cols} numbers): ")
            parts = row_input.split()

            if len(parts) != cols:
                print(f"Please enter exactly {cols} numbers.")
                continue

            try:
                row = [float(part) for part in parts]
                values.append(row)
                break
            except ValueError:
                print("Please enter numbers only.")

    return np.array(values)


def print_matrix(matrix):
    for row in matrix:
        print(" ".join(f"{value:g}" for value in row))


def main():
    print("Matrix multiplication in NumPy")

    rows_a = read_positive_int("Rows of matrix A: ")
    cols_a = read_positive_int("Columns of matrix A: ")
    rows_b = read_positive_int("Rows of matrix B: ")
    cols_b = read_positive_int("Columns of matrix B: ")

    if cols_a != rows_b:
        print("Matrix multiplication is not possible. The number of columns in matrix A must equal the number of rows in matrix B.")
        return

    matrix_a = read_matrix("A", rows_a, cols_a)
    matrix_b = read_matrix("B", rows_b, cols_b)

    result = np.matmul(matrix_a, matrix_b)

    print("Result:")
    print_matrix(result)


if __name__ == "__main__":
    main()
