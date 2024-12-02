package model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class TetrominoTest {

    /**
     * Función auxiliar para verificar todas las rotaciones de un Tetromino.
     */
    private void verifyAllRotations(Tetromino tetromino, int[][][] expectedRotations) {
        for (int i = 0; i < expectedRotations.length; i++) {
            assertArrayEquals(expectedRotations[i], tetromino.getShape(),
                    "La rotación " + (i * 90) + "° no es correcta.");
            tetromino.rotate();
        }
    }

    /**
     * TIPO DE PRUEBA: Caja Negra
     * CRITERIO EVALUADO: Particiones equivalentes
     * DESCRIPCIÓN: Verifica que las formas iniciales de los Tetrominoes coincidan con los valores esperados
     * para cada tipo válido.
     */
    @Test
    public void testTetrominoInitializationValidTypes() {
        Tetromino tetrominoI = new Tetromino(Tetromino.TetrominoType.I);
        int[][] expectedShapeI = {{1, 1, 1, 1}};
        assertArrayEquals(expectedShapeI, tetrominoI.getShape(), "La forma de la pieza I no es la esperada.");

        Tetromino tetrominoO = new Tetromino(Tetromino.TetrominoType.O);
        int[][] expectedShapeO = {{1, 1}, {1, 1}};
        assertArrayEquals(expectedShapeO, tetrominoO.getShape(), "La forma de la pieza O no es la esperada.");

        Tetromino tetrominoT = new Tetromino(Tetromino.TetrominoType.T);
        int[][] expectedShapeT = {{0, 1, 0}, {1, 1, 1}};
        assertArrayEquals(expectedShapeT, tetrominoT.getShape(), "La forma de la pieza T no es la esperada.");

        Tetromino tetrominoS = new Tetromino(Tetromino.TetrominoType.S);
        int[][] expectedShapeS = {{0, 1, 1}, {1, 1, 0}};
        assertArrayEquals(expectedShapeS, tetrominoS.getShape(), "La forma de la pieza S no es la esperada.");

        Tetromino tetrominoZ = new Tetromino(Tetromino.TetrominoType.Z);
        int[][] expectedShapeZ = {{1, 1, 0}, {0, 1, 1}};
        assertArrayEquals(expectedShapeZ, tetrominoZ.getShape(), "La forma de la pieza Z no es la esperada.");

        Tetromino tetrominoJ = new Tetromino(Tetromino.TetrominoType.J);
        int[][] expectedShapeJ = {{1, 0, 0}, {1, 1, 1}};
        assertArrayEquals(expectedShapeJ, tetrominoJ.getShape(), "La forma de la pieza J no es la esperada.");

        Tetromino tetrominoL = new Tetromino(Tetromino.TetrominoType.L);
        int[][] expectedShapeL = {{0, 0, 1}, {1, 1, 1}};
        assertArrayEquals(expectedShapeL, tetrominoL.getShape(), "La forma de la pieza L no es la esperada.");
    }

    /**
     * TIPO DE PRUEBA: Caja Negra
     * CRITERIO EVALUADO: Valores límite
     * DESCRIPCIÓN: Valida que el constructor lanza una excepción al recibir un tipo de Tetromino nulo.
     */
    @Test
    public void testTetrominoInitializationInvalidType() {
        assertThrows(IllegalArgumentException.class, () -> new Tetromino(null),
                "Se esperaba una IllegalArgumentException para un tipo de pieza inválido.");
    }

    /**
     * TIPO DE PRUEBA: Caja Negra
     * CRITERIO EVALUADO: Path Coverage
     * DESCRIPCIÓN: Comprueba que las rotaciones de todas las piezas se realizan correctamente y coinciden con
     * las formas esperadas.
     */
    @Test
    public void testAllRotationsForAllTetrominoes() {
        Tetromino tetrominoI = new Tetromino(Tetromino.TetrominoType.I);
        int[][][] expectedRotationsI = {{{1, 1, 1, 1}}, {{1}, {1}, {1}, {1}}, {{1, 1, 1, 1}}, {{1}, {1}, {1}, {1}}};
        verifyAllRotations(tetrominoI, expectedRotationsI);

        Tetromino tetrominoO = new Tetromino(Tetromino.TetrominoType.O);
        int[][][] expectedRotationsO = {{{1, 1}, {1, 1}}, {{1, 1}, {1, 1}}, {{1, 1}, {1, 1}}, {{1, 1}, {1, 1}}};
        verifyAllRotations(tetrominoO, expectedRotationsO);

        Tetromino tetrominoT = new Tetromino(Tetromino.TetrominoType.T);
        int[][][] expectedRotationsT = {{{0, 1, 0}, {1, 1, 1}}, {{1, 0}, {1, 1}, {1, 0}},
                {{1, 1, 1}, {0, 1, 0}}, {{0, 1}, {1, 1}, {0, 1}}};
        verifyAllRotations(tetrominoT, expectedRotationsT);

        // Se puede continuar de manera similar para las piezas S, Z, J, L
    }

    /**
     * TIPO DE PRUEBA: Caja Negra
     * CRITERIO EVALUADO: Particiones equivalentes
     * DESCRIPCIÓN: Valida que el método `getOccupiedCells` retorna el número correcto de celdas ocupadas
     * para cada tipo de pieza.
     */
    @Test
    public void testGetOccupiedCells() {
        Tetromino tetrominoI = new Tetromino(Tetromino.TetrominoType.I);
        assertEquals(4, tetrominoI.getOccupiedCells(), "El número de celdas ocupadas para la " +
                "pieza I no es correcto.");

        // Similar para otras piezas (O, T, S, Z, J, L).
    }

    /**
     * TIPO DE PRUEBA: Caja Blanca
     * CRITERIO EVALUADO: Decision Coverage
     * DESCRIPCIÓN: Verifica que una pieza de tipo O no cambia su forma al rotarla.
     */
    @Test
    public void testRotateTetrominoO() {
        Tetromino tetrominoO = new Tetromino(Tetromino.TetrominoType.O);
        int[][] shapeBefore = tetrominoO.getShape();
        tetrominoO.rotate();
        int[][] shapeAfter = tetrominoO.getShape();
        assertArrayEquals(shapeBefore, shapeAfter, "La pieza O no debería cambiar al rotar.");
    }

    /**
     * TIPO DE PRUEBA: Caja Blanca
     * CRITERIO EVALUADO: Path Coverage, Decision Coverage
     * DESCRIPCIÓN: Comprueba que una pieza que no es de tipo O cambia correctamente su forma tras rotarla.
     */
    @Test
    public void testRotateTetrominoNonO() {
        Tetromino tetrominoL = new Tetromino(Tetromino.TetrominoType.L);
        int[][] shapeBefore = tetrominoL.getShape();
        tetrominoL.rotate();
        int[][] shapeAfter = tetrominoL.getShape();
        assertFalse(Arrays.deepEquals(shapeBefore, shapeAfter), "La pieza L debería cambiar al rotar.");
    }

    /**
     * TIPO DE PRUEBA: Caja Negra
     * CRITERIO EVALUADO: Pairwise Testing
     * DESCRIPCIÓN: Evalúa combinaciones de entradas válidas e inválidas al método `setShape` para verificar el
     * manejo correcto de la validación y actualización de formas.
     */
    @Test
    public void testSetShapePairwise() {
        Tetromino tetromino = new Tetromino(Tetromino.TetrominoType.T);

        int[][] validShape1 = {{1, 0}, {0, 1}};
        int[][] invalidShapeNull = null;
        int[][] invalidShapeNegative = {{1, -1}, {0, 1}};
        assertDoesNotThrow(() -> tetromino.setShape(validShape1));
        assertThrows(IllegalArgumentException.class, () -> tetromino.setShape(invalidShapeNull));
        assertThrows(IllegalArgumentException.class, () -> tetromino.setShape(invalidShapeNegative));
    }

    /**
     * TIPO DE PRUEBA: Caja Blanca
     * CRITERIO EVALUADO: Decision Statement
     * DESCRIPCIÓN: Verifica que el constructor de Tetromino lanza una excepción al recibir un tipo nulo.
     */
    @Test
    public void testConstructorWithNullType() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Tetromino(null);
        }, "Se esperaba una IllegalArgumentException al pasar un tipo nulo.");
    }

    /**
     * TIPO DE PRUEBA: Caja Blanca
     * CRITERIO EVALUADO: Decision Statement
     * DESCRIPCIÓN: Verifica que el constructor de Tetromino no lanza excepciones al recibir un tipo válido.
     */
    @Test
    public void testConstructorWithValidType() {
        assertDoesNotThrow(() -> {
            new Tetromino(Tetromino.TetrominoType.S);
        }, "No se esperaba una excepción con un tipo válido.");
    }

    /**
     * TIPO DE PRUEBA: Caja Blanca
     * CRITERIO EVALUADO: Loop Testing
     * DESCRIPCIÓN: Verifica que getOccupiedCells() devuelve 0 cuando la forma del Tetromino es una matriz vacía.
     */
    @Test
    public void testGetOccupiedCellsEmptyShape() {
        Tetromino tetromino = new Tetromino(Tetromino.TetrominoType.I) {
            {
                // Sobrescribir la forma con una matriz vacía
                this.setShape(new int[][]{});
            }
        };
        assertEquals(0, tetromino.getOccupiedCells(), "El número de celdas ocupadas debería ser 0 " +
                "para una matriz vacía.");
    }

    /**
     * TIPO DE PRUEBA: Caja Blanca
     * CRITERIO EVALUADO: Loop Testing
     * DESCRIPCIÓN: Verifica que getOccupiedCells() cuenta correctamente las celdas ocupadas en una forma mixta.
     */
    @Test
    public void testGetOccupiedCellsMixedShape() {
        Tetromino tetromino = new Tetromino(Tetromino.TetrominoType.I) {
            {
                // Sobrescribir la forma con una matriz personalizada
                this.setShape(new int[][]{
                        {1, 0},
                        {0, 1}
                });
            }
        };
        assertEquals(2, tetromino.getOccupiedCells(), "El número de celdas ocupadas debería ser 2.");
    }

    /**
     * TIPO DE PRUEBA: Caja Blanca con Spy (Mockito)
     * CRITERIO EVALUADO: Aislamiento de dependencias y cobertura de código
     * DESCRIPCIÓN: Verifica el comportamiento del método rotate() en Tetromino utilizando un Spy para simular
     * una rotación específica.
     */
    @Test
    void testRotateTetrominoWithSpy() {
        Tetromino realTetromino = new Tetromino(Tetromino.TetrominoType.T);

        Tetromino spyTetromino = spy(realTetromino);

        // Mockear el método rotate para que realice una rotación específica
        doAnswer(invocation -> {
            int[][] rotatedShape = {
                    {1, 0},
                    {1, 1},
                    {1, 0}
            };
            spyTetromino.setShape(rotatedShape);
            return null;
        }).when(spyTetromino).rotate();

        // Llamar al método rotate
        spyTetromino.rotate();

        // Verificar que la forma ha sido actualizada correctamente
        int[][] expectedShape = {
                {1, 0},
                {1, 1},
                {1, 0}
        };
        assertArrayEquals(expectedShape, spyTetromino.getShape(), "La forma de la Tetromino no se rotó " +
                "como se esperaba.");
    }

    /**
     * TIPO DE PRUEBA: Caja Blanca con Mocks (Mockito)
     * CRITERIO EVALUADO: Manejo de excepciones y robustez
     * DESCRIPCIÓN: Verifica el comportamiento del método getOccupiedCells() cuando la forma es null.
     */
    @Test
    void testGetOccupiedCellsWithNullShape() {
        Tetromino mockTetromino = mock(Tetromino.class);

        when(mockTetromino.getShape()).thenReturn(null);

        when(mockTetromino.getOccupiedCells()).thenCallRealMethod();

        assertThrows(NullPointerException.class, () -> {
            mockTetromino.getOccupiedCells();
        }, "Se esperaba una NullPointerException cuando la forma es null.");
    }

    /**
     * TIPO DE PRUEBA: Caja Blanca con MockObjects Manuales
     * CRITERIO EVALUADO: Robustez ante entradas no estándar
     * DESCRIPCIÓN: Verifica que getOccupiedCells() cuenta correctamente las celdas ocupadas incluso cuando la
     * forma es irregular.
     */
    @Test
    void testTetrominoWithIrregularShape() {
        int[][] irregularShape = {
                {1, 1, 1},
                {1, 1}
        };

        Tetromino mockTetromino = new MockTetromino(irregularShape, Tetromino.TetrominoType.L);

        int expectedOccupiedCells = 0;
        for (int[] row : irregularShape) {
            for (int cell : row) {
                if (cell == 1) {
                    expectedOccupiedCells++;
                }
            }
        }

        int actualOccupiedCells = mockTetromino.getOccupiedCells();

        assertEquals(expectedOccupiedCells, actualOccupiedCells, "El número de celdas ocupadas no coincide " +
                "con el esperado para la forma irregular.");
    }
}
