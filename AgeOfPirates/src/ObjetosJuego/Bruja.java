package ObjetosJuego;

import Arma.Arma;
import Cliente.Client;
import Comodines.Escudo;
import Comodines.Kraken;
import General.Peticion;
import General.TipoAccion;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Bruja extends Item implements ActionListener{
    public static Arma tipoArma;
    private int DELAY = super.getVelocidadBruja();
    private Timer timer;

    public Bruja(){
        super.vivo=true;
        this.nombre = "Templo de la Bruja";
        this.precio = 2500;
        super.numero=setIDItem();
        super.agregadoAlGrid=false;
        super.puntosUbicacion= new ArrayList<>();
        super.rutaImage="images/witch.png";
        super.yaTieneConector=false;

        timer = new Timer(DELAY,this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Hilo de bruja crea un comodin
        System.out.println("\nHilo bruja corre\n");
        Random numAleatorio = new Random();
        boolean comodin = numAleatorio.nextBoolean();
        Kraken kraken = null;;
        Escudo escudo = null;
        if (comodin) {
            System.out.println("Escudo");
            escudo = new Escudo();
            Peticion peticionescudo = new Peticion(TipoAccion.GENERAR_COMODIN_BRUJA,jugador);
            peticionescudo.setDatosSalida(escudo);
            Client conexionEscudo = new Client(peticionescudo);
        } else {
            System.out.println("Kraken");
            kraken = new Kraken();
            Peticion peticionKraken = new Peticion(TipoAccion.GENERAR_COMODIN_BRUJA,jugador);
            peticionKraken.setDatosSalida(kraken);
            Client conexionKraken = new Client(peticionKraken);
        }
    }
}
