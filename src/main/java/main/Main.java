package main;

import controller.GameController;
import model.GameBoard;
import view.GameView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        GameBoard gameBoard = new GameBoard(20, 10); // Tablero de 20 filas x 10 columnas
        GameView gameView = new GameView(gameBoard);

        JFrame frame = new JFrame("Tetris");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(gameView);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        // Crear el controlador con un callback para redibujar
        GameController gameController = new GameController(gameBoard, gameView::repaint);
        frame.addKeyListener(gameController);

        frame.setVisible(true);

        // Bucle principal del juego (caída automática)
        new Timer(500, e -> {
            gameBoard.moveTetrominoDown();
            gameView.repaint();
        }).start();
    }
}
