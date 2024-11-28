package controller;

import model.GameBoard;
import model.Tetromino;
import view.GameView;

public class GameController {
    private GameBoard board;
    private Tetromino currentPiece;
    private int pieceRow;
    private int pieceCol;

    // Constructor
    public GameController(GameBoard boardm) {
        this.board = boardm;
    }

    /**
     * Inicia el juego con una pieza aleatoria centrada en la parte superior del tablero.
     */
    public void startGame() {
        currentPiece = generateRandomPiece(); // Genera una pieza aleatoria.
        pieceRow = 0;
        pieceCol = (board.getCols() - currentPiece.getWidth()) / 2; // Centrar la pieza.
    }

    /**
     * Genera una pieza aleatoria de tipo Tetromino.
     */
    private Tetromino generateRandomPiece() {
        Tetromino.TetrominoType[] types = Tetromino.TetrominoType.values();
        Tetromino.TetrominoType randomType = types[(int) (Math.random() * types.length)];
        return new Tetromino(randomType);
    }

    // ** Métodos de acceso **

    public Tetromino getCurrentPiece() {
        return currentPiece;
    }

    public int getPieceX() {
        return pieceCol;
    }

    public int getPieceY() {
        return pieceRow;
    }

    // ** Movimiento de la pieza **

    /**
     * Mueve la pieza actual hacia la izquierda si es posible.
     */
    public void movePieceLeft() {
        if (board.canPlacePiece(currentPiece, pieceRow, pieceCol - 1)) {
            pieceCol--;
        }
    }

    /**
     * Mueve la pieza actual hacia la derecha si es posible.
     */
    public void movePieceRight() {
        if (board.canPlacePiece(currentPiece, pieceRow, pieceCol + 1)) {
            pieceCol++;
        }
    }

    /**
     * Rota la pieza actual si es posible.
     */
    public void rotatePiece() {
        Tetromino rotatedPiece = new Tetromino(currentPiece.getType()); // Clonar pieza.
        rotatedPiece.rotate();
        if (board.canPlacePiece(rotatedPiece, pieceRow, pieceCol)) {
            currentPiece.rotate();
        }
    }

    /**
     * Baja la pieza un espacio si es posible.
     */
    public void movePieceDown() {
        if (board.canPlacePiece(currentPiece, pieceRow + 1, pieceCol)) {
            pieceRow++;
        } else {
            placeCurrentPiece();
        }
    }

    /**
     * Deja caer la pieza hasta que ya no pueda bajar más.
     */
    public void dropPiece() {
        while (board.canPlacePiece(currentPiece, pieceRow + 1, pieceCol)) {
            pieceRow++;
        }
        placeCurrentPiece();
    }

    /**
     * Coloca la pieza actual en el tablero y genera una nueva pieza.
     */
    private void placeCurrentPiece() {
        board.placePiece(currentPiece, pieceRow, pieceCol);
        currentPiece = null; // Indica que la pieza ha sido colocada.
    }

    // ** Estado del juego **

    /**
     * Indica si la pieza actual ya ha sido colocada.
     */
    public boolean isPiecePlaced() {
        return currentPiece == null;
    }

    /**
     * Finaliza el juego si no se puede colocar una nueva pieza en la parte superior.
     */
    public boolean isGameOver() {
        if (currentPiece == null) {
            Tetromino nextPiece = generateRandomPiece();
            return !board.canPlacePiece(nextPiece, 0, (board.getCols() - nextPiece.getWidth()) / 2);
        }
        return false;
    }
}
