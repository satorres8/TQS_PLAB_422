package controller;

import javax.swing.*;
import java.awt.event.KeyEvent;

/**
 * Clase auxiliar que extiende KeyEvent para facilitar la creación de eventos de teclado en las pruebas.
 *
 * Permite simular eventos de teclado personalizados sin necesidad de componentes gráficos reales.
 */

public class MockKeyEvent extends KeyEvent {
    public MockKeyEvent(int keyCode) {
        super(new JPanel(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, keyCode, KeyEvent.CHAR_UNDEFINED);
    }
}
