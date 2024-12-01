package model;

/**
 * Clase que representa una pieza Tetromino en el juego de Tetris.
 *
 * **Invariantes de Clase:**
 * - `type` nunca es `null` después de la construcción.
 * - `shape` es una matriz 2D válida que representa la forma del Tetromino.
 * - `shape` no es `null` y no contiene filas `null`.
 * - Los valores en `shape` son 0 o 1.
 */
public class Tetromino {

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
    private final TetrominoType type; // Tipo de pieza (I, O, T, S, Z, J, L).
    private int[][] shape; // Matriz que define la forma de la pieza.

    /**
     * Constructor de Tetromino.
     *
     * **Precondiciones:**
     * - `type` no es `null`.
     *
     * **Postcondiciones:**
     * - `this.type` se inicializa con el valor proporcionado.
     * - `this.shape` es una copia de la forma asociada al `type`.
     *
     * @param type Tipo de Tetromino.
     * @throws IllegalArgumentException si `type` es `null`.
     */
    public Tetromino(TetrominoType type) {
        if (type == null) {
            throw new IllegalArgumentException("El tipo de Tetromino no puede ser nulo.");
        }
        this.type = type;
        this.shape = cloneMatrix(type.getShape());

        // Postcondición: `shape` no es `null` y es una matriz válida.
        if (!isValidShape(this.shape)) {
            throw new IllegalStateException("Postcondición fallida: La forma del Tetromino no es válida.");
        }
    }

    // Métodos auxiliares

    /**
     * Clona una matriz 2D para garantizar inmutabilidad.
     *
     * **Precondiciones:**
     * - `original` no es `null`.
     * - Ninguna fila en `original` es `null`.
     *
     * **Postcondiciones:**
     * - Se devuelve una nueva matriz que es una copia profunda de `original`.
     *
     * @param original Matriz original a clonar.
     * @return Copia de la matriz original.
     * @throws IllegalArgumentException si `original` es `null` o contiene filas `null`.
     */
    private int[][] cloneMatrix(int[][] original) {
        if (original == null) {
            throw new IllegalArgumentException("La matriz original no puede ser nula.");
        }
        int[][] copy = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            if (original[i] == null) {
                throw new IllegalArgumentException("Las filas de la matriz original no pueden ser nulas.");
            }
            copy[i] = original[i].clone();
        }
        return copy;
    }

    /**
     * Verifica si una matriz representa una forma válida de Tetromino.
     *
     * @param shape Matriz a verificar.
     * @return `true` si es válida; `false` de lo contrario.
     */
    private boolean isValidShape(int[][] shape) {
        if (shape == null) {
            return false;
        }
        for (int[] row : shape) {
            if (row == null) {
                return false;
            }
            for (int cell : row) {
                if (cell != 0 && cell != 1) {
                    return false;
                }
            }
        }
        return true;
    }

    // Getters

    /**
     * Devuelve el tipo de Tetromino.
     *
     * **Postcondiciones:**
     * - El valor devuelto no es `null`.
     *
     * @return Tipo de Tetromino.
     */
    public TetrominoType getType() {
        return type;
    }

    /**
     * Devuelve una copia de la matriz de forma del Tetromino.
     *
     * **Postcondiciones:**
     * - El valor devuelto no es `null`.
     * - No modifica el estado interno del objeto.
     *
     * @return Copia de la matriz de forma.
     */
    public int[][] getShape() {
        return cloneMatrix(shape);
    }

    /**
     * Devuelve la altura de la pieza (número de filas).
     *
     * **Postcondiciones:**
     * - El valor devuelto es mayor que 0.
     *
     * @return Altura de la pieza.
     */
    public int getHeight() {
        return shape.length;
    }

    /**
     * Devuelve el ancho de la pieza (número de columnas).
     *
     * **Postcondiciones:**
     * - El valor devuelto es mayor que 0.
     *
     * @return Ancho de la pieza.
     */
    public int getWidth() {
        return shape[0].length;
    }

    /**
     * Cuenta el número de celdas ocupadas (valor 1) en la matriz.
     *
     * **Postcondiciones:**
     * - El valor devuelto es mayor o igual a 0.
     *
     * @return Número de celdas ocupadas.
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
     *
     * **Precondiciones:**
     * - `shape` es una matriz válida.
     *
     * **Postcondiciones:**
     * - La forma del Tetromino se actualiza correctamente.
     * - Si el Tetromino es de tipo O, la forma no cambia.
     * - El invariante de clase se mantiene.
     */
    public void rotate() {
        // Pieza "O" no rota, ya que es simétrica.
        if (type == TetrominoType.O) {
            return;
        }

        // Precondición: Verificar que `shape` es válida.
        if (!isValidShape(shape)) {
            throw new IllegalStateException("Precondición fallida: La forma actual no es válida.");
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

        // Postcondición: Verificar que `shape` sigue siendo válida.
        if (!isValidShape(shape)) {
            throw new IllegalStateException("Postcondición fallida: La forma rotada no es válida.");
        }
    }

    /**
     * Establece una nueva forma para el Tetromino.
     *
     * **Precondiciones:**
     * - `shape` no es `null`.
     * - `shape` es una matriz válida.
     *
     * **Postcondiciones:**
     * - La forma del Tetromino se actualiza con la nueva matriz.
     * - El invariante de clase se mantiene.
     *
     * @param shape Nueva forma a establecer.
     * @throws IllegalArgumentException si `shape` es `null` o inválida.
     */
    public void setShape(int[][] shape) {
        if (shape == null) {
            throw new IllegalArgumentException("La forma no puede ser nula.");
        }
        if (!isValidShape(shape)) {
            throw new IllegalArgumentException("La forma proporcionada no es válida.");
        }
        this.shape = cloneMatrix(shape);

        // Postcondición: Verificar que `shape` sigue siendo válida.
        if (!isValidShape(this.shape)) {
            throw new IllegalStateException("Postcondición fallida: La nueva forma no es válida.");
        }
    }
}
