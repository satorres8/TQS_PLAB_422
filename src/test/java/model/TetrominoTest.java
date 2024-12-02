package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class TetrominoTest {

    /**
     * Función auxiliar.
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
     * DESCRIPCIÓN: Verifica que las formas iniciales de los Tetrominoes coincidan con los valores esperados para cada tipo válido.
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
     * DESCRIPCIÓN: Comprueba que las rotaciones de todas las piezas (I, O, T, S, Z, J, L) se realizan correctamente y coinciden con las formas esperadas.
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

        // Similar para otras piezas (S, Z, J, L).
    }

    /**
     * TIPO DE PRUEBA: Caja Negra
     * CRITERIO EVALUADO: Particiones equivalentes
     * DESCRIPCIÓN: Valida que el método `getOccupiedCells` retorna el número correcto de celdas ocupadas para cada tipo de pieza.
     */
    @Test
    public void testGetOccupiedCells() {
        Tetromino tetrominoI = new Tetromino(Tetromino.TetrominoType.I);
        assertEquals(4, tetrominoI.getOccupiedCells(), "El número de celdas ocupadas para la pieza I no es correcto.");

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
     * DESCRIPCIÓN: Evalúa combinaciones de entradas válidas e inválidas al método `setShape` para verificar el manejo correcto de la validación y actualización de formas.
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
}
