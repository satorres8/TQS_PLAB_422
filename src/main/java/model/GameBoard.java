package model;

import javax.swing.Timer;

/**
 * Clase que representa el tablero del juego de Tetris.
 * Invariantes:
 * - rows y cols son constantes después de la construcción.
 * - Todas las celdas del tablero contienen valores >= 0.
 */
public class GameBoard {
    private final int rows;
    private final int cols;
    private final int[][] board;
    private Tetromino currentTetromino;
    private int tetrominoX;
    private int tetrominoY;
    private int score = 0; // Sistema de puntuación

    /**
     * Constructor para inicializar el tablero.
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
        spawnTetromino();
        assert allCellsAreValid() : "Postcondición fallida: Las celdas iniciales no están todas en 0.";
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getScore() {
        return score;
    }

    /**
     * Devuelve el valor de una celda específica en el tablero.
     * Precondiciones:
     * - row >= 0 y row < rows
     * - col >= 0 y col < cols
     * Postcondiciones:
     * - No modifica el estado del tablero.
     *
     * @param row Fila de la celda.
     * @param col Columna de la celda.
     * @return Valor de la celda (>= 0).
     */
    public int getCell(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            throw new IndexOutOfBoundsException("Índices fuera del rango del tablero.");
        }
        int value = board[row][col];
        assert value >= 0 : "Postcondición fallida: El valor de la celda no es válido.";
        return value;
    }


    /**
     * Establece el valor de una celda específica en el tablero.
     * Precondiciones:
     * - row >= 0 y row < rows
     * - col >= 0 y col < cols
     * - value >= 0
     *
     * @param row   Fila de la celda.
     * @param col   Columna de la celda.
     * @param value Valor a establecer en la celda.
     */
    public void setCell(int row, int col, int value) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            throw new IndexOutOfBoundsException("Índices fuera del rango del tablero.");
        }
        if (value < 0) {
            throw new IllegalArgumentException("El valor debe ser mayor o igual a 0.");
        }
        board[row][col] = value;
    }

    public void spawnTetromino() {
        // Precondición: El tablero debe estar en un estado válido.
        assert allCellsAreValid() : "Precondición fallida: El tablero tiene valores inválidos.";

        // Genera una nueva pieza aleatoria.
        currentTetromino = new Tetromino(Tetromino.TetrominoType.values()[(int) (Math.random() * 7)]);
        tetrominoX = cols / 2 - currentTetromino.getShape()[0].length / 2;
        tetrominoY = 0;

        // Postcondición: La pieza generada no puede ser null.
        assert currentTetromino != null : "Postcondición fallida: La pieza actual no puede ser null.";

        // Verifica si la nueva pieza puede colocarse en la posición inicial.
        if (!canMove(tetrominoX, tetrominoY)) {
            throw new IllegalStateException("No se puede generar una nueva pieza: colisión detectada. Fin del juego.");
        }

        // Postcondición: Después de generar la nueva pieza, debe haber espacio para moverla.
        assert canMove(tetrominoX, tetrominoY) : "Postcondición fallida: La nueva pieza no tiene espacio para moverse.";

        // Postcondición: El tablero debe seguir siendo válido.
        assert allCellsAreValid() : "Postcondición fallida: El tablero tiene valores inválidos después de generar la nueva pieza.";
    }

    public Tetromino getCurrentTetromino() {
        return currentTetromino;
    }

    public int getTetrominoX() {
        return tetrominoX;
    }

    public int getTetrominoY() {
        return tetrominoY;
    }

    /**
     * Mueve la pieza actual hacia abajo.
     * Postcondiciones:
     * - Si la pieza no puede moverse, se coloca en el tablero y se genera una nueva.
     *
     * @return true si la pieza se movió; false si se colocó en el tablero.
     */
    public boolean moveTetrominoDown() {
        if (!canMove(tetrominoX, tetrominoY + 1)) {
            placeTetromino();
            spawnTetromino();
            return false;
        }
        tetrominoY++;
        return true;
    }

    public void moveTetrominoLeft() {
        if (canMove(tetrominoX - 1, tetrominoY)) {
            tetrominoX--;
        }
    }

    public void moveTetrominoRight() {
        if (canMove(tetrominoX + 1, tetrominoY)) {
            tetrominoX++;
        }
    }

    public void rotateTetromino() {
        Tetromino rotatedTetromino = new Tetromino(currentTetromino.getType());
        rotatedTetromino.rotate();
        if (canMove(tetrominoX, tetrominoY, rotatedTetromino.getShape())) {
            currentTetromino.rotate();
        }
    }

    /**
     * Coloca la pieza actual en el tablero.
     * Postcondiciones:
     * - La pieza actual se integra en el tablero.
     * - Las líneas completas se eliminan si es necesario.
     */
    public void placeTetromino() {
        int[][] shape = currentTetromino.getShape();
        for (int row = 0; row < shape.length; row++) {
            for (int col = 0; col < shape[row].length; col++) {
                if (shape[row][col] == 1) {
                    int boardY = tetrominoY + row;
                    int boardX = tetrominoX + col;
                    if (boardY >= 0 && boardY < rows && boardX >= 0 && boardX < cols) {
                        board[boardY][boardX] = currentTetromino.getType().ordinal() + 1;
                    }
                }
            }
        }
        clearCompleteLines();
        assert allCellsAreValid() : "Postcondición fallida: El tablero contiene valores inválidos.";
    }

    /**
     * Limpia las líneas completas en el tablero.
     * Postcondiciones:
     * - Todas las líneas completas se eliminan.
     * - Las filas superiores se desplazan hacia abajo.
     */
    public void clearCompleteLines() {
        for (int row = 0; row < rows; row++) {
            boolean isComplete = true;
            for (int col = 0; col < cols; col++) {
                if (board[row][col] == 0) {
                    isComplete = false;
                    break;
                }
            }
            if (isComplete) {
                clearLine(row);
                score += 100; // Incrementar puntuación por línea
            }
        }
        assert allCellsAreValid() : "Postcondición fallida: El tablero contiene valores inválidos.";
    }

    private void clearLine(int line) {
        for (int row = line; row > 0; row--) {
            System.arraycopy(board[row - 1], 0, board[row], 0, cols);
        }
        for (int col = 0; col < cols; col++) {
            board[0][col] = 0;
        }
    }

    private boolean canMove(int newX, int newY) {
        return canMove(newX, newY, currentTetromino.getShape());
    }

    private boolean canMove(int newX, int newY, int[][] shape) {
        for (int row = 0; row < shape.length; row++) {
            for (int col = 0; col < shape[row].length; col++) {
                if (shape[row][col] == 1) {
                    int boardX = newX + col;
                    int boardY = newY + row;
                    if (boardX < 0 || boardX >= cols || boardY >= rows) {
                        return false;
                    }
                    if (boardY >= 0 && board[boardY][boardX] != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean allCellsAreValid() {
        for (int[] row : board) {
            for (int cell : row) {
                if (cell < 0) {
                    return false;
                }
            }
        }
        return true;
    }
}