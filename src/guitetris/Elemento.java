/*
 * Elemento.java
 *
 * Created on 18 de septiembre de 2005, 19:45
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package guitetris;

/**
 * Representa una de las celdas de las que se compone una pieza en el juego del tetris
 * 
 * @author Ignacio Justicia Ramos
 */
public class Elemento {
    private int fila;
    private int columna;
    /** 
     * Creates a new instance of Elemento 
     * @param f fila
     * @param c columna
     */
    public Elemento(int f,int c) {
        fila=f;
        columna=c;
    }
    
    /**
     * Obtiene la fila de este Elemento
     * @return la fila de este Elemento
     */
    public int getFila(){
        return fila;
    }
    
     /**
     * Obtiene la columna de este Elemento
     * @return la columna de este Elemento
     */
    public int getColumna(){
        return columna;
    }
    
    /**
     * Define la fila de este Elemento
     * @param valor el nuevo valor para la fila de este Elemento
     */
    public void setFila(int valor){
        fila=valor;
    }
    
    /**
     * Define la columna de este Elemento
     * @param valor el nuevo valor para la columna de este Elemento
     */    
    public void setColumna(int valor){
        columna=valor;
    }
    
}
 
