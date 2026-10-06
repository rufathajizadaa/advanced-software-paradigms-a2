# Task 3: Matrix Multiplication

## Goal

The goal of this task is to implement matrix multiplication in two ways:

- Python with NumPy arrays
- Java as the C-like language

I picked Java because I have been working with it, so it was the most comfortable C-like option for me. The programs are written for general matrix sizes. The user enters the number of rows and columns, and the program checks if multiplication is possible.

## Files

```text
task3/code/python/matrix_multiply_numpy.py
task3/code/java/src/main/java/MatrixMultiplier.java
task3/code/java/src/test/java/MatrixMultiplierTest.java
task3/code/java/pom.xml
```

## How to Run

Python / NumPy:

```bash
python3 task3/code/python/matrix_multiply_numpy.py
```

Java:

```bash
cd task3/code/java
mvn test
java -cp target/classes MatrixMultiplier
```

## Example Result

I used this example for the Java run:

```text
Matrix A:
1 2
3 4
5 6

Matrix B:
1 2 3
4 5 6
```

Java terminal output:

```text
hacizadarufat@KapMac334-M java % java -cp target/classes MatrixMultiplier
Matrix multiplication in Java
Rows of matrix A: 3
Columns of matrix A: 2
Rows of matrix B: 2
Columns of matrix B: 3
Enter values for matrix A:
Row 1 (2 numbers): 1 2
Row 2 (2 numbers): 3 4
Row 3 (2 numbers): 5 6
Enter values for matrix B:
Row 1 (3 numbers): 1 2 3
Row 2 (3 numbers): 4 5 6
Result:
9 12 15
19 26 33
29 40 51
hacizadarufat@KapMac334-M java %
```

The NumPy version gives the same result for the same matrices.

## Input Validation

The programs check the main input problems:

- matrix dimensions must be positive integers
- text or decimal input is rejected for dimensions
- matrix multiplication is rejected when columns of A are not equal to rows of B
- each matrix row must contain exactly the expected number of values
- matrix values must be numeric

## Java Unit Tests

I used JUnit for the Java implementation. The tests check normal multiplication and invalid matrix cases.

Test output:

```text
hacizadarufat@KapMac334-M java % mvn test
[INFO] Scanning for projects...
[INFO] Building matrix-multiplication 1.0.0
[INFO] Running MatrixMultiplierTest
Tests run: 5, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
[INFO] Total time:  1.074 s
hacizadarufat@KapMac334-M java %
```

## Code Size Analysis

I counted the lines of code using `wc -l`.

```text
hacizadarufat@anonymous A2 % wc -l task3/code/python/matrix_multiply_numpy.py task3/code/java/src/main/java/MatrixMultiplier.java task3/code/java/src/test/java/MatrixMultiplierTest.java task3/code/java/pom.xml
      65 task3/code/python/matrix_multiply_numpy.py
     137 task3/code/java/src/main/java/MatrixMultiplier.java
      88 task3/code/java/src/test/java/MatrixMultiplierTest.java
      34 task3/code/java/pom.xml
     324 total
```

The NumPy version is shorter because NumPy already provides matrix multiplication with `np.matmul()`. The Java version is longer because I wrote the multiplication loops, input reading, validation, and output formatting manually. The test file also adds extra lines, but it is useful because it checks that the Java logic is correct.

## Execution Time Analysis

To compare execution time, I ran both programs with the same 3x2 and 2x3 matrices. I used `/usr/bin/time -p` for a simple terminal measurement.

```text
hacizadarufat@anonymous A2 % /usr/bin/time -p sh -c "printf '3\n2\n2\n3\n1 2\n3 4\n5 6\n1 2 3\n4 5 6\n' | python3 task3/code/python/matrix_multiply_numpy.py > /tmp/task3_python_time_output.txt"
real 0.17
user 0.05
sys 0.04
```

```text
hacizadarufat@anonymous java % /usr/bin/time -p sh -c "printf '3\n2\n2\n3\n1 2\n3 4\n5 6\n1 2 3\n4 5 6\n' | java -cp target/classes MatrixMultiplier > /tmp/task3_java_time_output.txt"
real 0.07
user 0.04
sys 0.02
```

For this small example, the measured time includes program startup and input processing, not only the matrix multiplication step. Because of that, these numbers should not be treated as a full benchmark. Still, it shows that both programs run quickly for small matrices. For larger matrices, NumPy would usually be faster because its internal implementation is optimized.

## ChatGPT Comparison

After finishing my own code, I asked ChatGPT to implement the same task. My prompt asked for NumPy code, Java code, JUnit tests, and a README.

ChatGPT conversation link: [https://chatgpt.com/share/6ac52e0a-d834-83eb-af67-25ef384c9380](https://chatgpt.com/share/6ac52e0a-d834-83eb-af67-25ef384c9380)

ChatGPT solved the task with the same general idea. It used `np.matmul()` for the NumPy version and three nested loops for the Java version. It also created JUnit tests for the Java code and included a Maven `pom.xml`.

The main problem was that ChatGPT's generated programs used fixed example matrices inside the code. That does not fully match the assignment, because the assignment says that parameters like matrix size should come from user input. Because of that, I did not directly use its code. In my version, the user enters the matrix sizes and values, and both implementations check the input before multiplying.

ChatGPT also measured execution time inside the programs with `time.perf_counter()` in Python and `System.nanoTime()` in Java. I used terminal timing with `/usr/bin/time -p` instead, because I wanted to run both programs from the command line and show the real terminal output in the report.

The prompt helped me confirm the general structure of the solution, but I had to guide the final version toward the assignment requirements: user input, Java tests, simple validation, and clear run commands.
