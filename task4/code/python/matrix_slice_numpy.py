from pathlib import Path

import numpy as np


OUTPUT_FILE = Path(__file__).resolve().parents[2] / "output" / "numpy_slice.csv"


def read_positive_int(prompt):
    while True:
        try:
            value = int(input(prompt))
            if value > 0:
                return value
            print("Please enter a positive integer.")
        except ValueError:
            print("Please enter a valid integer.")


def read_slice_index(prompt, minimum, maximum):
    while True:
        try:
            value = int(input(prompt))
            if minimum <= value <= maximum:
                return value
            print(f"Please enter a value from {minimum} to {maximum}.")
        except ValueError:
            print("Please enter a valid integer.")


def read_matrix(rows, cols):
    print("Enter values for the matrix:")
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


def read_slice_range(size, label):
    while True:
        start = read_slice_index(f"{label} start index (inclusive): ", 0, size)
        end = read_slice_index(f"{label} end index (exclusive): ", 0, size)

        if start < end:
            return start, end

        print("Start index must be smaller than end index.")


def print_matrix(matrix):
    for row in matrix:
        print(" ".join(f"{value:g}" for value in row))


def save_matrix(matrix, path):
    path.parent.mkdir(parents=True, exist_ok=True)
    np.savetxt(path, matrix, delimiter=",", fmt="%g")


def main():
    print("2D matrix slicing in NumPy")

    rows = read_positive_int("Rows of matrix: ")
    cols = read_positive_int("Columns of matrix: ")
    matrix = read_matrix(rows, cols)

    row_start, row_end = read_slice_range(rows, "Row")
    col_start, col_end = read_slice_range(cols, "Column")

    result = matrix[row_start:row_end, col_start:col_end]

    print("Sliced result:")
    print_matrix(result)

    save_matrix(result, OUTPUT_FILE)
    print(f"Saved result to {OUTPUT_FILE}")


if __name__ == "__main__":
    main()
