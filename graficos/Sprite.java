package graficos;


public class Sprite {
    private final int box;
    
    private int x;
    private int y;
    
    private final HojaSprites hoja;
    public int[] pixeles;
    
    
    public Sprite(final int box, final int fila, final int columna, final HojaSprites hoja) {
        this.box = box;
        
        pixeles = new int[box * box];
        
        this.x = columna * box;
        this.y = columna * box;
        this.hoja = hoja;
        
        for (int y =0; y < box; y++) {
            for (int x=0; x < box; x++){
                pixeles[x+y * box] = hoja.pixeles[(x + this.x) + 
                        (y + this.y) * hoja.getAncho()];
            }
        }
    }

}

