package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas básicas para la clase GameBoard.
 */
class GameBoardTest {

    @Test
    void testGameBoardInitialization() {
        GameBoard board = new GameBoard(20, 10);
        assertEquals(20, board.getRows(), "El número de filas debería ser 20.");
        assertEquals(10, board.getCols(), "El número de columnas debería ser 10.");
    }

    @Test
    void testGetCell() {
        GameBoard board = new GameBoard(20, 10);
        assertEquals(0, board.getCell(0, 0), "La celda inicial debería ser 0.");
    }

    @Test
    void testSetCell() {
        GameBoard board = new GameBoard(20, 10);
        board.setCell(0, 0, 1);
        assertEquals(1, board.getCell(0, 0), "La celda debería ser actualizada a 1.");
    }
}