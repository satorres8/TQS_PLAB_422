package controller;

import model.GameBoard;
import java.awt.event.KeyEvent;

public class GameController {
    private final GameBoard gameBoard;
    private final Runnable repaintCallback;

    public GameController(GameBoard gameBoard, Runnable repaintCallback) {
        this.gameBoard = gameBoard;
        this.repaintCallback = repaintCallback;
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> gameBoard.moveTetrominoLeft();
            case KeyEvent.VK_RIGHT -> gameBoard.moveTetrominoRight();
            case KeyEvent.VK_DOWN -> gameBoard.moveTetrominoDown();
            case KeyEvent.VK_UP -> gameBoard.rotateTetromino();
        }
        repaintCallback.run();
    }

    public void keyReleased(KeyEvent e) {
        // No implementado aún (Placeholder para detener el temporizador)
    }
}