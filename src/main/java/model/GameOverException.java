package model;

/**
 * Excepción personalizada para manejar el estado de fin de juego.
 */
public class GameOverException extends RuntimeException {
    public GameOverException(String message) {
        super(message);
    }
}
