package view;

import javax.swing.*;

public class GameView extends JFrame {
    public GameView() {
        // Configura la ventana principal de la interfaz gráfica
        setTitle("Tetris");
        setSize(400, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
