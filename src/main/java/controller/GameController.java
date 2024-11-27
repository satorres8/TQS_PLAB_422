package controller;

import model.GameBoard;
import model.Tetromino;

public class GameController {
    private GameBoard board;
    private Tetromino currentPiece;
    private int pieceRow;
    private int pieceCol;

    public GameController(GameBoard board, Object view) { // `view` puede integrarse más adelante.
        this.board = board;
    }

    public void startGame() {
        currentPiece = new Tetromino("T"); // Ejemplo: empieza con una pieza "T".
        pieceRow = 0;
        pieceCol = board.getCols() / 2; // Centrar la pieza.
    }

    public Tetromino getCurrentPiece() {
        return currentPiece;
    }

    public int getPieceX() {
        return pieceCol;
    }

    public void movePieceLeft() {
        if (board.canPlacePiece(currentPiece, pieceRow, pieceCol - 1)) {
            pieceCol--;
        }
    }

    public void movePieceRight() {
        if (board.canPlacePiece(currentPiece, pieceRow, pieceCol + 1)) {
            pieceCol++;
        }
    }

    public void dropPiece() {
        while (board.canPlacePiece(currentPiece, pieceRow + 1, pieceCol)) {
            pieceRow++;
        }
        board.placePiece(currentPiece, pieceRow, pieceCol);
        currentPiece = null; // Simula que la pieza se coloca.
    }

    public boolean isPiecePlaced() {
        return currentPiece == null;
    }
}