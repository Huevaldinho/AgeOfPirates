package ObjetosJuego;

import Arma.Arma;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Conector extends Item{
    public static Arma tipoArma;
    Conector(){
        loadImage();
        this.nombre = "Conectores";
        this.precio = 100;
        super.numero=setIDItem();
    }
    private void loadImage(){
        try {
            imagen = ImageIO.read(new File("images/conector.png"));
        } catch (IOException e) {
            System.out.println("No se pudo abrir la imagen");
            e.printStackTrace();
        }
    }

}
