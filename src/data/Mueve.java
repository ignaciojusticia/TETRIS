package data;
import javax.swing.JPanel;
import guitetris.TetrisFrame;
import data.*;
import guitetris.Tiempo;

/**
 * Esta clase implementa una hebra que hace que se mueva continuamente hacia abajo la Figura actual.
 *  La hebra se encarga tambi�n de ir refrescando la pantalla
 * dónde se dibuja todos. Además controla si la Figura
 * choca contra un muro o contra las piezas ya colocadas. 
 * 
 * @author Ignacio Justicia Ramos
 */
public class Mueve implements Runnable{
    private int delay;
    private boolean continuar=true;
    private boolean suspendFlag=true;
    private TetrisFrame frame;
    Tiempo tiempo;
    /**
     * Constructor de la clase, que inicializa la referencia utilizadas por
     * la hebra al TetrisMidlet, establece el retardo en milisegundos
     * entre movimiento y movimiento de la Figura actual, y comienza a ejecutar
     * la hebra. 
     */
    public Mueve(TetrisFrame fr,int nivel){
        frame=fr;
        delay= actualizaRetardo(nivel);
        Thread t=new Thread(this);
        t.start();
        tiempo = new Tiempo();
        tiempo.Contar();
    }
    
    /**
     * C�digo que constituye las sentencias de la hebra. En este caso, se encarga
     * de hacer que se muevan continuamente la Serpiente
     * y los Ratones. La hebra se encarga tambi�n de ir refrescando la pantalla
     * d�nde se dibuja todo, y los puntos acumulados. Adem�s controla si
     * la Serpiente choca contra un muro o contra s� misma, para comenzar
     * el juego de nuevo. Cuando la Serpiente come un Raton aumenta su longitud
     * en una celda.
     */
    public void run(){
        frame.getTexField().setText(tiempo.toString());
        try{
            while(continuar){
                frame.getTexField().setText(tiempo.toString());
                synchronized(this){
                    while(suspendFlag){
                        wait();
                    }
                }
                Thread.sleep(delay);
                frame.getTexField().setText(tiempo.toString());
                if(!frame.getRejilla().seChoca(frame.getFigura(),Figura.ABAJO)){
                    frame.getTexField().setText(tiempo.toString());
                    frame.getFigura().mueve(Figura.ABAJO);
                    if(frame.getPanel()!=null)
                        frame.getPanel().repaint();
                }
                else{
                    frame.getTexField().setText(tiempo.toString());
                    boolean valor=frame.getRejilla().copiaFiguraEnRejilla(frame.getFigura());
                    frame.getRejilla().eliminarFilasLlenas();
                    if(frame.getPanel()!=null)
                        frame.getPanel().repaint();
                    if(!valor)
                        frame.nuevaFigura();
                    else{
                        frame.getTexField().setText(tiempo.toString());
                        System.out.println("He llegado al final");
                        continuar=false;
                    }
                }
            }// end while(continuar)
        } catch(InterruptedException e){
            System.out.println("Hilo MueveSerpiente interrumpido");
        }
    }
    
    /**
     * Detiene momentaneamente la ejecución de la hebra, haciendo que la Figura actual
     * quede parada.
     */
    synchronized public void suspender(){
        frame.getPanel().repaint();
        suspendFlag=true;
    }
    
    /**
     * Reanuda el movimiento de la hebra. La Figura actual vuelve  a moverse.
     */
    public synchronized void reanudar(){
        if(frame.getPanel()!=null)
            frame.getPanel().repaint();
        suspendFlag = false;
        notify();
    }
    
    /**
     * Termina la ejecución de la hebra.
     */
    public void parar(){
        continuar=false;
    }
    
    /**
     * Nos dice si la hebra está o no parada.
     * @return true si la hebra de movimiento está parada, false en otro caso
     */
    synchronized public boolean getParado(){
        return suspendFlag;
    }
    
    /**
     * La siguiente función actualiza el retardo que espera la hebra
     * para mover la Figura actual. El nivel más lento será
     * el 0 (retardo 700) y el más rápido el 10 (retardo 50)
     */
    private int actualizaRetardo(int nivel) {
        if (nivel>10) nivel=10;
        else if (nivel<0) nivel=0;
        return ( 400-(nivel*35) );
    }
}
 
