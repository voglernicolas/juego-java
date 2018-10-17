package juego;

import control.Teclado;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

public class Juego extends Canvas implements Runnable {

    private static final long serialVersionUID = 1L;
    // Prevenir uso de variable simultaneamente con volatile
    private static volatile boolean gameStatus = false;
    
    private static JFrame ventana;
    private static Thread thread;
    private static Teclado teclado;
    
    private static int APS = 0;
    private static int FPS = 0;
    
    private static final String NOMBRE = "Juego";  
    private static final int ANCHO = 800;
    private static final int ALTO = 600;
    
    private Juego() {
        setPreferredSize(new Dimension(ANCHO, ALTO));
        
        teclado = new Teclado();
        addKeyListener(teclado);
        
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
        juego.iniciar();
    }
    
    
    // Synchronized = prevencion de uso simultaneo en threads
    private synchronized void iniciar(){
        gameStatus = true;
        // Indicamos que el objeto se ejecute en esta clase
        thread = new Thread(this, "Graficos");
        // Inicio de thread run
        thread.start();
        
    }
    
    private synchronized void finalizar() {
        gameStatus = false;
        
        try {
            // thread.stop cierra de forma abrupta, thread.join cierra progresivamente
            thread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void actualizar(){
        teclado.actualizar();
        if(teclado.arriba) {
            System.out.println("arriba");
        }
        if(teclado.abajo) {
            System.out.println("abajo");
        }
        if(teclado.izquierda) {
            System.out.println("izquierda");
        }
        if(teclado.derecha) {
            System.out.println("derecha");
        }
        
        APS++;
    }
    
    private void mostrar(){
        FPS++;
    }
    
    
    // Al implementar Runnable necesitamos el metodo run()
    public void run() {
        // Limitamos que el juego se ejecute x veces por segundo.
        // System.currentTimeMillis() es un metodo (comunmente usado)incorrecto que depende del S.O
        // Tomamos el tiempo de referencia del ciclo de reloj del procesador con System.nanoTime();.
        
        // Segundos en 1 nanosegundo
        final int NS_POR_SEGUNDO = 100000000;
        // Actualizaciones por segundo
        final byte APS_OBJETIVO = 60;
        // Cuantos nanosegundos tienen que transcurrir para obtener las actualizaciones por segundo
        final double NS_POR_ACTUALIZACION = NS_POR_SEGUNDO / APS_OBJETIVO;
        // Se atribuyen los segundos del procesador como referencia
        long referenciaActualizacion = System.nanoTime();
        long referenciaContador = System.nanoTime();
        
        double tiempoTranscurrido;
        // Cantidad de tiempo transcurrido para una actualizacion
        double delta = 0;
        
        // Inicia el juego con la ventana seleccionada
        requestFocus();
        
        
        while (gameStatus) {
            // Iniciamos el "cronometro"
            final long inicioBucle = System.nanoTime();
            // Medimos cuanto tiempo pasa entre referencia e inicioBucle
            tiempoTranscurrido = inicioBucle - referenciaActualizacion;
            // Al prinicipio usamos el primer nanosegundo para entrar en el bucle, una vez dentro del bucle usamos un nanotime mas preciso.
            // Al iniciar el bucle restamos el tiempo entre esta variable e iniciobucle.
            referenciaActualizacion = inicioBucle;
            
            delta += tiempoTranscurrido / NS_POR_ACTUALIZACION;
            
            while (delta >= 1){
                // Actualizar se ejecuta de manera exacta cada 60 veces por segundo
                actualizar();
                delta--;
            }

            mostrar();
            
            // Vemos en pantalla los frame y actualizaciones por segundo
            if (System.nanoTime() - referenciaContador > NS_POR_SEGUNDO) {
                ventana.setTitle(NOMBRE + " APS: " + APS + " ~~  FPS: " + FPS);
                APS = 0;
                FPS = 0;
                // Cada vez que el bucle gira tenemos una medicion precisa del tiempo
                referenciaContador = System.nanoTime();
            }
        }
    }
    
}
