package MatrixProcessing;

import java.util.InputMismatchException;
import java.util.Scanner;

class Matrix {
    private final double[][] data;
    private final int rows;
    private final int cols;

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = new double[rows][cols];
    }

    public static Matrix fromInput(Scanner scanner, int rows, int cols) {
        Matrix matrix = new Matrix(rows, cols);
        System.out.println("Enter matrix elements row by row:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                while (true) {
                    try {
                        matrix.data[i][j] = scanner.nextDouble();
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a number:");
                        scanner.next();
                    }
                }
            }
        }
        return matrix;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public double get(int row, int col) {
        return data[row][col];
    }

    public void set(int row, int col, double value) {
        data[row][col] = value;
    }

    public void print() {
        for (double[] row : data) {
            for (double value : row) {
                System.out.printf("%.2f ", value);
            }
            System.out.println();
        }
    }

    public Matrix add(Matrix other) {
        if (rows != other.rows || cols != other.cols) {
            throw new IllegalArgumentException("Matrices must have the same dimensions for addition.");
        }
        Matrix result = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.set(i, j, this.get(i, j) + other.get(i, j));
            }
        }
        return result;
    }

    public Matrix multiplyByConstant(double constant) {
        Matrix result = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.set(i, j, this.get(i, j) * constant);
            }
        }
        return result;
    }

    public Matrix multiply(Matrix other) {
        if (cols != other.rows) {
            throw new IllegalArgumentException("Number of columns in the first matrix must equal number of rows in the second.");
        }
        Matrix result = new Matrix(rows, other.cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < other.cols; j++) {
                double sum = 0;
                for (int k = 0; k < cols; k++) {
                    sum += this.get(i, k) * other.get(k, j);
                }
                result.set(i, j, sum);
            }
        }
        return result;
    }

    public Matrix transposeMainDiagonal() {
        Matrix result = new Matrix(cols, rows);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.set(j, i, this.get(i, j));
            }
        }
        return result;
    }

    public double determinant() {
        if (rows != cols) {
            throw new IllegalArgumentException("Matrix must be square to calculate determinant.");
        }
        return determinantRecursive(data);
    }

    private double determinantRecursive(double[][] matrix) {
        int size = matrix.length;
        if (size == 1) return matrix[0][0];
        if (size == 2) return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];

        double determinant = 0;
        for (int i = 0; i < size; i++) {
            double[][] subMatrix = new double[size - 1][size - 1];
            for (int j = 1; j < size; j++) {
                for (int k = 0, col = 0; k < size; k++) {
                    if (k != i) subMatrix[j - 1][col++] = matrix[j][k];
                }
            }
            determinant += Math.pow(-1, i) * matrix[0][i] * determinantRecursive(subMatrix);
        }
        return determinant;
    }

    public Matrix inverse() {
        double det = this.determinant();
        if (det == 0) {
            throw new IllegalArgumentException("This matrix doesn't have an inverse.");
        }

        Matrix adjoint = adjugate();
        return adjoint.multiplyByConstant(1 / det);
    }

    private Matrix adjugate() {
        if (rows != cols) {
            throw new IllegalArgumentException("Matrix must be square to calculate adjugate.");
        }

        Matrix result = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                double[][] minor = new double[rows - 1][cols - 1];
                for (int k = 0, minorRow = 0; k < rows; k++) {
                    if (k == i) continue;
                    for (int l = 0, minorCol = 0; l < cols; l++) {
                        if (l == j) continue;
                        minor[minorRow][minorCol++] = data[k][l];
                    }
                    minorRow++;
                }
                result.set(j, i, Math.pow(-1, i + j) * determinantRecursive(minor));
            }
        }
        return result;
    }
}

public class MatrixProcessing {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            printMenu();
            choice = readChoice(scanner);

            try {
                switch (choice) {
                    case 1 -> performAddition(scanner);
                    case 2 -> performMultiplicationByConstant(scanner);
                    case 3 -> performMatrixMultiplication(scanner);
                    case 4 -> performTranspose(scanner);
                    case 5 -> performDeterminantCalculation(scanner);
                    case 6 -> performMatrixInversion(scanner);
                    case 0 -> System.out.println("Exiting program...");
                    default -> System.out.println("Invalid choice. Try again.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (choice != 0);

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("""
                1. Add matrices
                2. Multiply matrix by a constant
                3. Multiply matrices
                4. Transpose matrix
                5. Calculate a determinant
                6. Inverse matrix
                0. Exit
                """);
    }

    private static int readChoice(Scanner scanner) {
        while (true) {
            try {
                System.out.print("Your choice: > ");
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.next();
            }
        }
    }

    private static void performAddition(Scanner scanner) {
        System.out.print("Enter size of first matrix: > ");
        int rows1 = scanner.nextInt(), cols1 = scanner.nextInt();
        Matrix matrix1 = Matrix.fromInput(scanner, rows1, cols1);

        System.out.print("Enter size of second matrix: > ");
        int rows2 = scanner.nextInt(), cols2 = scanner.nextInt();
        Matrix matrix2 = Matrix.fromInput(scanner, rows2, cols2);

        Matrix result = matrix1.add(matrix2);
        System.out.println("The result is:");
        result.print();
    }

    private static void performMultiplicationByConstant(Scanner scanner) {
        System.out.print("Enter size of matrix: > ");
        int rows = scanner.nextInt(), cols = scanner.nextInt();
        Matrix matrix = Matrix.fromInput(scanner, rows, cols);

        System.out.print("Enter constant: > ");
        double constant = scanner.nextDouble();

        Matrix result = matrix.multiplyByConstant(constant);
        System.out.println("The result is:");
        result.print();
    }

    private static void performMatrixMultiplication(Scanner scanner) {
        System.out.print("Enter size of first matrix: > ");
        int rows1 = scanner.nextInt(), cols1 = scanner.nextInt();
        Matrix matrix1 = Matrix.fromInput(scanner, rows1, cols1);

        System.out.print("Enter size of second matrix: > ");
        int rows2 = scanner.nextInt(), cols2 = scanner.nextInt();
        Matrix matrix2 = Matrix.fromInput(scanner, rows2, cols2);

        Matrix result = matrix1.multiply(matrix2);
        System.out.println("The result is:");
        result.print();
    }

    private static void performTranspose(Scanner scanner) {
        System.out.print("Enter matrix size: > ");
        int rows = scanner.nextInt(), cols = scanner.nextInt();
        Matrix matrix = Matrix.fromInput(scanner, rows, cols);

        Matrix result = matrix.transposeMainDiagonal();
        System.out.println("The result is:");
        result.print();
    }

    private static void performDeterminantCalculation(Scanner scanner) {
        System.out.print("Enter matrix size: > ");
        int size = scanner.nextInt();
        Matrix matrix = Matrix.fromInput(scanner, size, size);

        double determinant = matrix.determinant();
        System.out.println("The result is:");
        System.out.printf("%.2f%n", determinant);
    }

    private static void performMatrixInversion(Scanner scanner) {
        System.out.print("Enter matrix size: > ");
        int size = scanner.nextInt();
        Matrix matrix = Matrix.fromInput(scanner, size, size);

        Matrix result = matrix.inverse();
        System.out.println("The result is:");
        result.print();
    }
}
