package ObjetosJuego;

import Arma.Arma;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Mercado extends Item{
    public static Arma tipoArma;
    Mercado(){
        loadImage();
        this.nombre = "Mercado";
        this.precio = 2000;
        super.numero=setIDItem();
    }
    private void loadImage(){
        try {
            imagen = ImageIO.read(new File("images/mercado.png"));
        } catch (IOException e) {
            System.out.println("No se pudo abrir la imagen");
            e.printStackTrace();
        }
    }
}
