/*
 * Figura.java
 *
 * Created on 18 de septiembre de 2005, 19:38
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package guitetris;

import java.util.Vector;

/**
 * Representa una pieza del juego del tetris. Existen 7 tipos de piezas en este juego (ver el constructor).
 * 
 * @author Ignacio Justicia Ramos
 */
public class Figura {
    public static final int IZQUIERDA         = 0;
    public static final int DERECHA           = 1;
    public static final int ABAJO             = 2;
    public static final int ARRIBA            = 3;
    
    
    private Vector<Elemento> elements;
    private int xorigen;
    private int yorigen;
    private int tipo;
    /** 
     * Creates a new instance of Figura 
     * @param fila0 utilizado para definir las celdas ocupadas por esta Figura en la primera fila
     * @param fila1 utilizado para definir las celdas ocupadas por esta Figura en la segunda  fila
     * @param fila2 utilizado para definir las celdas ocupadas por esta Figura en la tercera  fila
     * @param fila3 utilizado para definir las celdas ocupadas por esta Figura en la cuarta fila
     */
    public Figura(int fila0,int fila1,int fila2,int fila3,int t) {
        elements = new Vector<Elemento>();
        addElements(0,fila0);
        addElements(1,fila1);
        addElements(2,fila2);
        addElements(3,fila3);
        this.tipo = t;
    }

    public int getTipo() {
        return tipo;
    }
    
    
    /**
     * Genera una nueva Figura, eligiendo el tipo de forma aleatoria entre las 7 posibilidades
     * @return la nueva Figura generada de forma aleatoria
     */
    public static Figura nuevaFigura(){
        Figura fig=null;
        java.util.Random rand=new java.util.Random();
        int tipoFigura=rand.nextInt(7);
        
        if(tipoFigura==0){
            fig = new Figura(
                    0x0000,
                    0x0FF0,
                    0x0FF0,
                    0x0000,0);
        }else if(tipoFigura==1){
            fig = new Figura(
                    0x0F00,
                    0x0F00,
                    0x0FF0,
                    0x0000,1);
        }else if(tipoFigura==2){
            fig = new Figura(
                    0x00F0,
                    0x00F0,
                    0x0FF0,
                    0x0000,2);
        }else if(tipoFigura==3){
            fig = new Figura(
                    0x0000,
                    0x0F00,
                    0xFFF0,
                    0x0000,3);
        }else if(tipoFigura==4){
            fig = new Figura(
                    0x0F00,
                    0x0F00,
                    0x0F00,
                    0x0F00,4);
        }else if(tipoFigura==5){
            fig = new Figura(
                    0x0F00,
                    0x0FF0,
                    0x00F0,
                    0x0000,5);
        }else if(tipoFigura==6){
            fig = new Figura(
                    0x00F0,
                    0x0FF0,
                    0x0F00,
                    0x0000,6);
        }
        fig.xorigen=3;
        fig.yorigen=0;
        
        return fig;
    }
    
    /**
     * Añade los Elementos  ocupadas en la fila indicada como parámetro  a la Figura actual
     * @param fila la fila
     * @param valor entero usado como máscara hexadecimal para indicar las casillas ocupadas en la fila (ver el constructor)
     */
    private void addElements(int fila, int valor){
        if((valor & 0xF000)>0) elements.addElement(new Elemento(fila,0));
        if((valor & 0x0F00)>0) elements.addElement(new Elemento(fila,1));
        if((valor & 0x00F0)>0) elements.addElement(new Elemento(fila,2));
        if((valor & 0x000F)>0) elements.addElement(new Elemento(fila,3));
    }
    
    /**
     * Obtiene el número de Elementos (celdas) que forma la Figura actual
     * @return el número de Elementos (celdas) que forma la Figura actual
     */
    public int getNElements(){
        return elements.size();
    }
    
    /**
     * Obtiene el Elemento en la posición pos de la Figura actual
     * @param pos la posición
     * @return el Elemento en la posición pos de la Figura actual
     */
    public Elemento getElementAt(int pos){
        return (Elemento)elements.elementAt(pos);
    }
    
    /**
     * Obtiene la posición x respecto al origen de coordenadas de la Rejilla de la Figura actual
     * @return la posición x respecto al origen de coordenadas de la Rejilla de la Figura actual
     */
    public int getXOrigen(){
        return xorigen;
    }
 
    /**
     * Obtiene la posición y respecto al origen de coordenadas de la Rejilla de la Figura actual
     * @return la posición y respecto al origen de coordenadas de la Rejilla de la Figura actual
     */
    public int getYOrigen(){
        return yorigen;
    }
    
    /**
     * Mueve la Figura actual una casilla en la dirección indicado por direccion (ABAJO,IZQUIERDA o ARRIBA)
     * @param direccion la dirección de movimiento (ABAJO,IZQUIERDA o ARRIBA)
     */
    public void mueve(int direccion){
        int c,d,i,j;
        if(direccion==ABAJO){
            yorigen++;;
        }else if(direccion==IZQUIERDA){
            xorigen--;
        }else if(direccion==DERECHA){
            xorigen++;
        }
    }
    
    /**
     * Rota la figura actual en sentido contrario a las agujas del reloj si al
     * hacerlo no choca contra ninguna otra figura ni contra los muros exteriores
     * @param rej La Rejilla del juego
     */
    public void rotar(Rejilla rej){
        Elemento elemento;
        int x,y,i;
        boolean sepuederotar=true;
        Vector<Elemento> newelements=new Vector<Elemento>();
        
        i=0;
        while(i<getNElements() && sepuederotar){
            elemento=getElementAt(i);
            x=elemento.getColumna();
            y=elemento.getFila();
            if(rej.getTipoCelda(y-1+getXOrigen(),-x+3+getYOrigen())!=Rejilla.VACIA){
                sepuederotar=false;
            } else{
                newelements.addElement(new Elemento(-x+3,y-1));
            }
            i++;
        }
        if(sepuederotar)
            elements=newelements;
    }
}
 
