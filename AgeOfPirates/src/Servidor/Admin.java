package Servidor;

import GUI.Player;
import General.IConstantes;
import General.Peticion;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Admin {
    private int cantidadPlayersActivos;
    private int cantidadMaximaPlayers;
    private ArrayList<JTextArea> paneles;
    private ArrayList<Player> players;
    private Point ultimoPunto;

    public Admin(){
        ultimoPunto=null;
        players= new ArrayList<>();
        cantidadMaximaPlayers= IConstantes.MAX_PLAYERS;
        paneles = new ArrayList<>();
        System.out.println("Instancia el administrador en controlador");
    }
    public int RegistrarPlayer(Peticion peticion){//Solo le da el ID al player
        if (cantidadPlayersActivos<cantidadMaximaPlayers){
            cantidadPlayersActivos++;
            return cantidadPlayersActivos;
        }
        return -1;
    }
    public int GetCantidadPlayersActivos(){
        return cantidadPlayersActivos;
    }
    public void AgregarPanelArrayList(Peticion peticion){
        JTextArea panel = (JTextArea) peticion.getDatosEntrada();
        paneles.add(panel);
    }
    public  ArrayList<JTextArea> EscribirEnCuadroTexto(Peticion peticion){
        //Castea el arrayList
        ArrayList<JTextArea> objetos =(ArrayList<JTextArea>) peticion.getDatosEntrada();
        int IDjugador = (int) peticion.getDatosSalida();//Saca el ID del que lo envio
        String mensajeEntrante = objetos.get(1).getText();//Saca el mensaje

        if (mensajeEntrante.compareTo("")==0)//Mensaje vacio == actualiza
            return paneles;
        for (JTextArea actual: paneles){
            actual.append('\n'+"Mensaje del jugador "+IDjugador+": "+mensajeEntrante);
            //actual.paintImmediately(actual.getBounds());
            //actual.setText(actual.getText()+'\n'+"Mensaje del jugador "+IDjugador+": "+mensajeEntrante);
            //actual.paintImmediately(actual.getX(), actual.getY(), actual.getWidth(), actual.getHeight());
        }
        return  paneles;
    }
    public void InsertarUltimoPunto(Point punto){
        ultimoPunto=punto;
        System.out.println("Ultimo punto en servidor: "+ultimoPunto);
        //Buscar el jugador en el array y buscar si el punto esta disponible
    }
    public void AgregarJugador(Peticion peti){
        players.add((Player) peti.getDatosEntrada());
        ImprimirJugadores();
    }
    public void ImprimirJugadores(){
        for (Player actual:players)
            System.out.println("Jugador registrado en servidor: "+actual.getID());
    }
    public void JugadorListo(Peticion peticion){
        int idJugador= (int) peticion.getDatosEntrada();
        for (Player actual:players){
            if (actual.getID()==idJugador){
                actual.setListo(true);
                System.out.println("Jugador listo:"+actual.getID());
            }
        }
    }
}
