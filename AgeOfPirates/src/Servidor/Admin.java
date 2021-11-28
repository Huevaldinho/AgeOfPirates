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
    public int ActualizarDinero(Peticion peticion){
        int IDJugador = (int) peticion.getDatosEntrada();
        for (Player actual:players){
            if (actual.getID()==IDJugador){
                return actual.getDinero();
            }
        }
        return 0;
    }
    public int ActualizarAcero(Peticion peticion){
        int IDJugador=(int)peticion.getDatosEntrada();
        for (Player actual:players){
            if (actual.getID()==IDJugador){
                int acero = actual.getAcero();
                return acero;
            }
        }
        return 0;
    }
    public void EliminarJugadorPorID(Peticion peticion){
        int buscado= (int) peticion.getDatosEntrada();//Castea el numero que le pasan
        for (Player actual:players){//Busca en lista de jugadores
            if (actual.getID()==buscado){//Lo encuentra
                actual.setVivo(false);//Lo mata
            }
        }
    }
    public void EliminarJugadorPorItems(){
        for (int i=0;i<players.size();i++){
            if (players.get(i).getItems().size()==0){//Se quedo sin items
                players.get(i).setVivo(false);//Lo mata
            }
        }
    }
    public String [] SetComboBoxInventario(Peticion peticion){
        int buscado = (int) peticion.getDatosEntrada();
        for (Player actual:players){
            if (actual.getID()==buscado){
                //Llena el array con los nombres de los items que tiene el player
                String [] items = new String[actual.getItems().size()];
                for (int i=0;i<items.length;i++){
                    items[i] = actual.getItems().get(i).nombre;
                }
                return items;
            }
        }
        return null;
    }
}
