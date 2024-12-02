package controller;

import model.GameBoard;
import org.junit.jupiter.api.Test;

import javax.swing.JPanel;
import java.awt.event.KeyEvent;

import static org.mockito.Mockito.*;

class GameControllerTestWithMocks {

    /**
     * Prueba que verifica que el GameController invoca el método moveTetrominoLeft() en GameBoard al presionar la
     * tecla izquierda.
     *
     * Se utiliza un mock de GameBoard y un mock del repaintCallback para aislar el GameController.
     * Esto permite verificar que el GameController interactúa correctamente con sus dependencias sin depender de su implementación real.
     */
    @Test
    void testMoveTetrominoLeft_MockGameBoard() {
        // Crear un mock del GameBoard
        GameBoard mockGameBoard = mock(GameBoard.class);

        // Configurar el mock para que allCellsAreValid() devuelva true
        when(mockGameBoard.allCellsAreValid()).thenReturn(true);

        // Crear un mock del repaintCallback
        Runnable mockRepaintCallback = mock(Runnable.class);

        // Instanciar el GameController con los mocks
        GameController gameController = new GameController(mockGameBoard, mockRepaintCallback);

        // Simular la pulsación de la tecla izquierda
        KeyEvent leftKey = new KeyEvent(new JPanel(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, 'A');
        gameController.keyPressed(leftKey);

        // Verificar que se llamó al método moveTetrominoLeft en el GameBoard
        verify(mockGameBoard).moveTetrominoLeft();

        // Verificar que se ejecutó el repaintCallback
        verify(mockRepaintCallback).run();
    }

    /**
     * Prueba que verifica que el GameController no realiza acciones cuando se presionan teclas inválidas.
     *
     * Se utiliza un mock de GameBoard y se simula la pulsación de una tecla no asignada a ninguna acción.
     * Se verifica que no se invoca ningún método en GameBoard, asegurando que el GameController maneja correctamente
     * entradas inválidas.
     */

    @Test
    void testInvalidKeyPress() {
        GameBoard mockGameBoard = mock(GameBoard.class);
        Runnable mockRepaintCallback = mock(Runnable.class);
        GameController gameController = new GameController(mockGameBoard, mockRepaintCallback);

        // Simular una tecla no válida
        MockKeyEvent invalidKey = new MockKeyEvent(KeyEvent.VK_A);
        gameController.keyPressed(invalidKey);

        // Verificar que no se llamó a ningún método del GameBoard
        verifyNoInteractions(mockGameBoard);
    }

}
