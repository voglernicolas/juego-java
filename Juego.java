package juego;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;

public class Juego extends Canvas {

    private static final long serialVersionUID = 1L;
    
    private static JFrame ventana;
    
    private static final String NOMBRE = "Juego";  
    private static final int ANCHO = 800;
    private static final int ALTO = 600;
    
    private Juego() {
        setPreferredSize(new Dimension(ANCHO, ALTO));
        
        ventana = new JFrame(NOMBRE);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setResizable(false);
        ventana.setLayout(new BorderLayout());
        //  Canvas al centro de la ventana
        ventana.add(this, BorderLayout.CENTER);
        //  Todo el tamanio se ajusta a la ventana
        ventana.pack();
        // Centra ventana
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }
    
    public static void main(String[] args){
        Juego juego = new Juego();
    }

    
}
