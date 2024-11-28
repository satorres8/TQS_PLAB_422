package model;

/**
 * Clase que representa el tablero de juego de Tetris.
 */
public class GameBoard {
    private int rows;
    private int cols;
    private int[][] board;

    /**
     * Constructor para inicializar el tablero con las dimensiones especificadas.
     * Precondiciones:
     * - rows > 0
     * - cols > 0
     * Postcondiciones:
     * - El tablero se inicializa con todas las celdas en 0.
     */
    public GameBoard(int rows, int cols) {
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Las dimensiones del tablero deben ser mayores a 0.");
        }
        this.rows = rows;
        this.cols = cols;
        this.board = new int[rows][cols];
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getCell(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            throw new IndexOutOfBoundsException("Índices fuera del rango del tablero.");
        }
        return board[row][col];
    }

    public void setCell(int row, int col, int value) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            throw new IndexOutOfBoundsException("Índices fuera del rango del tablero.");
        }
        board[row][col] = value;
    }
}