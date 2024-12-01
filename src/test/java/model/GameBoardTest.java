package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas para la clase GameBoard del Código 1.
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
    void testMoveTetrominoDown() {
        GameBoard board = new GameBoard(20, 10);
        int initialY = board.getTetrominoY();

        assertTrue(board.moveTetrominoDown(), "La pieza debería poder moverse hacia abajo.");
        assertEquals(initialY + 1, board.getTetrominoY(), "La posición Y de la pieza debería incrementar.");

        // Mover la pieza hasta el límite.
        while (board.moveTetrominoDown()) {}

        assertEquals(0, board.getTetrominoY(), "Una nueva pieza debería generarse en la parte superior tras colocar la actual.");
    }

    @Test
    void testMoveTetrominoLeftRight() {
        GameBoard board = new GameBoard(20, 10);
        int initialX = board.getTetrominoX();

        board.moveTetrominoLeft();
        assertEquals(initialX - 1, board.getTetrominoX(), "La pieza debería moverse a la izquierda.");

        board.moveTetrominoRight();
        board.moveTetrominoRight();
        assertEquals(initialX + 1, board.getTetrominoX(), "La pieza debería moverse a la derecha dos veces.");
    }

    @Test
    void testRotateTetromino() {
        GameBoard board = new GameBoard(20, 10);
        Tetromino initialPiece = board.getCurrentTetromino();
        int[][] initialShape = initialPiece.getShape();

        board.rotateTetromino();
        assertNotEquals(initialShape, board.getCurrentTetromino().getShape(), "La forma de la pieza debería cambiar tras rotarla.");
    }

    @Test
    void testScoreAfterClearingLines() {
        GameBoard board = new GameBoard(20, 10);

        // Llena una fila completamente.
        for (int col = 0; col < board.getCols(); col++) {
            board.setCell(19, col, 1);
        }

        // Limpia las líneas y verifica el puntaje.
        board.clearCompleteLines();
        assertEquals(100, board.getScore(), "El puntaje debería aumentar en 100 al limpiar una línea.");
    }

    @Test
    void testClearMultipleCompleteLines() {
        GameBoard board = new GameBoard(20, 10);

        // Llena las dos últimas filas.
        for (int row = 18; row <= 19; row++) {
            for (int col = 0; col < board.getCols(); col++) {
                board.setCell(row, col, 1);
            }
        }

        board.clearCompleteLines();
        assertEquals(200, board.getScore(), "El puntaje debería aumentar en 200 al limpiar dos líneas.");
    }

    @Test
    void testSpawnNewTetromino() {
        GameBoard board = new GameBoard(20, 10);
        Tetromino firstPiece = board.getCurrentTetromino();

        // Mover la pieza hacia abajo hasta que se coloque.
        while (board.moveTetrominoDown()) {}

        Tetromino newPiece = board.getCurrentTetromino();
        assertNotSame(firstPiece, newPiece, "Debería generarse una nueva pieza tras colocar la actual.");
    }

    @Test
    void testBoundaryMovements() {
        GameBoard board = new GameBoard(20, 10);

        // Intentar mover la pieza fuera de los límites laterales.
        for (int i = 0; i < 15; i++) {
            board.moveTetrominoLeft();
        }
        assertEquals(0, board.getTetrominoX(), "La pieza no debería salir del tablero por la izquierda.");

        for (int i = 0; i < 15; i++) {
            board.moveTetrominoRight();
        }
        assertEquals(board.getCols() - board.getCurrentTetromino().getShape()[0].length,
                board.getTetrominoX(),
                "La pieza no debería salir del tablero por la derecha.");
    }

    @Test
    void testComplexCollisions() {
        GameBoard board = new GameBoard(20, 10);

        // Coloca la primera pieza en el fondo.
        while (board.moveTetrominoDown()) {}

        // La nueva pieza debería moverse hacia abajo si tiene espacio.
        assertTrue(board.moveTetrominoDown(), "La nueva pieza debería moverse hacia abajo si tiene espacio.");
    }

    @Test
    void testBoardStateAfterPlacement() {
        GameBoard board = new GameBoard(20, 10);

        // Dejar caer una pieza completamente y guardar su referencia antes de que se genere una nueva.
        Tetromino piece = board.getCurrentTetromino();

        Tetromino placedPiece = null; // Referencia a la pieza que se colocará.
        int placedX = 0, placedY = 0; // Posiciones de la pieza que se colocará.

        // Dejar caer una pieza completamente y capturar su estado antes de generar una nueva.
        while (true) {
            placedPiece = board.getCurrentTetromino(); // Captura la pieza actual.
            placedX = board.getTetrominoX(); // Captura su posición X.
            placedY = board.getTetrominoY(); // Captura su posición Y.

            if (!board.moveTetrominoDown()) {
                // La pieza se ha colocado en el tablero; salimos del bucle.
                break;
            }
        }

        // Ahora `placedPiece` tiene la referencia de la pieza que fue colocada.
        int[][] shape = placedPiece.getShape();
        int pieceX = placedX;
        int pieceY = placedY; // Ajusta la posición Y inicial.

        System.out.print("Coordenadas Tetromino: (" + pieceX + ", " + pieceY + ")");


        // Diagnóstico: Imprimir el tablero
        System.out.println("Tablero después de colocar la pieza:");
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                System.out.print(board.getCell(i, j) + " ");
            }
            System.out.println();
        }

        for (int row = 0; row < shape.length; row++) {
            for (int col = 0; col < shape[row].length; col++) {
                if (shape[row][col] == 1) { // Parte de la pieza
                    int boardY = pieceY + row; // Coordenada Y en el tablero
                    int boardX = pieceX + col; // Coordenada X en el tablero

                    // Validar que las coordenadas están dentro del rango del tablero.
                    if (boardY >= 0 && boardY < board.getRows() && boardX >= 0 && boardX < board.getCols()) {
                        assertEquals(piece.getType().ordinal() + 1,
                                board.getCell(boardY, boardX),
                                "El tablero debería contener los valores de la pieza colocada en (" + boardY + ", " + boardX + ")");
                    } else {
                        fail("Coordenadas fuera de rango: (" + boardY + ", " + boardX + ")");
                    }
                }
            }
        }
    }

}