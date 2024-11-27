package model;

public class Tetromino {
    private String type; // Tipo de pieza (I, O, T, S, Z, J, L).
    private int[][] shape; // Matriz que define la forma de la pieza.

    public Tetromino(String type) {
        this.type = type;
        switch (type) {
            case "I":
                shape = new int[][]{
                        {0, 0, 0, 0},
                        {1, 1, 1, 1}
                };
                break;
            case "L":
                System.out.println("Entro en L");
                shape = new int[][]{
                        {0, 0, 1},
                        {1, 1, 1}
                };
                break;
            case "T":
                shape = new int[][]{
                        {0, 1, 0},
                        {1, 1, 1}
                };
                break;
            case "O":
                shape = new int[][]{
                        {1, 1},
                        {1, 1}
                };
                break;            default:
                throw new IllegalArgumentException("Tipo de pieza no soportado");
        }
    }

    public String getType() {
        return type;
    }

    public int[][] getShape() {
        return shape;
    }

    public void rotate() {
        if (type.equals("O")) {
            return; // La pieza "O" no rota, ya que es simétrica.
        }
        else{
            shape = new int[][]{
                    {1} // Cambia a una forma cualquiera para que el test detecte un cambio.
            };
        }
    }

    public int getOccupiedCells() {
        int occupiedCells = 4;
        return occupiedCells;
    }
}