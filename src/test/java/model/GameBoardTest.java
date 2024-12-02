package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas para la clase GameBoard del Código 1.
 */
class GameBoardTest {

    /**
     * TIPO DE PRUEBA: Caja Negra
     * CRITERIO EVALUADO: Particiones equivalentes
     * DESCRIPCION: Verifica la inicialización del tablero con dimensiones válidas.
     */
    @Test
    void testGameBoardInitialization() {
        GameBoard board = new GameBoard(20, 10);
        assertEquals(20, board.getRows(), "El número de filas debería ser 20.");
        assertEquals(10, board.getCols(), "El número de columnas debería ser 10.");
    }

    /**
     * TIPO DE PRUEBA: Caja Negra
     * CRITERIO EVALUADO: Valores límite
     * DESCRIPCION: Verifica que se lanza una excepción para dimensiones de tablero inválidas.
     */
    @Test
    void testInvalidGameBoardDimensions() {
        assertThrows(IllegalArgumentException.class, () -> new GameBoard(0, 10));
        assertThrows(IllegalArgumentException.class, () -> new GameBoard(10, 0));
    }

    /**
     * TIPO DE PRUEBA: Caja Negra
     * CRITERIO EVALUADO: Particiones equivalentes
     * DESCRIPCION: Comprueba que las celdas del tablero devuelven valores correctos iniciales.
     */
    @Test
    void testGetCell() {
        GameBoard board = new GameBoard(20, 10);
        assertEquals(0, board.getCell(0, 0), "La celda inicial debería ser 0.");
    }

