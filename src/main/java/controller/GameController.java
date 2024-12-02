package controller;

import model.GameBoard;
import model.GameOverException;

import javax.swing.Timer;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Controlador del juego que maneja la interacción del usuario mediante teclas.
 *
 * **Invariantes de Clase:**
 * - `gameBoard` nunca es `null`.
 * - `repaintCallback` nunca es `null`.
 * - `keyQueue` no es `null` y contiene códigos de teclas válidos.
 * - Si `keyHoldTimer` está activo, entonces `keyQueue` no está vacía.
 */
public class GameController implements KeyListener {
    private final GameBoard gameBoard;
    private final Runnable repaintCallback;
    private Timer keyHoldTimer; // Temporizador para movimientos continuos
    private final Queue<Integer> keyQueue; // Cola para manejar teclas activas

    /**
     * Constructor del GameController.
     *
     * **Precondiciones:**
     * - `gameBoard` no es `null`.
     * - `repaintCallback` no es `null`.
     *
     * **Postcondiciones:**
     * - `gameBoard` y `repaintCallback` se inicializan con los valores proporcionados.
     * - `keyQueue` se inicializa vacía.
     *
     * @param gameBoard       Instancia del tablero del juego.
     * @param repaintCallback Función a ejecutar para repintar la interfaz.
     * @throws IllegalArgumentException si `gameBoard` o `repaintCallback` son `null`.
     */
    public GameController(GameBoard gameBoard, Runnable repaintCallback) {
        if (gameBoard == null) {
            throw new IllegalArgumentException("El GameBoard no puede ser null.");
        }
        if (repaintCallback == null) {
            throw new IllegalArgumentException("El repaintCallback no puede ser null.");
        }

        this.gameBoard = gameBoard;
        this.repaintCallback = repaintCallback;
        this.keyQueue = new LinkedList<>();

        // Invariante: La cola debe estar vacía al inicio
        if (!keyQueue.isEmpty()) {
            throw new IllegalStateException("Invariante fallida: La cola debe estar vacía después de inicializar.");
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e == null) {
            throw new IllegalArgumentException("El evento KeyEvent no puede ser null.");
        }

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
        if (!keyQueue.contains(keyCode)) {
            throw new IllegalStateException("Postcondición fallida: La tecla debe estar en la cola después de presionarla.");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e == null) {
            throw new IllegalArgumentException("El evento KeyEvent no puede ser null.");
        }

        int keyCode = e.getKeyCode();
        keyQueue.remove(keyCode);

        // Detener el temporizador si no quedan teclas activas
        if (keyQueue.isEmpty() && keyHoldTimer != null) {
            keyHoldTimer.stop();
            keyHoldTimer = null;
        }

        // Postcondición: La tecla no debe estar en la cola después de ser liberada
        if (keyQueue.contains(keyCode)) {
            throw new IllegalStateException("Postcondición fallida: La tecla no debe estar en la cola después de liberarla.");
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Este evento no se maneja
        if (e == null) {
            throw new IllegalArgumentException("El evento KeyEvent no puede ser null.");
        }
    }

    /**
     * Procesa la cola de teclas activas, ejecutando las acciones correspondientes.
     *
     * **Precondiciones:**
     * - `keyQueue` no está vacía.
     *
     * **Postcondiciones:**
     * - Se ejecutan las acciones correspondientes a las teclas en la cola.
     * - El estado del `gameBoard` sigue siendo válido.
     *
     * @throws IllegalStateException si `keyQueue` está vacía al llamar al método.
     */
    private void processKeyQueue() {
        if (keyQueue.isEmpty()) {
            throw new IllegalStateException("Precondición fallida: La cola no debe estar vacía al procesarla.");
        }

        for (int keyCode : keyQueue) {
            executeKeyAction(keyCode);
        }

        // Postcondición: El estado del tablero debe ser válido después de procesar las teclas
        if (!gameBoard.allCellsAreValid()) {
            throw new IllegalStateException("Postcondición fallida: El tablero contiene celdas inválidas después de procesar la cola.");
        }
    }

    /**
     * Ejecuta la acción correspondiente a una tecla.
     *
     * **Precondiciones:**
     * - `keyCode` es una tecla válida según `isValidKey(keyCode)`.
     *
     * **Postcondiciones:**
     * - Se actualiza el estado del `gameBoard` de acuerdo a la acción.
     * - El estado del `gameBoard` sigue siendo válido.
     *
     * @param keyCode Código de la tecla presionada.
     * @throws IllegalArgumentException si `keyCode` no es una tecla válida.
     */
    private void executeKeyAction(int keyCode) {
        try {
            if (gameBoard.isGameOver()) {
                throw new GameOverException("El juego ha terminado: no se pueden realizar acciones.");
            }

            switch (keyCode) {
                case KeyEvent.VK_LEFT -> gameBoard.moveTetrominoLeft();
                case KeyEvent.VK_RIGHT -> gameBoard.moveTetrominoRight();
                case KeyEvent.VK_DOWN -> gameBoard.moveTetrominoDown();
                case KeyEvent.VK_UP -> gameBoard.rotateTetromino();
            }
            repaintCallback.run();
        } catch (GameOverException ex) {
            System.out.println(ex.getMessage());
        }
    }


    /**
     * Verifica si una tecla es válida para este controlador.
     *
     * **Postcondiciones:**
     * - Devuelve `true` si la tecla es una de las teclas permitidas.
     *
     * @param keyCode Código de la tecla.
     * @return `true` si es una tecla válida, `false` en caso contrario.
     */
    private boolean isValidKey(int keyCode) {
        return keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT ||
                keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_UP;
    }
}
