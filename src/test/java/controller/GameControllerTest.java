package controller;

import model.GameBoard;
import model.Tetromino;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {

    @Test
    void testGameStart() {
        GameBoard board = new GameBoard();
        GameController controller = new GameController(board, null);
        controller.startGame();
        assertNotNull(controller.getCurrentPiece(), "Debería haber una pieza activa al iniciar.");
    }

    @Test
    void testMovePieceLeft() {
        GameBoard board = new GameBoard();
        GameController controller = new GameController(board, null);
        controller.startGame();
        Tetromino currentPiece = controller.getCurrentPiece();
        int initialX = controller.getPieceX();
        controller.movePieceLeft();
        assertEquals(initialX - 1, controller.getPieceX(),
                "La pieza debería moverse una posición a la izquierda.");
    }

    @Test
    void testMovePieceRight() {
        GameBoard board = new GameBoard();
        GameController controller = new GameController(board, null);
        controller.startGame();
        Tetromino currentPiece = controller.getCurrentPiece();
        int initialX = controller.getPieceX();
        controller.movePieceRight();
        assertEquals(initialX + 1, controller.getPieceX(),
                "La pieza debería moverse una posición a la derecha.");
    }

    @Test
    void testDropPiece() {
        GameBoard board = new GameBoard();
        GameController controller = new GameController(board, null);
        controller.startGame();
        controller.dropPiece();
        assertTrue(controller.isPiecePlaced(),
                "La pieza debería colocarse después de caer.");
    }
}