    /**
     * TIPO DE PRUEBA: Caja Negra
     * CRITERIO EVALUADO: Valores límite
     * DESCRIPCION: Verifica que se lanza una excepción cuando se intenta acceder a una celda fuera de los límites del tablero.
     */
    @Test
    void testInvalidCellAccess() {
        GameBoard board = new GameBoard(20, 10);
        assertThrows(IndexOutOfBoundsException.class, () -> board.getCell(-1, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> board.getCell(20, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> board.getCell(0, -1));
        assertThrows(IndexOutOfBoundsException.class, () -> board.getCell(0, 10));
    }

    /**
     * TIPO DE PRUEBA: Caja Blanca
     * CRITERIO EVALUADO: Statement coverage
     * DESCRIPCION: Comprueba que no se pueden establecer valores inválidos en celdas.
     */
    @Test
    void testSetCellInvalidValue() {
        GameBoard board = new GameBoard(20, 10);
        assertThrows(IllegalArgumentException.class, () -> board.setCell(0, 0, -1));
    }

    /**
     * TIPO DE PRUEBA: Caja Blanca
     * CRITERIO EVALUADO: Loop testing(bucle anidado)
     * DESCRIPCION: Valida que las filas superiores se desplacen al limpiar múltiples líneas.
     */
    @Test
    void testClearLineWithMultipleRows() {
        GameBoard board = new GameBoard(20, 10);
        for (int row = 18; row < 20; row++) {
            for (int col = 0; col < board.getCols(); col++) {
                board.setCell(row, col, 1);
            }
        }
        board.clearCompleteLines();
        for (int row = 18; row < 20; row++) {
            for (int col = 0; col < board.getCols(); col++) {
                assertEquals(0, board.getCell(row, col));
            }
        }
    }

    /**
     * TIPO DE PRUEBA: Caja Blanca
     * CRITERIO EVALUADO: Statement coverage
     * DESCRIPCION: Comprueba que el método canMove detecta colisiones con otras celdas ocupadas en el tablero.
     */
    @Test
    void testCanMoveCollision() {
        GameBoard board = new GameBoard(20, 10);
        board.setCell(0, 0, 1);
        assertFalse(board.canMove(0, 0));
    }

    /**
     * TIPO DE PRUEBA: Caja Blanca
     * CRITERIO EVALUADO: Loop testing
     * DESCRIPCION: Valida que el Tetromino se mueve hacia abajo hasta alcanzar el límite inferior.
     */
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

    /**
     * TIPO DE PRUEBA: Caja Negra
     * CRITERIO EVALUADO: Particiones equivalentes, límites
     * DESCRIPCION: Verifica el movimiento lateral del Tetromino en el tablero.
     */
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

    /**
     * TIPO DE PRUEBA: Caja Negra
     * CRITERIO EVALUADO: Particiones equivalentes
     * DESCRIPCION: Comprueba que la rotación del Tetromino cambia su forma correctamente.
     */
    @Test
    void testRotateTetromino() {
        GameBoard board = new GameBoard(20, 10);
        Tetromino initialPiece = board.getCurrentTetromino();
        int[][] initialShape = initialPiece.getShape();

        board.rotateTetromino();
        assertNotEquals(initialShape, board.getCurrentTetromino().getShape(), "La forma de la pieza debería cambiar tras rotarla.");
    }

    /**
     * TIPO DE PRUEBA: Caja Negra
     * CRITERIO EVALUADO: Particiones equivalentes
     * DESCRIPCION: Verifica que se actualice el puntaje tras limpiar una línea completa.
     */
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

    /**
     * TIPO DE PRUEBA: Caja Blanca
     * CRITERIO EVALUADO: Loop testing
     * DESCRIPCION: Valida que se limpien correctamente múltiples líneas completas del tablero.
     */
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

    /**
     * TIPO DE PRUEBA: Caja Negra
     * CRITERIO EVALUADO: Valores límite
     * DESCRIPCION: Comprueba que se genera un nuevo Tetromino después de colocar el anterior.
     */
    @Test
    void testSpawnNewTetromino() {
        GameBoard board = new GameBoard(20, 10);
        Tetromino firstPiece = board.getCurrentTetromino();

        // Mover la pieza hacia abajo hasta que se coloque.
        while (board.moveTetrominoDown()) {}

        Tetromino newPiece = board.getCurrentTetromino();
        assertNotSame(firstPiece, newPiece, "Debería generarse una nueva pieza tras colocar la actual.");
    }

    /**
     * TIPO DE PRUEBA: Caja Negra
     * CRITERIO EVALUADO: Valores límite
     * DESCRIPCION: Verifica que el Tetromino no puede moverse fuera de los bordes del tablero.
     */
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

    /**
     * TIPO DE PRUEBA: Caja Blanca
     * CRITERIO EVALUADO: Statement Coverage
     * DESCRIPCION: Verifica que las colisiones complejas se manejan correctamente.
     */
    @Test
    void testComplexCollisions() {
        GameBoard board = new GameBoard(20, 10);

        // Coloca la primera pieza en el fondo.
        while (board.moveTetrominoDown()) {}

        // La nueva pieza debería moverse hacia abajo si tiene espacio.
        assertTrue(board.moveTetrominoDown(), "La nueva pieza debería moverse hacia abajo si tiene espacio.");
    }

    /**
     * TIPO DE PRUEBA: Caja Blanca
     * CRITERIO EVALUADO: Statement Coverage, Path Coverage
     * DESCRIPCION: Valida el estado del tablero tras colocar un Tetromino, cubriendo rutas adicionales en placeTetromino.
     */
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

    /**
     * TIPO DE PRUEBA: Caja Blanca
     * CRITERIO EVALUADO: Pairwise testing
     * DESCRIPCION: Evalúa combinaciones de posiciones y formas del Tetromino en el tablero.
     */
    @Test
    void testCanMovePairwise() {
        GameBoard board = new GameBoard(20, 10);

        // Formas de Tetromino para probar
        int[][] shape1 = {{1, 1}, {1, 1}}; // Cuadrado 2x2
        int[][] shape2 = {{1, 1, 1}, {0, 1, 0}}; // T de 3x2
        int[][] shape3 = {{1, 1, 1, 1}}; // Línea horizontal 1x4

        // Posiciones X e Y para probar
        int[] xValues = {0, 5, 9}; // Izquierda, centro, derecha
        int[] yValues = {0, 10, 19}; // Superior, medio, inferior

        for (int x : xValues) {
            for (int y : yValues) {
                // Verificar si `shape1` puede moverse
                if (x + shape1[0].length <= board.getCols() && y + shape1.length <= board.getRows()) {
                    assertTrue(board.canMove(x, y, shape1), "Shape1 debería poder moverse a (" + x + ", " + y + ")");
                } else {
                    assertFalse(board.canMove(x, y, shape1), "Shape1 no debería poder moverse a (" + x + ", " + y + ")");
                }

                // Verificar si `shape2` puede moverse
                if (x + shape2[0].length <= board.getCols() && y + shape2.length <= board.getRows()) {
                    assertTrue(board.canMove(x, y, shape2), "Shape2 debería poder moverse a (" + x + ", " + y + ")");
                } else {
                    assertFalse(board.canMove(x, y, shape2), "Shape2 no debería poder moverse a (" + x + ", " + y + ")");
                }

                // Verificar si `shape3` puede moverse
                if (x + shape3[0].length <= board.getCols() && y + shape3.length <= board.getRows()) {
                    assertTrue(board.canMove(x, y, shape3), "Shape3 debería poder moverse a (" + x + ", " + y + ")");
                } else {
                    assertFalse(board.canMove(x, y, shape3), "Shape3 no debería poder moverse a (" + x + ", " + y + ")");
                }
            }
        }
    }

    /**
     * TIPO DE PRUEBA: Caja Blanca
     * CRITERIO EVALUADO: Path Coverage
     * DESCRIPCION: Valida todas las rutas del método placeTetromino.
     */
    @Test
    void testPlaceTetrominoPathCoverage() {
        GameBoard board = new GameBoard(20, 10);

        // Configurar una forma específica para el Tetromino actual
        int[][] shape = {{1, 1}, {1, 1}}; // Forma de un cuadrado 2x2
        Tetromino tetromino = board.getCurrentTetromino();
        tetromino.setShape(shape);

        // Guardar el tipo del Tetromino
        int expectedValue = tetromino.getType().ordinal() + 1;

        // Posicionar el Tetromino cerca del centro del tablero
        board.moveTetrominoDown(); // Mover hacia abajo para simular movimiento
        board.placeTetromino();    // Colocar la pieza en el tablero

        // Verificar que las celdas del tablero coinciden con la forma del Tetromino
        int startX = board.getTetrominoX();
        int startY = board.getTetrominoY();

        for (int row = 0; row < shape.length; row++) {
            for (int col = 0; col < shape[row].length; col++) {
                if (shape[row][col] == 1) {
                    int boardX = startX + col;
                    int boardY = startY + row;
                    assertEquals(expectedValue, board.getCell(boardY, boardX),
                            "La celda debería coincidir con la forma colocada.");
                }
            }
        }

        // Verificar que el tablero está en un estado válido después de colocar la pieza
        assertTrue(board.allCellsAreValid(), "El tablero debería estar en un estado válido.");
    }
}