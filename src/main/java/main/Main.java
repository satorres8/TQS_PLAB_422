package main;

import model.GameBoard;
import view.GameView;
import controller.GameController;

public class Main {
    public static void main(String[] args) {
        GameBoard gameBoard = new GameBoard();
        GameView gameView = new GameView();
        GameController gameController = new GameController(gameBoard, gameView);

        gameController.startGame();
    }
}