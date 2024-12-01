package controller;

import model.GameBoard;

import javax.swing.Timer;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Queue;
import java.util.LinkedList;

public class GameController implements KeyListener {
    private final GameBoard gameBoard;
    private final Runnable repaintCallback;
    private Timer keyHoldTimer;
    private int currentKey;
    private final Queue<Integer> actionQueue;

    public GameController(GameBoard gameBoard, Runnable repaintCallback) {
        this.gameBoard = gameBoard;
        this.repaintCallback = repaintCallback;
        this.actionQueue = new LinkedList<>();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        // Añadir la acción a la cola si es una tecla válida
        if (isValidKey(keyCode)) {
            actionQueue.add(keyCode);
            processNextAction();
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

    private void processNextAction() {
        while (!actionQueue.isEmpty()) {
            int keyCode = actionQueue.poll();
            executeKeyAction(keyCode);
        }
    }

    private void executeKeyAction(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_LEFT -> {
                if (gameBoard.getTetrominoX() > 0) {
                    gameBoard.moveTetrominoLeft();
                }
            }
            case KeyEvent.VK_RIGHT -> {
                if (gameBoard.getTetrominoX() < gameBoard.getCols() - gameBoard.getCurrentTetromino().getShape()[0].length) {
                    gameBoard.moveTetrominoRight();
                }
            }
            case KeyEvent.VK_DOWN -> {
                // Garantiza que `moveTetrominoDown` actualice correctamente el estado
                if (gameBoard.getTetrominoY() < gameBoard.getRows() - gameBoard.getCurrentTetromino().getShape().length) {
                    gameBoard.moveTetrominoDown();
                }
            }
            case KeyEvent.VK_UP -> {
                // Rotar sólo si es válido
                gameBoard.rotateTetromino();
            }
        }
        repaintCallback.run(); // Redibuja la vista después de cada acción
    }

    private boolean isValidKey(int keyCode) {
        return keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT ||
                keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_UP;
    }
}