package control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Teclado implements KeyListener{

    private final static int numeroTeclas = 120;
    private final boolean[] teclas =  new boolean[numeroTeclas];
    
    public boolean arriba, abajo, izquierda, derecha;
    
    public void actualizar() {
        arriba = teclas[KeyEvent.VK_W];   
        abajo = teclas[KeyEvent.VK_S];
        izquierda = teclas[KeyEvent.VK_A];
        derecha = teclas[KeyEvent.VK_D];
    }
    
    
    public void keyTyped(KeyEvent e) {
        
    }

    
    public void keyPressed(KeyEvent e) {
        teclas[e.getKeyCode()] = true;
    }

    
    public void keyReleased(KeyEvent e) {
        teclas[e.getKeyCode()] = false;
    }
    
}
