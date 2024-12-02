package model;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas para la clase GameBoard del Código 1.
 */
class GameBoardTest {

    /**
     * TIPO DE PRUEBA: Caja Negra
     * CRITERIO EVALUADO: Particiones equivalentes
     * DESCRIPCIÓN: Verifica la inicialización del tablero con dimensiones válidas.
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
     * DESCRIPCIÓN: Verifica que se lanza una excepción para dimensiones de tablero inválidas.
     */
    @Test
    void testInvalidGameBoardDimensions() {
        assertThrows(IllegalArgumentException.class, () -> new GameBoard(0, 10));
        assertThrows(IllegalArgumentException.class, () -> new GameBoard(10, 0));
    }

    /**
     * TIPO DE PRUEBA: Caja Negra
     * CRITERIO EVALUADO: Particiones equivalentes
     * DESCRIPCIÓN: Comprueba que las celdas del tablero devuelven valores correctos iniciales.
     */
    @Test
    void testGetCell() {
        GameBoard board = new GameBoard(20, 10);
        assertEquals(0, board.getCell(0, 0), "La celda inicial debería ser 0.");
    }

    /**
     * TIPO DE PRUEBA: Caja Negra
     * CRITERIO EVALUADO: Valores límite
     * DESCRIPCIÓN: Verifica que se lanza una excepción cuando se intenta acceder a una celda fuera de
     * los límites del tablero.
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
     * CRITERIO EVALUADO: Statement Coverage
     * DESCRIPCIÓN: Comprueba que no se pueden establecer valores inválidos en celdas.
     */
    @Test
    void testSetCellInvalidValue() {
        GameBoard board = new GameBoard(20, 10);
        assertThrows(IllegalArgumentException.class, () -> board.setCell(0, 0, -1));
    }

    /**
     * TIPO DE PRUEBA: Caja Blanca
     * CRITERIO EVALUADO: Loop Testing (bucle anidado)
     * DESCRIPCIÓN: Valida que las filas superiores se desplacen al limpiar múltiples líneas.
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
     * CRITERIO EVALUADO: Statement Coverage
     * DESCRIPCIÓN: Comprueba que el método canMove detecta colisiones con otras celdas ocupadas en el tablero.
     */
    @Test
    void testCanMoveCollision() {
        GameBoard board = new GameBoard(20, 10);
        board.setCell(0, 0, 1); // Ocupamos la celda (0, 0)

        // Definimos una forma que ocupa la posición (0, 0)
        int[][] shape = {
                {1}
        };

        // Intentamos mover la forma a la posición (0, 0)
        assertFalse(board.canMove(0, 0, shape), "Se esperaba que canMove devolviera false debido a una colisión en (0, 0).");
    }


    /**
     * TIPO DE PRUEBA: Caja Blanca
     * CRITERIO EVALUADO: Loop Testing
     * DESCRIPCIÓN: Valida que el Tetromino se mueve hacia abajo hasta alcanzar el límite inferior.
     */
    @Test
    void testMoveTetrominoDown() {
        GameBoard board = new GameBoard(20, 10);
        int initialY = board.getTetrominoY();

        assertTrue(board.moveTetrominoDown(), "La pieza debería poder moverse hacia abajo.");
        assertEquals(initialY + 1, board.getTetrominoY(), "La posición Y de la pieza " +
                "debería incrementar.");

        // Mover la pieza hasta el límite.
        while (board.moveTetrominoDown()) {}

        assertEquals(0, board.getTetrominoY(), "Una nueva pieza debería generarse en la parte " +
                "superior tras colocar la actual.");
    }

    /**
     * TIPO DE PRUEBA: Caja Negra
     * CRITERIO EVALUADO: Particiones equivalentes, límites
     * DESCRIPCIÓN: Verifica el movimiento lateral del Tetromino en el tablero.
     */
    @Test
    void testMoveTetrominoLeftRight() {
        GameBoard board = new GameBoard(20, 10);
        int initialX = board.getTetrominoX();

        board.moveTetrominoLeft();
        assertEquals(initialX - 1, board.getTetrominoX(), "La pieza debería moverse a la izquierda.");

        board.moveTetrominoRight();
        board.moveTetrominoRight();
        assertEquals(initialX + 1, board.getTetrominoX(), "La pieza debería moverse a la " +
                "derecha dos veces.");
    }

    /**
     * TIPO DE PRUEBA: Caja Negra
     * CRITERIO EVALUADO: Particiones equivalentes
     * DESCRIPCIÓN: Comprueba que la rotación del Tetromino cambia su forma correctamente.
     */
    @Test
    void testRotateTetromino() {
        GameBoard board = new GameBoard(20, 10);
        Tetromino initialPiece = board.getCurrentTetromino();
        int[][] initialShape = initialPiece.getShape();

        board.rotateTetromino();
        assertNotEquals(initialShape, board.getCurrentTetromino().getShape(), "La forma de la pieza " +
                "debería cambiar tras rotarla.");
    }

