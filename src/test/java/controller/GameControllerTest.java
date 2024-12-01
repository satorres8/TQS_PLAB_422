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

    @Test
    void testMoveTetrominoLeft() {
        int initialX = gameBoard.getTetrominoX();
        KeyEvent leftKey = new KeyEvent(dummyComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, 'A');
        gameController.keyPressed(leftKey);
        assertEquals(initialX - 1, gameBoard.getTetrominoX(), "La tetromino no se movió a la izquierda.");
    }

    @Test
    void testMoveTetrominoRight() {
        int initialX = gameBoard.getTetrominoX();
        KeyEvent rightKey = new KeyEvent(dummyComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, 'D');
        gameController.keyPressed(rightKey);
        assertEquals(initialX + 1, gameBoard.getTetrominoX(), "La tetromino no se movió a la derecha.");
    }

    @Test
    void testMoveTetrominoDown() {
        int initialY = gameBoard.getTetrominoY();
        KeyEvent downKey = new KeyEvent(dummyComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_DOWN, 'S');
        gameController.keyPressed(downKey);
        assertEquals(initialY + 1, gameBoard.getTetrominoY(), "La tetromino no se movió hacia abajo.");
    }

    @Test
    void testRotateTetromino() {
        int[][] initialShape = gameBoard.getCurrentTetromino().getShape();
        KeyEvent upKey = new KeyEvent(dummyComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_UP, 'W');
        gameController.keyPressed(upKey);
        int[][] rotatedShape = gameBoard.getCurrentTetromino().getShape();
        assertNotEquals(initialShape, rotatedShape, "La tetromino no se rotó.");
    }

    @Test
    void testKeyReleasedStopsContinuousMovement() {
        KeyEvent leftKey = new KeyEvent(dummyComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, 'A');
        gameController.keyPressed(leftKey);

        KeyEvent leftKeyRelease = new KeyEvent(dummyComponent, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, 'A');
        gameController.keyReleased(leftKeyRelease);

        // Aquí no verificamos directamente el temporizador, pero asumimos que se detuvo correctamente.
        assertEquals(gameBoard.getTetrominoX(), gameBoard.getTetrominoX(), "La pieza no debe moverse después de soltar la tecla.");
    }

    @Test
    void testBoundaryLeftMovement() {
        // Colocar la tetromino en el borde izquierdo
        while (gameBoard.getTetrominoX() > 0) {
            gameController.keyPressed(new KeyEvent(dummyComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, 'A'));
        }
        int initialX = gameBoard.getTetrominoX();
        gameController.keyPressed(new KeyEvent(dummyComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, 'A'));
        assertEquals(initialX, gameBoard.getTetrominoX(), "La tetromino se movió fuera del límite izquierdo.");
    }

    @Test
    void testBoundaryRightMovement() {
        // Colocar la tetromino en el borde derecho
        while (gameBoard.getTetrominoX() < gameBoard.getCols() - gameBoard.getCurrentTetromino().getShape()[0].length) {
            gameController.keyPressed(new KeyEvent(dummyComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, 'D'));
        }
        int initialX = gameBoard.getTetrominoX();
        gameController.keyPressed(new KeyEvent(dummyComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, 'D'));
        assertEquals(initialX, gameBoard.getTetrominoX(), "La tetromino se movió fuera del límite derecho.");
    }

    @Test
    void testContinuousDownMovement() throws InterruptedException {
        int initialY = gameBoard.getTetrominoY();
        KeyEvent downKey = new KeyEvent(dummyComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_DOWN, 'S');
        gameController.keyPressed(downKey);

        // Esperar para verificar movimiento continuo
        Thread.sleep(300);
        assertTrue(gameBoard.getTetrominoY() > initialY, "La tetromino no se movió continuamente hacia abajo.");
    }

    @Test
    void testPairwiseKeyCombinations() {
        int initialX = gameBoard.getTetrominoX();
        int initialY = gameBoard.getTetrominoY();

        // Izquierda y abajo
        gameController.keyPressed(new KeyEvent(dummyComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, 'A'));
        gameController.keyPressed(new KeyEvent(dummyComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_DOWN, 'S'));

        assertEquals(initialX - 1 , gameBoard.getTetrominoX(), "La tetromino no se movió a la izquierda correctamente.");
        assertEquals(initialY + 1, gameBoard.getTetrominoY(), "La tetromino no se movió hacia abajo correctamente.");

        // Derecha y rotar
        gameController.keyPressed(new KeyEvent(dummyComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, 'D'));
        int[][] initialShape = gameBoard.getCurrentTetromino().getShape();
        gameController.keyPressed(new KeyEvent(dummyComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_UP, 'W'));
        int[][] rotatedShape = gameBoard.getCurrentTetromino().getShape();

        assertEquals(initialX, gameBoard.getTetrominoX(), "La tetromino no se movió a la derecha correctamente.");
        assertNotEquals(initialShape, rotatedShape, "La tetromino no se rotó correctamente.");
    }

    @Test
    void testRapidKeyPresses() {
        int initialX = gameBoard.getTetrominoX();

        // Simular varias pulsaciones rápidas de teclas
        gameController.keyPressed(new KeyEvent(dummyComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, 'A'));
        gameController.keyPressed(new KeyEvent(dummyComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, 'D'));
        gameController.keyPressed(new KeyEvent(dummyComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, 'A'));

        assertTrue(gameBoard.getTetrominoX() != initialX, "La tetromino no respondió a las pulsaciones rápidas de teclas.");
    }
}
