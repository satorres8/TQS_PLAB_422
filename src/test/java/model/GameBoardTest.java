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

    @Test
    void testPlacePieceWithinBounds() {
        GameBoard board = new GameBoard(20, 10);
        Tetromino piece = new Tetromino(Tetromino.TetrominoType.L);

        // Colocar la pieza "L" en una posición válida.
        assertTrue(board.placePiece(piece, 0, 0), "La pieza 'L' debería colocarse dentro de los límites.");
    }

    @Test
    void testPlacePieceOutOfBounds() {
        GameBoard board = new GameBoard(20, 10);
        Tetromino piece = new Tetromino(Tetromino.TetrominoType.I);

        // Intentar colocar la pieza "I" fuera del tablero.
        assertFalse(board.placePiece(piece, 19, 8), "No debería ser posible colocar la pieza fuera de los límites.");
    }

    @Test
    void testPieceCollision() {
        GameBoard board = new GameBoard(20, 10);
        Tetromino piece1 = new Tetromino(Tetromino.TetrominoType.O);
        Tetromino piece2 = new Tetromino(Tetromino.TetrominoType.T);

        // Colocar la primera pieza en el tablero.
        board.placePiece(piece1, 18, 4);

        // Intentar colocar la segunda pieza en la misma posición.
        assertFalse(board.placePiece(piece2, 18, 4), "No debería ser posible colocar una pieza donde ya hay otra.");
    }

    @Test
    void testClearCompleteLinesSingle() {
        GameBoard board = new GameBoard(20, 10);
        Tetromino piece = new Tetromino(Tetromino.TetrominoType.O);

        // Llenar la última fila completamente con valores de celdas.
        for (int col = 0; col < board.getCols(); col++) {
            board.setCell(19, col, 1);
        }

        // Verificar que la línea completa se limpia.
        int linesCleared = board.clearCompleteLines();
        assertEquals(1, linesCleared, "Debería limpiarse una línea completa.");
    }

    @Test
    void testClearMultipleCompleteLines() {
        GameBoard board = new GameBoard(20, 10);
        Tetromino piece = new Tetromino(Tetromino.TetrominoType.I);

        // Llenar las dos últimas filas.
        for (int row = 18; row <= 19; row++) {
            for (int col = 0; col < board.getCols(); col++) {
                board.setCell(row, col, 1);
            }
        }

        // Verificar que ambas líneas se limpian.
        int linesCleared = board.clearCompleteLines();
        assertEquals(2, linesCleared, "Deberían limpiarse dos líneas completas.");
    }

    @Test
    void testPlaceRotatedPiece() {
        GameBoard board = new GameBoard(20, 10);
        Tetromino piece = new Tetromino(Tetromino.TetrominoType.J);

        // Rotar la pieza antes de colocarla.
        piece.rotate();

        // Verificar que se puede colocar la pieza rotada.
        assertTrue(board.placePiece(piece, 0, 0), "Debería ser posible colocar la pieza 'J' rotada.");
    }

    @Test
    void testCollisionAfterRotation() {
        GameBoard board = new GameBoard(20, 10);
        Tetromino piece1 = new Tetromino(Tetromino.TetrominoType.O);
        Tetromino piece2 = new Tetromino(Tetromino.TetrominoType.T);

        // Colocar la primera pieza en el tablero.
        board.placePiece(piece1, 18, 4);

        // Rotar la segunda pieza y tratar de colocarla en conflicto con la primera.
        piece2.rotate();
        assertFalse(board.placePiece(piece2, 18, 4), "No debería ser posible colocar una pieza rotada en conflicto con otra.");
    }

}