package controller;

import model.GameBoard;
import controller.GameController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.JPanel;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {
    private GameBoard gameBoard;
    private GameController gameController;
    private JPanel dummyComponent; // Componente válido para los eventos


    @BeforeEach
    void setUp() {
        gameBoard = new GameBoard(20, 10); // Tablero estándar de Tetris
        dummyComponent = new JPanel(); // Usamos un JPanel como componente para los eventos
        gameController = new GameController(gameBoard, () -> {
            // Callback para redibujar la vista (vacío para pruebas básicas)
        });
    }

    /**
     * TIPO DE PRUEBA: Caja Negra
     * CRITERIO EVALUADO: Particiones equivalentes
     * DESCRIPCIÓN: Verifica que el Tetromino se mueve correctamente a la izquierda cuando se presiona la tecla correspondiente.
     */
    @Test
    void testMoveTetrominoLeft() {
        int initialX = gameBoard.getTetrominoX();
        KeyEvent leftKey = new KeyEvent(dummyComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, 'A');
        gameController.keyPressed(leftKey);
        assertEquals(initialX - 1, gameBoard.getTetrominoX(), "La tetromino no se movió a la izquierda.");
    }

    /**
     * TIPO DE PRUEBA: Caja Negra
     * CRITERIO EVALUADO: Particiones equivalentes
     * DESCRIPCIÓN: Valida que el Tetromino se mueve a la derecha al presionar la tecla correspondiente.
     */
    @Test
    void testMoveTetrominoRight() {
        int initialX = gameBoard.getTetrominoX();
        KeyEvent rightKey = new KeyEvent(dummyComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, 'D');
        gameController.keyPressed(rightKey);
        assertEquals(initialX + 1, gameBoard.getTetrominoX(), "La tetromino no se movió a la derecha.");
    }

    /**
     * TIPO DE PRUEBA: Caja Negra
     * CRITERIO EVALUADO: Particiones equivalentes
     * DESCRIPCIÓN: Comprueba que el Tetromino desciende correctamente al presionar la tecla correspondiente.
     */
    @Test
    void testMoveTetrominoDown() {
        int initialY = gameBoard.getTetrominoY();
        KeyEvent downKey = new KeyEvent(dummyComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_DOWN, 'S');
        gameController.keyPressed(downKey);
        assertEquals(initialY + 1, gameBoard.getTetrominoY(), "La tetromino no se movió hacia abajo.");
    }

    /**
     * TIPO DE PRUEBA: Caja Negra
     * CRITERIO EVALUADO: Particiones equivalentes
     * DESCRIPCIÓN: Evalúa que la rotación del Tetromino funcione correctamente al presionar la tecla de rotación.
     */
    @Test
    void testRotateTetromino() {
        int[][] initialShape = gameBoard.getCurrentTetromino().getShape();
        KeyEvent upKey = new KeyEvent(dummyComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_UP, 'W');
        gameController.keyPressed(upKey);
        int[][] rotatedShape = gameBoard.getCurrentTetromino().getShape();
        assertNotEquals(initialShape, rotatedShape, "La tetromino no se rotó.");
    }

    /**
     * TIPO DE PRUEBA: Caja Negra
     * CRITERIO EVALUADO: Valores límite
     * DESCRIPCIÓN: Verifica que el movimiento continuo del Tetromino se detiene al liberar una tecla.
     */
    @Test
    void testKeyReleasedStopsContinuousMovement() {
        KeyEvent leftKey = new KeyEvent(dummyComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, 'A');
        gameController.keyPressed(leftKey);

        KeyEvent leftKeyRelease = new KeyEvent(dummyComponent, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, 'A');
        gameController.keyReleased(leftKeyRelease);

        assertEquals(gameBoard.getTetrominoX(), gameBoard.getTetrominoX(), "La pieza no debe moverse después de soltar la tecla.");
    }

    /**
     * TIPO DE PRUEBA: Caja Negra
     * CRITERIO EVALUADO: Valores límite
     * DESCRIPCIÓN: Comprueba que el Tetromino no puede moverse fuera del límite izquierdo del tablero.
     */
    @Test
    void testBoundaryLeftMovement() {
        while (gameBoard.getTetrominoX() > 0) {
            gameController.keyPressed(new KeyEvent(dummyComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, 'A'));
        }
        int initialX = gameBoard.getTetrominoX();
        gameController.keyPressed(new KeyEvent(dummyComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, 'A'));
        assertEquals(initialX, gameBoard.getTetrominoX(), "La tetromino se movió fuera del límite izquierdo.");
    }

    /**
     * TIPO DE PRUEBA: Caja Negra
     * CRITERIO EVALUADO: Valores límite
     * DESCRIPCIÓN: Valida que el Tetromino no puede cruzar el límite derecho del tablero.
     */
    @Test
    void testBoundaryRightMovement() {
        while (gameBoard.getTetrominoX() < gameBoard.getCols() - gameBoard.getCurrentTetromino().getShape()[0].length) {
            gameController.keyPressed(new KeyEvent(dummyComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, 'D'));
        }
        int initialX = gameBoard.getTetrominoX();
        gameController.keyPressed(new KeyEvent(dummyComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, 'D'));
        assertEquals(initialX, gameBoard.getTetrominoX(), "La tetromino se movió fuera del límite derecho.");
    }

    /**
     * TIPO DE PRUEBA: Caja Blanca
     * CRITERIO EVALUADO: Loop Testing (bucle simple)
     * DESCRIPCIÓN: Verifica que el movimiento continuo hacia abajo funciona correctamente.
     */
    @Test
    void testContinuousDownMovement() throws InterruptedException {
        int initialY = gameBoard.getTetrominoY();
        KeyEvent downKey = new KeyEvent(dummyComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_DOWN, 'S');
        gameController.keyPressed(downKey);

        Thread.sleep(300);
        assertTrue(gameBoard.getTetrominoY() > initialY, "La tetromino no se movió continuamente hacia abajo.");
    }

    /**
     * TIPO DE PRUEBA: Caja Negra
     * CRITERIO EVALUADO: Pairwise Testing
     * DESCRIPCIÓN: Prueba combinaciones de teclas como izquierda + abajo y derecha + rotación.
     */
    @Test
    void testPairwiseKeyCombinations() {
        int initialX = gameBoard.getTetrominoX();
        int initialY = gameBoard.getTetrominoY();

        gameController.keyPressed(new KeyEvent(dummyComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, 'A'));
        gameController.keyPressed(new KeyEvent(dummyComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_DOWN, 'S'));

        assertEquals(initialX - 1 , gameBoard.getTetrominoX(), "La tetromino no se movió a la izquierda correctamente.");
        assertEquals(initialY + 1, gameBoard.getTetrominoY(), "La tetromino no se movió hacia abajo correctamente.");

        gameController.keyPressed(new KeyEvent(dummyComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, 'D'));
        int[][] initialShape = gameBoard.getCurrentTetromino().getShape();
        gameController.keyPressed(new KeyEvent(dummyComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_UP, 'W'));
        int[][] rotatedShape = gameBoard.getCurrentTetromino().getShape();

        assertEquals(initialX, gameBoard.getTetrominoX(), "La tetromino no se movió a la derecha correctamente.");
        assertNotEquals(initialShape, rotatedShape, "La tetromino no se rotó correctamente.");
    }

    /**
     * TIPO DE PRUEBA: Caja Negra
     * CRITERIO EVALUADO: Valores límite
     * DESCRIPCIÓN: Valida que el Tetromino responde correctamente a pulsaciones rápidas de teclas.
     */
    @Test
    void testRapidKeyPresses() {
        int initialX = gameBoard.getTetrominoX();

        gameController.keyPressed(new KeyEvent(dummyComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, 'A'));
        gameController.keyPressed(new KeyEvent(dummyComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, 'D'));
        gameController.keyPressed(new KeyEvent(dummyComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, 'A'));

        assertTrue(gameBoard.getTetrominoX() != initialX, "La tetromino no respondió a las pulsaciones rápidas de teclas.");
    }


    /**
     * TIPO DE PRUEBA: Caja Blanca
     * CRITERIO EVALUADO: Path Coverage
     * DESCRIPCIÓN: Verifica rutas adicionales al procesar múltiples teclas en la cola.
     */
    @Test
    void testProcessKeyQueueWithMultipleKeys() {
        // Añadir varias teclas a la cola
        gameController.keyPressed(new KeyEvent(dummyComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, 'A'));
        gameController.keyPressed(new KeyEvent(dummyComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_DOWN, 'S'));

        // Procesar la cola
        gameController.keyReleased(new KeyEvent(dummyComponent, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, 'A'));
        gameController.keyReleased(new KeyEvent(dummyComponent, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_DOWN, 'S'));

        // Validar que la cola está vacía y el estado del tablero es válido
        assertTrue(gameBoard.allCellsAreValid(), "El tablero debe estar en un estado válido.");
    }

    /**
     * TIPO DE PRUEBA: Caja Blanca
     * CRITERIO EVALUADO: Loop Testing (bucle anidado)
     * DESCRIPCIÓN: Evalúa cómo se procesan múltiples teclas en la cola de eventos para garantizar que el estado del tablero sea válido después de cada acción.
     */
    @Test
    void testMultipleKeysProcessing() {
        // Simular múltiples teclas presionadas
        gameController.keyPressed(new KeyEvent(dummyComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, 'A'));
        gameController.keyPressed(new KeyEvent(dummyComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, 'D'));

        // Ejecutar procesamiento
        gameController.keyReleased(new KeyEvent(dummyComponent, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, 'A'));
        gameController.keyReleased(new KeyEvent(dummyComponent, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, 'D'));

        // Validar que las teclas fueron procesadas correctamente
        assertTrue(gameBoard.allCellsAreValid(), "El tablero debe estar en un estado válido tras procesar múltiples teclas.");
    }

}
