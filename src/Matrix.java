public class Matrix {
    private double[][] data;
    private int size;

    // Konstruktor für eine leere Matrix der gegebenen Größe
    public Matrix(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Die Größe der Matrix muss größer als 0 sein.");
        }
        this.size = size;
        this.data = new double[size][size];
    }

    //Erstellen einer Matrix mit einem Array
    public Matrix(double[][] data) {
        this.size = data.length;
        this.data = data;
    }

    // Zugriffsmethoden
    public double get(int row, int col) {
        checkBounds(row, col);
        return data[row][col];
    }

    // Methode, um die i-te Zeile der Matrix zu erhalten
    public double[] getRow(int i) {
        if (i < 0 || i >= size) {
            throw new IllegalArgumentException("Row index out of bounds");
        }
        return data[i].clone(); // Gibt eine Kopie der Zeile zurück, um die Datenkapselung zu wahren
    }

    public void set(int row, int col, double value) {
        checkBounds(row, col);
        data[row][col] = value;
    }

    // Hilfsmethode, um sicherzustellen, dass die Indizes gültig sind
    private void checkBounds(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            throw new IndexOutOfBoundsException("Index außerhalb der Matrixgrenzen.");
        }
    }

    // Erzeugung einer Einheitsmatrix
    public static Matrix identity(int size) {
        Matrix identity = new Matrix(size);
        for (int i = 0; i < size; i++) {
            identity.set(i, i, 1.0);
        }
        return identity;
    }

    // Methode zur Ausgabe der Matrix auf der Konsole
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                sb.append(data[i][j]).append(" | ");
            }
            sb.delete(sb.length() - 3, sb.length()); // Entfernt das überflüssige " | " am Ende der Zeile
            sb.append("\n"); // Fügt einen Zeilenumbruch hinzu
        }
        return sb.toString();
    }

    // Getter für die Größe der Matrix
    public int getSize() {
        return size;
    }

    @Override
    public boolean equals(Object o) {
        // Null check and type check
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        // Cast to Matrix
        Matrix matrix = (Matrix) o;
        // Size check
        if (this.size != matrix.size) {
            return false;
        }
        // Element-wise check
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!areEqualWithTolerance(this.data[i][j], matrix.data[i][j])) {
                    return false;
                }
            }
        }
        return true;
    }

    // Helper method to compare two double values with a tolerance
    private boolean areEqualWithTolerance(double a, double b) {
        final double TOLERANCE = 1e-9;
        return Math.abs(a - b) < TOLERANCE;
    }

    // Methode zur Multiplikation zweier Matrizen
    public Matrix multiply(Matrix other) {
        if (this.size != other.size) {
            throw new IllegalArgumentException("Matrices have different sizes and cannot be multiplied.");
        }

        double[][] resultData = new double[this.size][this.size];

        for (int i = 0; i < this.size; i++) {
            for (int k = 0; k < this.size; k++) {
                for (int j = 0; j < this.size; j++) {
                    resultData[i][k] += this.data[i][j] * other.data[j][k];
                }
            }
        }

        return new Matrix(resultData);
    }

    public Vector multiply(Vector vector) {
        double[] result = multiply(new double[]{vector.getX(), vector.getY(), vector.getZ(), vector.getW()});
        Vector resultVector = new Vector(result[0], result[1], result[2], result[3]);
        return resultVector;
    }


    public Point multiply(Point point) {
        double[] result = multiply(new double[]{point.getX(), point.getY(), point.getZ(), point.getW()});
        return new Point(result[0], result[1], result[2], result[3]);
    }

    //rechnung für Multiplikation von Vektor/Point
    private double[] multiply(double[] vec) {
        double[] result = new double[4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result[i] += this.data[i][j] * vec[j];
            }
            //System.out.println(result[i]);
        }
        return result;
    }

    // Methode zum Transponieren der Matrix
    public Matrix transpose() {
        double[][] transposedData = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                transposedData[j][i] = this.data[i][j];
            }
        }
        return new Matrix(transposedData);
    }

    // Methode zur Berechnung der Determinante
    public double determinant() {
        if (size == 1) {
            return data[0][0];
        }
        if (size == 2) {
            return data[0][0] * data[1][1] - data[0][1] * data[1][0];
        }
        double det = 0;
        for (int j = 0; j < size; j++) {
            det += Math.pow(-1, j) * data[0][j] * subMatrix(0, j).determinant();
        }
        return det;
    }

    // Hilfsmethode zur Erstellung einer Untermatrix
    public Matrix subMatrix(int excluding_row, int excluding_col) {
        Matrix mat = new Matrix(new double[size - 1][size - 1]);
        int r = -1;
        for (int i = 0; i < size; i++) {
            if (i == excluding_row) continue;
            r++;
            int c = -1;
            for (int j = 0; j < size; j++) {
                if (j == excluding_col) continue;
                mat.data[r][++c] = this.data[i][j];
            }
        }
        return mat;
    }

    // Methode zur Berechnung des Minors einer Matrix
    public double minor(int row, int col) {
        return subMatrix(row, col).determinant();
    }

    // Methode zur Berechnung des Kofaktors einer Matrix
    public double cofactor(int row, int col) {
        return Math.pow(-1, row + col) * minor(row, col);
    }

    // Methode zur Berechnung der inversen Matrix
    public Matrix inverse() {
        double det = determinant();
        if (Math.abs(det) < 1e-9) {
            throw new ArithmeticException("Matrix ist nicht invertierbar, da ihre Determinante null ist.");
        }

        Matrix cofactorMatrix = new Matrix(size);
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                // Berechnung des Kofaktors für jedes Element
                double cofactor = cofactor(row, col);
                cofactorMatrix.set(row, col, cofactor);
            }
        }

        // Transponieren der Kofaktorenmatrix, um die Adjunkte zu erhalten
        Matrix adjugate = cofactorMatrix.transpose();

        // Multiplikation jedes Elements der Adjunkte mit dem Kehrwert der Determinante
        Matrix inverseMatrix = new Matrix(size);
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                inverseMatrix.set(row, col, adjugate.get(row, col) / det);
            }
        }

        return inverseMatrix;
    }


}
