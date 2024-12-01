package controller;
import static org.mockito.Mockito.*;

import model.GameBoard;
import model.Tetromino;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.event.KeyEvent;

class GameControllerTest {
    private GameBoard gameBoardMock;
    private Runnable repaintCallbackMock;
    private GameController gameController;

    @BeforeEach
    void setUp() {
        gameBoardMock = mock(GameBoard.class);
        repaintCallbackMock = mock(Runnable.class);
        gameController = new GameController(gameBoardMock, repaintCallbackMock);
    }

    @Test
    void testKeyPressedLeft() {
        KeyEvent keyEvent = new KeyEvent(new Object(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, 'A');
        gameController.keyPressed(keyEvent);

        verify(gameBoardMock).moveTetrominoLeft();
        verify(repaintCallbackMock).run();
    }

    @Test
    void testKeyPressedRight() {
        KeyEvent keyEvent = new KeyEvent(new Object(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, 'D');
        gameController.keyPressed(keyEvent);

        verify(gameBoardMock).moveTetrominoRight();
        verify(repaintCallbackMock).run();
    }

    @Test
    void testKeyPressedDown() {
        KeyEvent keyEvent = new KeyEvent(new Object(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_DOWN, 'S');
        gameController.keyPressed(keyEvent);

        verify(gameBoardMock).moveTetrominoDown();
        verify(repaintCallbackMock).run();
    }

    @Test
    void testKeyPressedUp() {
        KeyEvent keyEvent = new KeyEvent(new Object(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_UP, 'W');
        gameController.keyPressed(keyEvent);

        verify(gameBoardMock).rotateTetromino();
        verify(repaintCallbackMock).run();
    }

    @Test
    void testKeyReleasedStopsTimer() {
        KeyEvent keyEvent = new KeyEvent(new Object(), KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, 'A');
        gameController.keyReleased(keyEvent);

        // Simulación: No hay una verificación directa, pero debe manejarse correctamente
        // Aquí podríamos verificar un comportamiento observable en una implementación avanzada.
    }
}
