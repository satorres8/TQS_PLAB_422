package controller;

import model.GameBoard;

import javax.swing.Timer;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.Queue;

public class GameController implements KeyListener {
    private final GameBoard gameBoard;
    private final Runnable repaintCallback;
    private Timer keyHoldTimer; // Temporizador para movimientos continuos
    private final Queue<Integer> keyQueue; // Cola para manejar teclas activas

    public GameController(GameBoard gameBoard, Runnable repaintCallback) {
        assert gameBoard != null : "Precondición fallida: El GameBoard no puede ser null.";
        assert repaintCallback != null : "Precondición fallida: El repaintCallback no puede ser null.";

        this.gameBoard = gameBoard;
        this.repaintCallback = repaintCallback;
        this.keyQueue = new LinkedList<>();

        // Invariante: La cola debe estar vacía al inicio
        assert keyQueue.isEmpty() : "Invariante fallida: La cola debe estar vacía después de inicializar.";
    }

    @Override
    public void keyPressed(KeyEvent e) {
        assert e != null : "Precondición fallida: El evento KeyEvent no puede ser null.";

        int keyCode = e.getKeyCode();
        if (!isValidKey(keyCode)) {
            return;
        }

        // Añadir la tecla a la cola si no está ya en la cola
        if (!keyQueue.contains(keyCode)) {
            keyQueue.add(keyCode);
        }

        // Manejar movimiento continuo si no hay un temporizador activo
        if (keyHoldTimer == null || !keyHoldTimer.isRunning()) {
            keyHoldTimer = new Timer(100, evt -> processKeyQueue());
            keyHoldTimer.start();
        }

        // Ejecutar inmediatamente la acción de la tecla actual
        executeKeyAction(keyCode);

        // Postcondición: La tecla debe estar en la cola después de ser presionada
        assert keyQueue.contains(keyCode) : "Postcondición fallida: La tecla debe estar en la cola después de presionarla.";
    }

    @Override
    public void keyReleased(KeyEvent e) {
        assert e != null : "Precondición fallida: El evento KeyEvent no puede ser null.";

        int keyCode = e.getKeyCode();
        keyQueue.remove(keyCode);

        // Detener el temporizador si no quedan teclas activas
        if (keyQueue.isEmpty() && keyHoldTimer != null) {
            keyHoldTimer.stop();
        }

        // Postcondición: La tecla no debe estar en la cola después de ser liberada
        assert !keyQueue.contains(keyCode) : "Postcondición fallida: La tecla no debe estar en la cola después de liberarla.";
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Este evento no se maneja
        assert e != null : "Precondición fallida: El evento KeyEvent no puede ser null.";
    }

    /**
     * Procesa la cola de teclas activas, ejecutando las acciones correspondientes.
     */
    private void processKeyQueue() {
        assert !keyQueue.isEmpty() : "Precondición fallida: La cola no debe estar vacía al procesarla.";

        for (int keyCode : keyQueue) {
            executeKeyAction(keyCode);
        }

        // Postcondición: El estado del tablero debe ser válido después de procesar las teclas
        assert gameBoard.allCellsAreValid() : "Postcondición fallida: El tablero contiene celdas inválidas después de procesar la cola.";
    }

    /**
     * Ejecuta la acción correspondiente a una tecla.
     *
     * @param keyCode Código de la tecla presionada.
     */
    private void executeKeyAction(int keyCode) {
        assert isValidKey(keyCode) : "Precondición fallida: La tecla debe ser válida para ejecutar la acción.";

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
            case KeyEvent.VK_DOWN -> gameBoard.moveTetrominoDown();
            case KeyEvent.VK_UP -> gameBoard.rotateTetromino();
        }
        repaintCallback.run();

        // Postcondición: El estado del tablero debe ser válido después de cada acción
        assert gameBoard.allCellsAreValid() : "Postcondición fallida: El tablero contiene celdas inválidas después de ejecutar la acción.";
    }

    /**
     * Verifica si una tecla es válida para este controlador.
     *
     * @param keyCode Código de la tecla.
     * @return true si es una tecla válida, false en caso contrario.
     */
    private boolean isValidKey(int keyCode) {
        return keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT ||
                keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_UP;
    }
}