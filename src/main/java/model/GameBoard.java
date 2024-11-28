package model;

/**
 * Clase que representa el tablero del juego de Tetris.
 * Invariantes:
 * - rows y cols son constantes después de la construcción.
 * - Todas las celdas del tablero contienen valores 0 o 1.
 */
public class GameBoard {
    private final int rows;
    private final int cols;
    private final int[][] board;

    /**
     * Constructor para inicializar el tablero con las dimensiones especificadas.
     * Precondiciones:
     * - rows > 0
     * - cols > 0
     * Postcondiciones:
     * - El tablero se inicializa con todas las celdas en 0.
     *
     * @param rows Número de filas del tablero.
     * @param cols Número de columnas del tablero.
     */
    public GameBoard(int rows, int cols) {
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Las dimensiones del tablero deben ser mayores a 0.");
        }
        this.rows = rows;
        this.cols = cols;
        this.board = new int[rows][cols];
        assert allCellsAreValid() : "Postcondición fallida: Las celdas iniciales no están todas en 0.";
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    /**
     * Devuelve el valor de una celda en el tablero.
     * Precondiciones:
     * - row >= 0 y row < rows
     * - col >= 0 y col < cols
     * Postcondiciones:
     * - No modifica el estado del tablero.
     *
     * @param row Fila de la celda.
     * @param col Columna de la celda.
     * @return Valor de la celda (0 o 1).
     */
    public int getCell(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            throw new IndexOutOfBoundsException("Índices fuera del rango del tablero.");
        }
        return board[row][col];
    }

    /**
     * Establece el valor de una celda específica en el tablero.
     * Precondiciones:
     * - row >= 0 y row < rows
     * - col >= 0 y col < cols
     * - value == 0 o value == 1
     * Postcondiciones:
     * - La celda especificada contiene el valor dado.
     *
     * @param row   Fila de la celda.
     * @param col   Columna de la celda.
     * @param value Valor a establecer (0 o 1).
     */
    public void setCell(int row, int col, int value) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            throw new IndexOutOfBoundsException("Índices fuera del rango del tablero.");
        }
        if (value != 0 && value != 1) {
            throw new IllegalArgumentException("El valor debe ser 0 o 1.");
        }
        board[row][col] = value;
        assert board[row][col] == value : "Postcondición fallida: La celda no contiene el valor esperado.";
    }

    /**
     * Coloca una pieza en el tablero si hay espacio disponible.
     * Precondiciones:
     * - piece != null
     * - La posición inicial (row, col) es válida.
     * Postcondiciones:
     * - La pieza se coloca en el tablero si es posible.
     * - Si no hay espacio disponible, el estado del tablero no cambia.
     *
     * @param piece Pieza a colocar.
     * @param row   Fila inicial para colocar la pieza.
     * @param col   Columna inicial para colocar la pieza.
     * @return true si la pieza se coloca con éxito; false si no se puede colocar.
     */
    public boolean placePiece(Tetromino piece, int row, int col) {
        if (piece == null) {
            throw new IllegalArgumentException("La pieza no puede ser nula.");
        }

        int[][] shape = piece.getShape();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] == 1) {
                    int boardRow = row + i;
                    int boardCol = col + j;

                    if (boardRow < 0 || boardRow >= rows || boardCol < 0 || boardCol >= cols) {
                        return false; // La pieza se sale del tablero.
                    }
                    if (board[boardRow][boardCol] == 1) {
                        return false; // Colisión detectada.
                    }
                }
            }
        }

        // Colocar la pieza.
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] == 1) {
                    board[row + i][col + j] = 1;
                }
            }
        }

        assert allCellsAreValid() : "Postcondición fallida: El tablero contiene valores inválidos.";
        return true;
    }

    /**
     * Limpia las líneas completas en el tablero.
     * Postcondiciones:
     * - Todas las líneas completas se eliminan.
     * - Las filas superiores se desplazan hacia abajo.
     * - Devuelve el número de líneas limpiadas.
     *
     * @return Número de líneas eliminadas.
     */
    public int clearCompleteLines() {
        int linesCleared = 0;

        for (int i = 0; i < rows; i++) {
            boolean isComplete = true;
            for (int j = 0; j < cols; j++) {
                if (board[i][j] == 0) {
                    isComplete = false;
                    break;
                }
            }

            if (isComplete) {
                linesCleared++;
                clearLine(i);
            }
        }

        assert allCellsAreValid() : "Postcondición fallida: El tablero contiene valores inválidos.";
        return linesCleared;
    }

    private void clearLine(int line) {
        for (int i = line; i > 0; i--) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = board[i - 1][j];
            }
        }
        for (int j = 0; j < cols; j++) {
            board[0][j] = 0;
        }
    }

    private boolean allCellsAreValid() {
        for (int[] row : board) {
            for (int cell : row) {
                if (cell != 0 && cell != 1) {
                    return false;
                }
            }
        }
        return true;
    }
}