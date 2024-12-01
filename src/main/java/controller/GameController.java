package controller;

import model.GameBoard;

import javax.swing.Timer;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameController implements KeyListener {
    private final GameBoard gameBoard;
    private final Runnable repaintCallback;
    private Timer keyHoldTimer;
    private int currentKey;

    public GameController(GameBoard gameBoard, Runnable repaintCallback) {
        this.gameBoard = gameBoard;
        this.repaintCallback = repaintCallback;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (keyHoldTimer == null || !keyHoldTimer.isRunning()) {
            currentKey = e.getKeyCode();
            executeKeyAction(currentKey);
            keyHoldTimer = new Timer(100, evt -> executeKeyAction(currentKey));
            keyHoldTimer.start();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (keyHoldTimer != null && e.getKeyCode() == currentKey) {
            keyHoldTimer.stop();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // No se maneja
    }

    private void executeKeyAction(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_LEFT -> gameBoard.moveTetrominoLeft();
            case KeyEvent.VK_RIGHT -> gameBoard.moveTetrominoRight();
            case KeyEvent.VK_DOWN -> gameBoard.moveTetrominoDown();
            case KeyEvent.VK_UP -> gameBoard.rotateTetromino();
        }
        repaintCallback.run();
    }
}