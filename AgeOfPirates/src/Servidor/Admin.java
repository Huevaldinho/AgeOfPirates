package Servidor;

import General.Peticion;

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Admin {
    private int cantidadPlayersActivos;
    private int cantidadMaximaPlayers;
    private ArrayList<JTextArea> paneles;
    //private ArrayList<Player> players;
    public Admin(){
        //players= new ArrayList<Player>();
        cantidadMaximaPlayers=4;
        paneles = new ArrayList<>();
        System.out.println("Instancia el administrador en controlador");
    }
    public int RegistrarPlayer(Peticion peticion){//Solo le da el ID al player
        System.out.println("Funcion registrar player");
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
        System.out.println("AGREGA TEXFIELD AL ARRAYLIST");
    }
    public  ArrayList<JTextArea> EscribirEnCuadroTexto(Peticion peticion){
        //Castea el arrayList
        ArrayList<JTextArea> objetos =(ArrayList<JTextArea>) peticion.getDatosEntrada();
        int IDjugador = (int) peticion.getDatosSalida();//Saca el ID del que lo envio
        String mensajeEntrante = objetos.get(1).getText();//Saca el mensaje

        objetos.get(0).setText(objetos.get(0).getText()+'\n'+"Mensaje del jugador "+IDjugador+": "+mensajeEntrante);
        for (JTextArea actual: paneles){
            actual.setText(actual.getText()+'\n'+"Mensaje del jugador "+IDjugador+": "+mensajeEntrante);
        }
        return  paneles;
    }
}
