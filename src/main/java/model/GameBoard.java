package model;

public class GameBoard {
    private int rows;
    private int cols;
    private int[][] board; // Representa el estado del tablero (0: vacío, 1: ocupado).

    public GameBoard() {
        this.rows = 20; // Tamaño estándar del tablero de Tetris.
        this.cols = 10;
        this.board = new int[rows][cols];
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getCell(int row, int col) {
        return board[row][col];
    }

    public void setCell(int row, int col, int value) {
        board[row][col] = value;
    }

    public boolean placePiece(Tetromino piece, int row, int col) {
        int[][] shape = piece.getShape();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] == 1) {
                    int boardRow = row + i;
                    int boardCol = col + j;
                    if (boardRow >= rows || boardCol < 0 || boardCol >= cols || board[boardRow][boardCol] == 1) {
                        return false; // Colisión detectada.
                    }
                }
            }
        }

        // Coloca la pieza en el tablero.
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] == 1) {
                    board[row + i][col + j] = 1;
                }
            }
        }
        return true;
    }

    public boolean canPlacePiece(Tetromino piece, int row, int col) {
        int[][] shape = piece.getShape();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] == 1) {
                    int boardRow = row + i;
                    int boardCol = col + j;
                    if (boardRow >= rows || boardCol < 0 || boardCol >= cols || board[boardRow][boardCol] == 1) {
                        return false; // Colisión detectada.
                    }
                }
            }
        }
        return true;
    }

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
        return linesCleared;
    }

    private void clearLine(int line) {
        for (int i = line; i > 0; i--) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = board[i - 1][j];
            }
        }
        // Limpia la primera fila.
        for (int j = 0; j < cols; j++) {
            board[0][j] = 0;
        }
    }
}