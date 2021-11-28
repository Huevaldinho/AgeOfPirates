package ObjetosJuego;

import Arma.Arma;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Mina extends Item{
    public static Arma tipoArma;
    public Mina(){
        loadImage();
        this.nombre = "Mina";
        this.precio = 1000;
        super.numero=setIDItem();
    }
    private void loadImage(){
        try {
            imagen = ImageIO.read(new File("images/mina.png"));
        } catch (IOException e) {
            System.out.println("No se pudo abrir la imagen");
            e.printStackTrace();
        }
    }
}
