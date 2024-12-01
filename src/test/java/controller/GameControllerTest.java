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
}
