package model;

public class Tetromino {

    public void setShape(int[][] shape) {
    }

    // Enumerado para los tipos de piezas
    public enum TetrominoType {
        I(new int[][]{
                {1, 1, 1, 1}
        }),
        O(new int[][]{
                {1, 1},
                {1, 1}
        }),
        T(new int[][]{
                {0, 1, 0},
                {1, 1, 1}
        }),
        S(new int[][]{
                {0, 1, 1},
                {1, 1, 0}
        }),
        Z(new int[][]{
                {1, 1, 0},
                {0, 1, 1}
        }),
        J(new int[][]{
                {1, 0, 0},
                {1, 1, 1}
        }),
        L(new int[][]{
                {0, 0, 1},
                {1, 1, 1}
        });

        private final int[][] shape;

        TetrominoType(int[][] shape) {
            this.shape = shape;
        }

        public int[][] getShape() {
            return shape;
        }
    }

    // Atributos
    private TetrominoType type; // Tipo de pieza (I, O, T, S, Z, J, L).
    private int[][] shape; // Matriz que define la forma de la pieza.

    // Constructor
    public Tetromino(TetrominoType type) {
        if (type == null) {
            throw new IllegalArgumentException("El tipo de Tetromino no puede ser nulo.");
        }
        this.type = type;
        this.shape = cloneMatrix(type.getShape());
    }

    // Métodos auxiliares

    /**
     * Clona una matriz 2D para garantizar inmutabilidad.
     */
    private int[][] cloneMatrix(int[][] original) {
        int[][] copy = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = original[i].clone();
        }
        return copy;
    }

    // Getters

    /**
     * Devuelve el tipo de Tetromino.
     */
    public TetrominoType getType() {
        return type;
    }

    /**
     * Devuelve una copia de la matriz de forma del Tetromino.
     */
    public int[][] getShape() {
        return cloneMatrix(shape);
    }

    /**
     * Devuelve la altura de la pieza (número de filas).
     */
    public int getHeight() {
        return shape.length;
    }

    /**
     * Devuelve el ancho de la pieza (número de columnas).
     */
    public int getWidth() {
        return shape[0].length;
    }

    /**
     * Cuenta el número de celdas ocupadas (valor 1) en la matriz.
     */
    public int getOccupiedCells() {
        int count = 0;
        for (int[] row : shape) {
            for (int cell : row) {
                if (cell == 1) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Rota la pieza 90 grados en sentido horario.
     */
    public void rotate() {
        // Pieza "O" no rota, ya que es simétrica.
        if (type == TetrominoType.O) {
            return;
        }

        // Crear una nueva matriz con las dimensiones rotadas.
        int rows = shape.length;
        int cols = shape[0].length;
        int[][] rotatedShape = new int[cols][rows];

        // Rotar la matriz: Transposición e inversión.
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rotatedShape[j][rows - 1 - i] = shape[i][j];
            }
        }

        // Actualizar la forma.
        shape = rotatedShape;
    }
}
