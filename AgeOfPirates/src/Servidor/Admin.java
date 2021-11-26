package Servidor;

import General.Peticion;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Admin {
    private int cantidadPlayersActivos;
    private int cantidadMaximaPlayers;
    //private ArrayList<Player> players;
    public Admin(){
        //players= new ArrayList<Player>();
        cantidadMaximaPlayers=4;
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

}
