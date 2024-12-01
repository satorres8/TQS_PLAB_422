package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class TetrominoTest {

    private void verifyAllRotations(Tetromino tetromino, int[][][] expectedRotations) {
        for (int i = 0; i < expectedRotations.length; i++) {
            assertArrayEquals(expectedRotations[i], tetromino.getShape(),
                    "La rotación " + (i * 90) + "° no es correcta.");
            tetromino.rotate();
        }
    }

    // A continuación, realizamos las pruebas de CAJA NEGRA

    // Pruebas de Inicialización
    @Test
    public void testTetrominoInitializationValidTypes() {
        // Verificar que la forma de la pieza "I" es la esperada.
        Tetromino tetrominoI = new Tetromino(Tetromino.TetrominoType.I);
        int[][] expectedShapeI = {{1, 1, 1, 1}};
        assertArrayEquals(expectedShapeI, tetrominoI.getShape(), "La forma de la pieza I no es la esperada.");

        // Verificar que la forma de la pieza "O" es la esperada.
        Tetromino tetrominoO = new Tetromino(Tetromino.TetrominoType.O);
        int[][] expectedShapeO = {
                {1, 1},
                {1, 1}
        };
        assertArrayEquals(expectedShapeO, tetrominoO.getShape(), "La forma de la pieza O no es la esperada.");

        // Verificar que la forma de la pieza "T" es la esperada.
        Tetromino tetrominoT = new Tetromino(Tetromino.TetrominoType.T);
        int[][] expectedShapeT = {
                {0, 1, 0},
                {1, 1, 1}
        };
        assertArrayEquals(expectedShapeT, tetrominoT.getShape(), "La forma de la pieza T no es la esperada.");

        // Verificar que la forma de la pieza "S" es la esperada.
        Tetromino tetrominoS = new Tetromino(Tetromino.TetrominoType.S);
        int[][] expectedShapeS = {
                {0, 1, 1},
                {1, 1, 0}
        };
        assertArrayEquals(expectedShapeS, tetrominoS.getShape(), "La forma de la pieza S no es la esperada.");

        // Verificar que la forma de la pieza "Z" es la esperada.
        Tetromino tetrominoZ = new Tetromino(Tetromino.TetrominoType.Z);
        int[][] expectedShapeZ = {
                {1, 1, 0},
                {0, 1, 1}
        };
        assertArrayEquals(expectedShapeZ, tetrominoZ.getShape(), "La forma de la pieza Z no es la esperada.");

        // Verificar que la forma de la pieza "J" es la esperada.
        Tetromino tetrominoJ = new Tetromino(Tetromino.TetrominoType.J);
        int[][] expectedShapeJ = {
                {1, 0, 0},
                {1, 1, 1}
        };
        assertArrayEquals(expectedShapeJ, tetrominoJ.getShape(), "La forma de la pieza J no es la esperada.");

        // Verificar que la forma de la pieza "L" es la esperada.
        Tetromino tetrominoL = new Tetromino(Tetromino.TetrominoType.L);
        int[][] expectedShapeL = {
                {0, 0, 1},
                {1, 1, 1}
        };
        assertArrayEquals(expectedShapeL, tetrominoL.getShape(), "La forma de la pieza L no es la esperada.");
    }

    // Inicializacion Invalida
    @Test
    public void testTetrominoInitializationInvalidType() {
        // Verificar que un tipo de pieza no soportado lanza una excepción.
        assertThrows(IllegalArgumentException.class, () -> {
            new Tetromino(null);
        }, "Se esperaba una IllegalArgumentException para un tipo de pieza inválido.");
    }

    // **Pruebas de Rotación**

    @Test
    public void testAllRotationsForAllTetrominoes() {
        // **Pieza "I"**
        Tetromino tetrominoI = new Tetromino(Tetromino.TetrominoType.I);
        int[][][] expectedRotationsI = {
                {{1, 1, 1, 1}},  // Rotación 0° (original)
                {{1}, {1}, {1}, {1}},  // Rotación 90°
                {{1, 1, 1, 1}},  // Rotación 180°
                {{1}, {1}, {1}, {1}}  // Rotación 270°
        };
        verifyAllRotations(tetrominoI, expectedRotationsI);

        // **Pieza "O" (no rota)**
        Tetromino tetrominoO = new Tetromino(Tetromino.TetrominoType.O);
        int[][][] expectedRotationsO = {
                {{1, 1}, {1, 1}},  // Rotación 0° (y todas las demás)
                {{1, 1}, {1, 1}},  // Repetida
                {{1, 1}, {1, 1}},  // Repetida
                {{1, 1}, {1, 1}}   // Repetida
        };
        verifyAllRotations(tetrominoO, expectedRotationsO);

        // **Pieza "T"**
        Tetromino tetrominoT = new Tetromino(Tetromino.TetrominoType.T);
        int[][][] expectedRotationsT = {
                {{0, 1, 0}, {1, 1, 1}},  // Rotación 0° (original)
                {{1, 0}, {1, 1}, {1, 0}},  // Rotación 90°
                {{1, 1, 1}, {0, 1, 0}},  // Rotación 180°
                {{0, 1}, {1, 1}, {0, 1}}  // Rotación 270°
        };
        verifyAllRotations(tetrominoT, expectedRotationsT);

        // **Pieza "S"**
        Tetromino tetrominoS = new Tetromino(Tetromino.TetrominoType.S);
        int[][][] expectedRotationsS = {
                {{0, 1, 1}, {1, 1, 0}},  // Rotación 0° (original)
                {{1, 0}, {1, 1}, {0, 1}},  // Rotación 90°
                {{0, 1, 1}, {1, 1, 0}},  // Rotación 180°
                {{1, 0}, {1, 1}, {0, 1}}  // Rotación 270°
        };
        verifyAllRotations(tetrominoS, expectedRotationsS);

        // **Pieza "Z"**
        Tetromino tetrominoZ = new Tetromino(Tetromino.TetrominoType.Z);
        int[][][] expectedRotationsZ = {
                {{1, 1, 0}, {0, 1, 1}},  // Rotación 0° (original)
                {{0, 1}, {1, 1}, {1, 0}},  // Rotación 90°
                {{1, 1, 0}, {0, 1, 1}},  // Rotación 180°
                {{0, 1}, {1, 1}, {1, 0}}  // Rotación 270°
        };
        verifyAllRotations(tetrominoZ, expectedRotationsZ);

        // **Pieza "J"**
        Tetromino tetrominoJ = new Tetromino(Tetromino.TetrominoType.J);
        int[][][] expectedRotationsJ = {
                {{1, 0, 0}, {1, 1, 1}},  // Rotación 0° (original)
                {{1, 1}, {1, 0}, {1, 0}},  // Rotación 90°
                {{1, 1, 1}, {0, 0, 1}},  // Rotación 180°
                {{0, 1}, {0, 1}, {1, 1}}  // Rotación 270°
        };
        verifyAllRotations(tetrominoJ, expectedRotationsJ);

        // **Pieza "L"**
        Tetromino tetrominoL = new Tetromino(Tetromino.TetrominoType.L);
        int[][][] expectedRotationsL = {
                {{0, 0, 1}, {1, 1, 1}},  // Rotación 0° (original)
                {{1, 0}, {1, 0}, {1, 1}},  // Rotación 90°
                {{1, 1, 1}, {1, 0, 0}},  // Rotación 180°
                {{1, 1}, {0, 1}, {0, 1}}  // Rotación 270°
        };
        verifyAllRotations(tetrominoL, expectedRotationsL);
    }

    // **Pruebas de Celdas Ocupadas**

    @Test
    public void testGetOccupiedCells() {
        // Verificar que el conteo de celdas ocupadas para la pieza "I" es correcto.
        Tetromino tetrominoI = new Tetromino(Tetromino.TetrominoType.I);
        assertEquals(4, tetrominoI.getOccupiedCells(), "El número de celdas ocupadas para la pieza I no es correcto.");

        // Verificar que el conteo de celdas ocupadas para la pieza "O" es correcto.
        Tetromino tetrominoO = new Tetromino(Tetromino.TetrominoType.O);
        assertEquals(4, tetrominoO.getOccupiedCells(), "El número de celdas ocupadas para la pieza O no es correcto.");

        // Verificar que el conteo de celdas ocupadas para la pieza "T" es correcto.
        Tetromino tetrominoT = new Tetromino(Tetromino.TetrominoType.T);
        assertEquals(4, tetrominoT.getOccupiedCells(), "El número de celdas ocupadas para la pieza T no es correcto.");

        // Verificar que el conteo de celdas ocupadas para la pieza "S" es correcto.
        Tetromino tetrominoS = new Tetromino(Tetromino.TetrominoType.S);
        assertEquals(4, tetrominoS.getOccupiedCells(), "El número de celdas ocupadas para la pieza S no es correcto.");

        // Verificar que el conteo de celdas ocupadas para la pieza "Z" es correcto.
        Tetromino tetrominoZ = new Tetromino(Tetromino.TetrominoType.Z);
        assertEquals(4, tetrominoZ.getOccupiedCells(), "El número de celdas ocupadas para la pieza Z no es correcto.");

        // Verificar que el conteo de celdas ocupadas para la pieza "J" es correcto.
        Tetromino tetrominoJ = new Tetromino(Tetromino.TetrominoType.J);
        assertEquals(4, tetrominoJ.getOccupiedCells(), "El número de celdas ocupadas para la pieza J no es correcto.");

        // Verificar que el conteo de celdas ocupadas para la pieza "L" es correcto.
        Tetromino tetrominoL = new Tetromino(Tetromino.TetrominoType.L);
        assertEquals(4, tetrominoL.getOccupiedCells(), "El número de celdas ocupadas para la pieza L no es correcto.");
    }

    // A continuación, realizamos las pruebas de CAJA BLANCA

    // Probar casos donde la pieza debe Rotar (O) y donde no (el resto de casos)
    @Test
    public void testRotateTetrominoO() {
        // Caso donde el tipo es O y no debe rotar
        Tetromino tetrominoO = new Tetromino(Tetromino.TetrominoType.O);
        int[][] shapeBefore = tetrominoO.getShape();
        tetrominoO.rotate();
        int[][] shapeAfter = tetrominoO.getShape();
        assertArrayEquals(shapeBefore, shapeAfter, "La pieza O no debería cambiar al rotar.");
    }

    @Test
    public void testRotateTetrominoNonO() {
        // Caso donde el tipo no es O y debe rotar
        Tetromino tetrominoL = new Tetromino(Tetromino.TetrominoType.L);
        int[][] shapeBefore = tetrominoL.getShape();
        tetrominoL.rotate();
        int[][] shapeAfter = tetrominoL.getShape();
        assertFalse(Arrays.deepEquals(shapeBefore, shapeAfter), "La pieza L debería cambiar al rotar.");
    }

    // Asegurar que se maneja el caso cuando type es null y cuando es un valor válido. (Decision Statement)

    @Test
    public void testConstructorWithNullType() {
        // Caso donde el tipo es null y debe lanzar excepción
        assertThrows(IllegalArgumentException.class, () -> {
            new Tetromino(null);
        }, "Se esperaba una IllegalArgumentException al pasar un tipo nulo.");
    }

    @Test
    public void testConstructorWithValidType() {
        // Caso donde el tipo es válido y no debe lanzar excepción
        assertDoesNotThrow(() -> {
            new Tetromino(Tetromino.TetrominoType.S);
        }, "No se esperaba una excepción con un tipo válido.");
    }
}
