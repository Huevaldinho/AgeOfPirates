package ObjetosJuego;

import Arma.Arma;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Armeria extends Item{
    public static Arma tipoArma;
    public Armeria(){
        loadImage();
        super.precio = 1500;
        super.nombre = "Armería";
        super.numero=setIDItem();
        super.agregadoAlGrid=false;
        //super.cantidadDeEspacios=1;
    }
    private void loadImage(){
                try {
                    imagen = ImageIO.read(new File("images/armeria.png"));
                } catch (IOException e) {
                    System.out.println("No se pudo abrir la imagen");
                    e.printStackTrace();
                }
    }

}
