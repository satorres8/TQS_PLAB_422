package model;

/**
 * Clase MockTetromino que extiende Tetromino para fines de prueba.
 *
 * Permite crear Tetrominos con formas específicas y controlar su comportamiento, como evitar que roten.
 * Esto es útil para pruebas que requieren formas personalizadas y cumple con los criterios de crear MockObjects manuales.
 */

public class MockTetromino extends Tetromino {
    public MockTetromino(int[][] shape, TetrominoType type) {
        super(type);
        this.setShape(shape);
    }

    @Override
    public void rotate() {
        // No hacer nada
    }
}
