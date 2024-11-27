package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TetrominoTest {

    @Test
    void testTetrominoInitialization() {
        Tetromino piece = new Tetromino("T");
        assertEquals("T", piece.getType(), "El tipo de la pieza debería ser 'T'.");
        assertNotNull(piece.getShape(), "La forma de la pieza debería inicializarse correctamente.");
    }

    @Test
    void testRotation() {
        Tetromino piece = new Tetromino("L");
        int[][] initialShape = piece.getShape();
        piece.rotate();
        int[][] rotatedShape = piece.getShape();
        assertNotEquals(initialShape, rotatedShape, "La forma debería cambiar después de rotar.");
    }

    @Test
    void testRotationBoundary() {
        Tetromino piece = new Tetromino("O"); // La pieza "O" no debería rotar.
        int[][] initialShape = piece.getShape();
        piece.rotate();
        int[][] rotatedShape = piece.getShape();
        assertArrayEquals(initialShape, rotatedShape,
                "La forma de la pieza 'O' debería ser la misma después de rotar.");
    }

    @Test
    void testOccupiedCells() {
        Tetromino piece = new Tetromino("I");
        int occupiedCells = piece.getOccupiedCells();
        assertEquals(4, occupiedCells, "La pieza 'I' debería ocupar 4 celdas.");
    }
}
