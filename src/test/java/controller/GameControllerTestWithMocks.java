package controller;

import model.GameBoard;
import org.junit.jupiter.api.Test;

import javax.swing.JPanel;
import java.awt.event.KeyEvent;

import static org.mockito.Mockito.*;

/**
 * Pruebas para la clase GameController utilizando MockObjects.
 */
class GameControllerTestWithMocks {

    /**
     * TIPO DE PRUEBA: Caja Negra con Mocks (Mockito)
     * CRITERIO EVALUADO: Interacción con dependencias y aislamiento
     * DESCRIPCIÓN: Verifica que el GameController invoca el método moveTetrominoLeft() en GameBoard al
     * presionar la tecla izquierda.
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
        KeyEvent leftKey = new KeyEvent(new JPanel(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
                KeyEvent.VK_LEFT, 'A');
        gameController.keyPressed(leftKey);

        // Verificar que se llamó al método moveTetrominoLeft en el GameBoard
        verify(mockGameBoard).moveTetrominoLeft();

        // Verificar que se ejecutó el repaintCallback
        verify(mockRepaintCallback).run();
    }

    /**
     * TIPO DE PRUEBA: Caja Negra con Mocks (Mockito)
     * CRITERIO EVALUADO: Manejo de entradas inválidas y robustez
     * DESCRIPCIÓN: Verifica que el GameController no realiza acciones cuando se presionan teclas inválidas.
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
