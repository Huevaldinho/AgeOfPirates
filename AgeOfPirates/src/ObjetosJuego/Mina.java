package ObjetosJuego;

import Arma.Arma;
import Cliente.Client;
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

public class Mina extends Item implements ActionListener {
    public static Arma tipoArma;
    private int DELAY = super.getVelocidad();
    private Timer timer;

    public Mina(){
        super.vivo=true;
        super.nombre = "Mina";
        super.precio = 1000;
        super.numero=setIDItem();
        super.cantidadDeEspacios=2;
        super.agregadoAlGrid=false;
        super.puntosUbicacion= new ArrayList<>();
        super.rutaImage="images/mina.png";
        super.yaTieneConector=false;

        timer = new Timer(DELAY,this);
        timer.start();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        DELAY = super.getVelocidad();
        timer.stop();
        timer = new Timer(DELAY,this);
        timer.start();
        //Hilo para crear acero de Mina, Falta poder configurar el tiempo y la cantidad que procesa
        Peticion peticion = new Peticion(TipoAccion.BUSCAR_ITEM_POR_ID,numero);
        peticion.setDatosSalida(super.jugador);
        peticion.setDatosExtra(nombre);
        Client conexion = new Client(peticion);
        if (conexion!=null){
            Item item = (Item) conexion.getRespuestaServer();
            agregadoAlGrid=item.getAgregadoAlGrid();
        }
        if (agregadoAlGrid && vivo){
            Peticion peticionSumarAcero = new Peticion(TipoAccion.SUMAR_ACERO_JUGADOR,jugador);
            peticionSumarAcero.setDatosSalida(super.getCapacidadDeProcesamiento());
            Client conexionAcero = new Client(peticionSumarAcero);
        }else{
            System.out.println("AUN NO SE COLOCA LA MINA EN EL GRID O LA MINA MURIO");
        }
    }
}
