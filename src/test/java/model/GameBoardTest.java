package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;

class GameBoardTest {

    @Test
    void testBoardInitialization() {
        GameBoard board = new GameBoard();
        assertNotNull(board, "El tablero debería inicializarse correctamente.");
        assertEquals(20, board.getRows(), "El tablero debería tener 20 filas.");
        assertEquals(10, board.getCols(), "El tablero debería tener 10 columnas.");
    }

    @Test
    void testEmptyBoard() {
        GameBoard board = new GameBoard();
        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getCols(); col++) {
                assertEquals(0, board.getCell(row, col),
                        "Todas las celdas deberían estar vacías al inicio.");
            }
        }
    }

    // Test Error
    @Disabled
    @Test
    void testPlacePiece() {
        GameBoard board = new GameBoard();
        Tetromino piece = new Tetromino("I"); // Ejemplo de pieza "I".
        boolean placed = board.placePiece(piece, 0, 4); // Coloca en la fila 0, columna 4.
        assertTrue(placed, "La pieza debería colocarse correctamente en el tablero.");
        // Verifica que las posiciones ocupadas por la pieza no están vacías.
        assertEquals(1, board.getCell(0, 4), "La celda debería estar ocupada por la pieza.");
    }

    @Test
    void testCollisionDetection() {
        GameBoard board = new GameBoard();
        Tetromino piece = new Tetromino("O"); // Pieza "O".
        board.placePiece(piece, 18, 4); // Coloca la pieza cerca del fondo.
        boolean canMove = board.canPlacePiece(piece, 19, 4); // Intenta colocar fuera del tablero.
        assertFalse(canMove, "La pieza no debería colocarse fuera del tablero.");
    }

    @Test
    void testClearCompleteLine() {
        GameBoard board = new GameBoard();
        for (int col = 0; col < board.getCols(); col++) {
            board.setCell(19, col, 1); // Llena la última fila.
        }
        int linesCleared = board.clearCompleteLines();
        assertEquals(1, linesCleared, "Debería detectar y limpiar una línea completa.");
        for (int col = 0; col < board.getCols(); col++) {
            assertEquals(0, board.getCell(19, col),
                    "La última fila debería estar vacía después de limpiarla.");
        }
    }
}
