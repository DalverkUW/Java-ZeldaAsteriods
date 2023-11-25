//Clase para cargar gráficos//
package Recursos;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class grafic {
    
    public static BufferedImage loadImg(String ruta){
        try {
            return ImageIO.read(grafic.class.getResource(ruta));
        } catch (IOException ex) {}
        return null;
    }
    
}
