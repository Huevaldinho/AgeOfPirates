package ObjetosJuego;

import Arma.Arma;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FuenteEnergia extends Item{
    public static Arma tipoArma;
    FuenteEnergia(){
        loadImage();
        this.nombre = "Fuente de Energía";
        this.precio = 12000;
        super.numero=setIDItem();
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
