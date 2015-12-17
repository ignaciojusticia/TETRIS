package data;

/**
 * @author Ignacio Justicia Ramos
 * 
 * Esta clase representa una rejilla con una determinada Anchura
 * y Altura, en la que cada celda puede estar VACIA, contener
 * un BLOQUE (muro exterior) o PIEZA (trozo de una pieza)
 * 
 */
public class Rejilla{
    public static final int VACIA        = 0;
    public static final int BLOQUE       = 1;
    public static final int PIEZA        = 2;
    
    private int anchura;
    private int altura;
    
    private int[][] celdas;
    
    /**
     * Crea espacio para una rejilla con anchura igual a w  y altura
     * igual a h.
     * @param w anchura de la nueva Rejilla
     * @param h altura de la nueva Rejilla
     */
    public Rejilla(int w,int h){
        anchura=w;
        altura=h;
        celdas= new int[anchura][altura];
        initRejilla();
    }
    
    /**
     * Devuelve la anchura de la rejilla.
     * @return la anchura de la rejilla
     */
    public int getAnchura(){
        return anchura;
    }
    
    /**
     * Devuelve la altura de la rejilla.
     * @return la altura de la rejilla
     */
    public int getAltura(){
        return altura;
    }
       
    /**
     * Establece el tipo de celda en las coordenadas x e y de esta Rejilla
     *  @param x coordenada x (columna)
     * @param y coordenada y (fila)
     * @param valor el tipo de celda (VACIA, PIEZA o BLOQUE)
     */
    public void assignTipoCelda(int x,int y,int valor){
        celdas[x][y]= valor ;
    }
    
    /**
     * Obtiene el tipo de celda en las coordenadas x e y de esta Rejilla
     * @param x coordenada x (columna)
     * @param y coordenada y (fila)
     * @return el tipo de Celda en la coordenada x,y.
     */
    
    public int getTipoCelda(int x,int y){
        return celdas[x][y];
    }
    
    /**
     * Pone VACIA como tipo de celda, en todas las celdas de esta
     * Rejilla.
     */
    public void initRejilla(){
        int i,j;
        
        for(i=0;i<anchura;i++){
            for(j=0;j<altura;j++){
                celdas[i][j]=VACIA;
            }
        }       
        // Añadimos los muros exteriores
        for(i=0;i<anchura;i++){
            //celdas[i][0]=BLOQUE;
            celdas[i][altura-1]=BLOQUE;
        }
        for(j=1;j<altura-1;j++){
            celdas[0][j]=BLOQUE;
            celdas[anchura-1][j]=BLOQUE;
        }
    }
    
    /**
     * Pone las celdas de la figura como ocupadas en la rejilla en las posiciones correspondientes
     * @param fig La Figura que queremos copiar en la rejilla
     * @return Devuelve true si tras copiar la figura en la rejilla se ocupa alguna celda de
     * las 4 primeras filas. false en otro caso
     */ 
    boolean copiaFiguraEnRejilla(Figura fig){
        Elemento elemento;
        boolean valorDevuelto=false;
        
        for(int i=0;i<fig.getNElements();i++){
            elemento=fig.getElementAt(i);
            if(elemento.getFila()+fig.getYOrigen()<4)
                valorDevuelto=true;
            celdas[elemento.getColumna()+fig.getXOrigen()][elemento.getFila()+fig.getYOrigen()]=PIEZA;
        }
        return valorDevuelto;
    }   
    