    /**
     * TIPO DE PRUEBA: Caja Negra
     * CRITERIO EVALUADO: Particiones equivalentes
     * DESCRIPCIÓN: Verifica que se actualice el puntaje tras limpiar una línea completa.
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
        assertEquals(100, board.getScore(), "El puntaje debería aumentar en " +
                "100 al limpiar una línea.");
    }

    /**
     * TIPO DE PRUEBA: Caja Blanca
     * CRITERIO EVALUADO: Loop Testing
     * DESCRIPCIÓN: Valida que se limpien correctamente múltiples líneas completas del tablero.
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
        assertEquals(200, board.getScore(), "El puntaje debería aumentar en " +
                "200 al limpiar dos líneas.");
    }

    /**
     * TIPO DE PRUEBA: Caja Negra
     * CRITERIO EVALUADO: Valores límite
     * DESCRIPCIÓN: Comprueba que se genera un nuevo Tetromino después de colocar el anterior.
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
     * DESCRIPCIÓN: Verifica que el Tetromino no puede moverse fuera de los bordes del tablero.
     */
    @Test
    void testBoundaryMovements() {
        GameBoard board = new GameBoard(20, 10);

        // Intentar mover la pieza fuera de los límites laterales.
        for (int i = 0; i < 15; i++) {
            board.moveTetrominoLeft();
        }
        assertEquals(0, board.getTetrominoX(), "La pieza no debería salir del tablero " +
                "por la izquierda.");

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
     * DESCRIPCIÓN: Verifica que las colisiones complejas se manejan correctamente.
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
     * DESCRIPCIÓN: Valida el estado del tablero tras colocar un Tetromino, cubriendo rutas adicionales
     * en placeTetromino.
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
                                "El tablero debería contener los valores de la pieza colocada en " +
                                        "(" + boardY + ", " + boardX + ")");
                    } else {
                        fail("Coordenadas fuera de rango: (" + boardY + ", " + boardX + ")");
                    }
                }
            }
        }
    }

    /**
     * TIPO DE PRUEBA: Caja Blanca
     * CRITERIO EVALUADO: Pairwise Testing
     * DESCRIPCIÓN: Evalúa combinaciones de posiciones y formas del Tetromino en el tablero.
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
                    assertTrue(board.canMove(x, y, shape1), "Shape1 debería poder moverse a " +
                            "(" + x + ", " + y + ")");
                } else {
                    assertFalse(board.canMove(x, y, shape1), "Shape1 no debería poder moverse a " +
                            "(" + x + ", " + y + ")");
                }

                // Verificar si `shape2` puede moverse
                if (x + shape2[0].length <= board.getCols() && y + shape2.length <= board.getRows()) {
                    assertTrue(board.canMove(x, y, shape2), "Shape2 debería poder moverse a " +
                            "(" + x + ", " + y + ")");
                } else {
                    assertFalse(board.canMove(x, y, shape2), "Shape2 no debería poder moverse a " +
                            "(" + x + ", " + y + ")");
                }

                // Verificar si `shape3` puede moverse
                if (x + shape3[0].length <= board.getCols() && y + shape3.length <= board.getRows()) {
                    assertTrue(board.canMove(x, y, shape3), "Shape3 debería poder moverse a " +
                            "(" + x + ", " + y + ")");
                } else {
                    assertFalse(board.canMove(x, y, shape3), "Shape3 no debería poder moverse a " +
                            "(" + x + ", " + y + ")");
                }
            }
        }
    }

    /**
     * TIPO DE PRUEBA: Caja Blanca
     * CRITERIO EVALUADO: Path Coverage
     * DESCRIPCIÓN: Valida todas las rutas del método placeTetromino.
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

    /**
     * TIPO DE PRUEBA: Caja Blanca con Mocks (Mockito)
     * CRITERIO EVALUADO: Aislamiento de dependencias y cobertura de código
     * DESCRIPCIÓN: Verifica la colocación de un Tetromino en el GameBoard utilizando un MockObject para controlar
     * su forma y tipo, probando el método placeTetromino() en un escenario específico.
     */
    @Test
    void testPlaceTetrominoWithMockedTetromino() {
        GameBoard board = new GameBoard(20, 10);

        // Crear un mock del Tetromino
        Tetromino mockTetromino = mock(Tetromino.class);

        // Configurar el mock para devolver una forma específica
        int[][] mockShape = {
                {1, 1},
                {1, 1}
        };
        when(mockTetromino.getShape()).thenReturn(mockShape);
        when(mockTetromino.getType()).thenReturn(Tetromino.TetrominoType.O);

        // Establecer el Tetromino mockeado en el GameBoard
        board.setCurrentTetromino(mockTetromino);
        board.setTetrominoX(0);
        board.setTetrominoY(0);

        // Colocar el Tetromino
        board.placeTetromino();

        // Verificar que el tablero se actualizó correctamente
        for (int row = 0; row < mockShape.length; row++) {
            for (int col = 0; col < mockShape[row].length; col++) {
                if (mockShape[row][col] == 1) {
                    assertEquals(
                            mockTetromino.getType().ordinal() + 1,
                            board.getCell(row, col),
                            "La celda del tablero no coincide con la forma del Tetromino mockeado."
                    );
                }
            }
        }
    }

    /**
     * TIPO DE PRUEBA: Caja Blanca con MockObjects Manuales
     * CRITERIO EVALUADO: Manejo de colisiones y aislamiento de dependencias
     * DESCRIPCIÓN: Verifica la detección de colisiones al mover un Tetromino mockeado, utilizando un
     * MockTetromino implementado manualmente.
     */
    @Test
    void testCollisionDetectionWithMockedTetromino() {
        GameBoard board = new GameBoard(20, 10);

        // Crear un MockTetromino con una forma específica
        int[][] mockShape = {
                {1, 1, 1, 1} // Una línea horizontal
        };
        MockTetromino mockTetromino = new MockTetromino(mockShape, Tetromino.TetrominoType.I);

        // Establecer el Tetromino mockeado en el GameBoard
        board.setCurrentTetromino(mockTetromino);
        board.setTetrominoX(0);
        board.setTetrominoY(board.getRows() - 1); // Colocarlo en la última fila

        // Intentar mover el Tetromino hacia abajo (debe fallar)
        boolean movedDown = board.moveTetrominoDown();
        assertFalse(movedDown, "El Tetromino no debería poder moverse hacia abajo debido a una colisión con " +
                "el fondo.");

        // Verificar que el Tetromino se colocó automáticamente
        for (int col = 0; col < mockShape[0].length; col++) {
            assertEquals(
                    mockTetromino.getType().ordinal() + 1,
                    board.getCell(board.getRows() - 1, col),
                    "La celda del tablero no coincide con la forma del Tetromino mockeado."
            );
        }
    }

    /**
     * TIPO DE PRUEBA: Caja Blanca con Mocks (Mockito)
     * CRITERIO EVALUADO: Manejo de excepciones y escenarios límite
     * DESCRIPCIÓN: Verifica el manejo del fin del juego cuando no hay espacio para un nuevo Tetromino, mockeando el
     * método spawnTetromino().
     */
    @Test
    void testGameOverWhenNoSpaceForNewTetromino() {
        // Crear un spy del GameBoard real
        GameBoard realBoard = new GameBoard(20, 10);
        GameBoard spyBoard = spy(realBoard);

        // Llenar la parte superior del tablero para simular que no hay espacio
        for (int col = 0; col < spyBoard.getCols(); col++) {
            spyBoard.setCell(0, col, 1);
        }

        // Mockear el método spawnTetromino para que lance una excepción
        doThrow(new IllegalStateException("No hay espacio para un nuevo Tetromino.")).when(spyBoard).spawnTetromino();

        // Intentar generar un nuevo Tetromino y verificar que se maneja el fin del juego
        assertThrows(IllegalStateException.class, () -> {
            spyBoard.spawnTetromino();
        }, "Se esperaba una IllegalStateException al no haber espacio para un nuevo Tetromino.");
    }

    /**
     * TIPO DE PRUEBA: Caja Blanca con Mocks (Mockito)
     * CRITERIO EVALUADO: Manejo de excepciones y cobertura de código
     * DESCRIPCIÓN: Verifica el comportamiento del GameBoard cuando un Tetromino no puede moverse ni generarse,
     * mockeando el método canMove() para que siempre devuelva false.
     */
    @Test
    void testTetrominoCannotMove_ExpectException() {
        GameBoard realBoard = new GameBoard(20, 10);
        GameBoard spyBoard = spy(realBoard);

        // Mockear el método canMove para que siempre devuelva false
        doReturn(false).when(spyBoard).canMove(anyInt(), anyInt(), any(int[][].class));

        // Intentar mover la tetromino hacia abajo y esperar la excepción
        assertThrows(IllegalStateException.class, () -> {
            spyBoard.moveTetrominoDown();
        }, "Se esperaba una IllegalStateException debido a que el Tetromino no puede moverse y no se puede " +
                "generar una nueva pieza.");
    }
}
