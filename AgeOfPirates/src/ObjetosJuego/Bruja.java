package ObjetosJuego;

import Arma.Arma;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Bruja extends Item{
    public static Arma tipoArma;
    public Bruja(){
        loadImage();
        this.nombre = "Templo de la Bruja";
        this.precio = 2500;
        super.numero=setIDItem();
        super.agregadoAlGrid=false;
    }
    private void loadImage(){
        try {
            imagen = ImageIO.read(new File("images/witch.png"));
        } catch (IOException e) {
            System.out.println("No se pudo abrir la imagen");
            e.printStackTrace();
        }
    }

}