    /**
     * Indica si al mover la figura una celda según la direccion indicada, se chocará con
     * alguna otra pieza ya colocada, o con los bordes de la rejilla
     * @param fig la Figura que queremos comprobar si se chocará
     * @param direccion de movimiento (Figura.ABAJO,Figura.DERECHA o FIGURA.IZQUIERDA)
     * @return true si se choca, false en caso contrario
     */
    public boolean seChoca(Figura fig, int direccion){
        Elemento elemento;
        for(int i=0;i<fig.getNElements();i++){
            elemento=fig.getElementAt(i);
            if(direccion==Figura.ABAJO){
                if(celdas[elemento.getColumna()+fig.getXOrigen()]
                        [elemento.getFila()+fig.getYOrigen()+1]!=VACIA){
                    return true;
                }
            } 
            else if(direccion==Figura.IZQUIERDA){
                if(celdas[elemento.getColumna()+fig.getXOrigen()-1]
                        [elemento.getFila()+fig.getYOrigen()]!=VACIA){
                    return true;
                }
            }
            else if(direccion==Figura.DERECHA){
                if(celdas[elemento.getColumna()+fig.getXOrigen()+1]
                        [elemento.getFila()+fig.getYOrigen()]!=VACIA){
                    return true;
                }
            }
        }       
        return false;
    }
    
    /**
     * Encuentra la primera fila que está completa (llena de elementos de piezas)
     * @param desdeFila la fila inicial donde se empieza la búsqueda
     * @return devuelve la primera fila que está completa (llena de elementos de piezas). Devuelve -1
     * si no encuentra ninguna fila llena.
     */
    int primeraFilaLlena(int desdeFila){
        boolean encontrada=false;
        
        while(desdeFila<altura-1 && !encontrada)
        {
            if(filaLlena(desdeFila)){
                encontrada=true;
            }
            else
                desdeFila++;
        }
        if(encontrada)
            return desdeFila;
        else
            return -1;
    }
    
    /**
     * Devuelve la ultima fila vacia empezando a buscar en la fila fvacia
     * @param fvacia la fila en la que se comienza a comprobar si está vacía
     * @return la ultima fila vacia empezando a buscar en la fila fvacia
     */
    int ultimaFilaVacia(int fvacia){
        boolean encontrada=false;
        
        while((fvacia<altura-1) && !encontrada){
            if (!filaVacia(fvacia)){
                encontrada=true;
            }
            else
                fvacia++;
        }      
        return fvacia-1;             
    }
    
    /**
     * Permite saber si la fila pasada como parámetro está o no totalmente vacía
     * @param fila la fila
     * @return true si la fila está vacía, else en otro caso
     */
    boolean filaVacia(int fila){
        int i=1;
        boolean vacia=true;
        while((i<anchura-1) && vacia){
            if(celdas[i][fila]!=VACIA)
                vacia=false;
            i++;
        }
        return vacia;
    }
    
    /**
     * Permite saber si la fila pasada como parámetro está o no totalmente llena
     * @param fila la fila
     * @return true si la fila está llena, else en otro caso
     */
    boolean filaLlena(int fila){
        int i=1;
        boolean llena=true;
        while((i<anchura-1) && llena){
            if(celdas[i][fila]!=PIEZA)
                llena=false;
            i++;
        }
        return llena;
    }
    
    /**
     * Elimina las flas llenas en esta Rejilla, desplazando hacia abajo las filas que haya encima
     */
    void eliminarFilasLlenas(){
        boolean hayfilasllenas=true;
        int filavacia=ultimaFilaVacia(0);
        int filallena=0;
        
        while(hayfilasllenas){
            filallena=primeraFilaLlena(filallena);
            if(filallena>=0 && filallena<altura){
                for(int i=filallena-1;i>=filavacia;i--){
                    if(i==-1){
                        for(int j=0;j<anchura;j++){
                            celdas[j][0]=VACIA;
                        }
                    }
                    else
                        copiarFilas(i,i+1);
                }
                filavacia++;
                filallena++;
            }
            else
                hayfilasllenas=false;
        }
    }
    
    /**
     * Copia el contenido de la fila forigen en la fila fdestino
     * @param forigen fila origen
     * @param fdestino fila destino
     */
    void copiarFilas(int forigen, int fdestino){
        for(int i=0;i<anchura;i++){
            celdas[i][fdestino]=celdas[i][forigen];
        }
    }
}

 
