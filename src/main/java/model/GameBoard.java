package model;

/**
 * Clase que representa el tablero del juego de Tetris.
 *
 * **Invariantes de Clase:**
 * - `rows` y `cols` son constantes después de la construcción y mayores que 0.
 * - Todas las celdas del tablero contienen valores >= 0.
 * - `currentTetromino` nunca es `null` después de ser inicializado.
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
     *
     * **Precondiciones:**
     * - `rows > 0`
     * - `cols > 0`
     *
     * **Postcondiciones:**
     * - El tablero se inicializa con todas las celdas en 0.
     * - Se genera un Tetromino inicial válido.
     *
     * @param rows Número de filas del tablero.
     * @param cols Número de columnas del tablero.
     * @throws IllegalArgumentException si `rows <= 0` o `cols <= 0`.
     */
    public GameBoard(int rows, int cols) {
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Las dimensiones del tablero deben ser mayores a 0.");
        }
        this.rows = rows;
        this.cols = cols;
        this.board = new int[rows][cols];
        spawnTetromino();

        // Postcondición: Verificar que todas las celdas están en 0.
        if (!allCellsAreValid()) {
            throw new IllegalStateException("Postcondición fallida: Las celdas iniciales no están todas en 0.");
        }
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
     *
     * **Precondiciones:**
     * - `row >= 0` y `row < rows`
     * - `col >= 0` y `col < cols`
     *
     * **Postcondiciones:**
     * - No modifica el estado del tablero.
     *
     * @param row Fila de la celda.
     * @param col Columna de la celda.
     * @return Valor de la celda (>= 0).
     * @throws IndexOutOfBoundsException si los índices están fuera de rango.
     */
    public int getCell(int row, int col) {
        validateIndices(row, col);
        int value = board[row][col];
        if (value < 0) {
            throw new IllegalStateException("Postcondición fallida: El valor de la celda no es válido.");
        }
        return value;
    }

    /**
     * Establece el valor de una celda específica en el tablero.
     *
     * **Precondiciones:**
     * - `row >= 0` y `row < rows`
     * - `col >= 0` y `col < cols`
     * - `value >= 0`
     *
     * @param row   Fila de la celda.
     * @param col   Columna de la celda.
     * @param value Valor a establecer en la celda.
     * @throws IndexOutOfBoundsException si los índices están fuera de rango.
     * @throws IllegalArgumentException  si `value < 0`.
     */
    public void setCell(int row, int col, int value) {
        validateIndices(row, col);
        if (value < 0) {
            throw new IllegalArgumentException("El valor debe ser mayor o igual a 0.");
        }
        board[row][col] = value;
    }

    /**
     * Genera una nueva pieza Tetromino en la posición inicial.
     *
     * **Precondiciones:**
     * - El tablero está en un estado válido.
     *
     * **Postcondiciones:**
     * - `currentTetromino` no es `null`.
     * - La nueva pieza tiene espacio para colocarse en la posición inicial.
     * - El tablero sigue siendo válido.
     *
     * @throws IllegalStateException si no hay espacio para la nueva pieza (fin del juego).
     */
    public void spawnTetromino() {
        // Precondición: El tablero debe estar en un estado válido.
        if (!allCellsAreValid()) {
            throw new IllegalStateException("Precondición fallida: El tablero tiene valores inválidos.");
        }

        // Genera una nueva pieza aleatoria.
        currentTetromino = new Tetromino(Tetromino.TetrominoType.values()[(int) (Math.random() * 7)]);
        tetrominoX = cols / 2 - currentTetromino.getShape()[0].length / 2;
        tetrominoY = 0;

        // Postcondición: La pieza generada no puede ser null.
        if (currentTetromino == null) {
            throw new IllegalStateException("Postcondición fallida: La pieza actual no puede ser null.");
        }

        // Verifica si la nueva pieza puede colocarse en la posición inicial.
        if (!canMove(tetrominoX, tetrominoY)) {
            throw new IllegalStateException("No se puede generar una nueva pieza: colisión detectada. Fin del juego.");
        }

        // Postcondición: Después de generar la nueva pieza, debe haber espacio para moverla.
        if (!canMove(tetrominoX, tetrominoY)) {
            throw new IllegalStateException("Postcondición fallida: La nueva pieza no tiene espacio para moverse.");
        }

        // Postcondición: El tablero debe seguir siendo válido.
        if (!allCellsAreValid()) {
            throw new IllegalStateException("Postcondición fallida: El tablero tiene valores inválidos después de generar la nueva pieza.");
        }
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
     *
     * **Postcondiciones:**
     * - Si la pieza no puede moverse, se coloca en el tablero y se genera una nueva.
     *
     * @return `true` si la pieza se movió; `false` si se colocó en el tablero.
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
        rotatedTetromino.setShape(currentTetromino.getShape());
        rotatedTetromino.rotate();

        if (canMove(tetrominoX, tetrominoY, rotatedTetromino.getShape())) {
            currentTetromino.rotate();
        }
    }

    /**
     * Coloca la pieza actual en el tablero.
     *
     * **Postcondiciones:**
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

        // Postcondición: El tablero no debe contener valores inválidos.
        if (!allCellsAreValid()) {
            throw new IllegalStateException("Postcondición fallida: El tablero contiene valores inválidos.");
        }
    }

    /**
     * Limpia las líneas completas en el tablero.
     *
     * **Postcondiciones:**
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

        // Postcondición: El tablero no debe contener valores inválidos.
        if (!allCellsAreValid()) {
            throw new IllegalStateException("Postcondición fallida: El tablero contiene valores inválidos.");
        }
    }

    /**
     * Elimina una línea completa y desplaza las superiores hacia abajo.
     *
     * @param line Índice de la línea a eliminar.
     */
    private void clearLine(int line) {
        for (int row = line; row > 0; row--) {
            System.arraycopy(board[row - 1], 0, board[row], 0, cols);
        }
        for (int col = 0; col < cols; col++) {
            board[0][col] = 0;
        }
    }

    /**
     * Verifica si la pieza puede moverse a una posición específica.
     *
     * @param newX Nueva posición X.
     * @param newY Nueva posición Y.
     * @return `true` si puede moverse; `false` de lo contrario.
     */
     public boolean canMove(int newX, int newY) {
        return canMove(newX, newY, currentTetromino.getShape());
    }

    /**
     * Verifica si una forma específica puede ubicarse en una posición dada.
     *
     * @param newX  Nueva posición X.
     * @param newY  Nueva posición Y.
     * @param shape Forma a verificar.
     * @return `true` si puede moverse; `false` de lo contrario.
     */
    public boolean canMove(int newX, int newY, int[][] shape) {
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

    /**
     * Verifica si todas las celdas del tablero contienen valores válidos.
     *
     * @return `true` si todas las celdas son válidas; `false` de lo contrario.
     */
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

    /**
     * Valida que los índices de fila y columna estén dentro de los límites del tablero.
     *
     * @param row Índice de fila.
     * @param col Índice de columna.
     * @throws IndexOutOfBoundsException si los índices están fuera de rango.
     */
    private void validateIndices(int row, int col) {
        if (row < 0 || row >= rows) {
            throw new IndexOutOfBoundsException("Índice de fila fuera de rango: " + row);
        }
        if (col < 0 || col >= cols) {
            throw new IndexOutOfBoundsException("Índice de columna fuera de rango: " + col);
        }
    }
}
