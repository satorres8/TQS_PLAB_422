package view;

import model.GameBoard;
import model.Tetromino;

import javax.swing.*;
import java.awt.*;

public class GameView extends JPanel {
    private final GameBoard gameBoard;
    private static final int CELL_SIZE = 30;

    public GameView(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        setPreferredSize(new Dimension(gameBoard.getCols() * CELL_SIZE, gameBoard.getRows() * CELL_SIZE + 30));
        setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dibujar puntaje
        g.setColor(Color.WHITE);
        g.drawString("Score: " + gameBoard.getScore(), 10, 20);

        // Dibujar el tablero
        for (int row = 0; row < gameBoard.getRows(); row++) {
            for (int col = 0; col < gameBoard.getCols(); col++) {
                int cell = gameBoard.getCell(row, col);
                if (cell != 0) {
                    g.setColor(getColor(cell));
                    g.fillRect(col * CELL_SIZE, row * CELL_SIZE + 30, CELL_SIZE, CELL_SIZE);
                }
                g.setColor(Color.DARK_GRAY);
                g.drawRect(col * CELL_SIZE, row * CELL_SIZE + 30, CELL_SIZE, CELL_SIZE);
            }
        }

        // Dibujar Tetromino activo
        Tetromino currentTetromino = gameBoard.getCurrentTetromino();
        if (currentTetromino != null) {
            int[][] shape = currentTetromino.getShape();
            int x = gameBoard.getTetrominoX();
            int y = gameBoard.getTetrominoY();

            for (int row = 0; row < shape.length; row++) {
                for (int col = 0; col < shape[row].length; col++) {
                    if (shape[row][col] == 1) {
                        g.setColor(getColor(currentTetromino.getType().ordinal() + 1));
                        g.fillRect((x + col) * CELL_SIZE, (y + row) * CELL_SIZE + 30, CELL_SIZE, CELL_SIZE);
                    }
                }
            }
        }
    }

    private Color getColor(int type) {
        return switch (type) {
            case 1 -> Color.CYAN;    // I
            case 2 -> Color.YELLOW;  // O
            case 3 -> Color.MAGENTA; // T
            case 4 -> Color.GREEN;   // S
            case 5 -> Color.RED;     // Z
            case 6 -> Color.BLUE;    // J
            case 7 -> Color.ORANGE;  // L
            default -> Color.GRAY;   // Vacío o desconocido
        };
    }
}
