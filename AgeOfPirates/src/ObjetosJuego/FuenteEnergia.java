package ObjetosJuego;

import Arma.Arma;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FuenteEnergia extends Item{
    public FuenteEnergia(){
        //loadImage();
        super.nombre = "Fuente de Energía";
        super.precio=12000;
        super.numero=setIDItem();
        super.cantidadDeEspacios=4;
    }
    private void loadImage(){
        try {
            imagen = ImageIO.read(new File("images/fuentepoder.png"));
        } catch (IOException e) {
            System.out.println("No se pudo abrir la imagen");
            e.printStackTrace();
        }
    }

}
