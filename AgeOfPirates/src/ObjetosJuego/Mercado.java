package ObjetosJuego;

import Arma.Arma;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Mercado extends Item{
    public static Arma tipoArma;
    public Mercado(){
        //loadImage();
        super.nombre = "Mercado";
        super.precio = 2000;
        super.numero=setIDItem();
        super.cantidadDeEspacios=2;
        super.agregadoAlGrid=false;
        super.puntosUbicacion= new ArrayList<>();
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
