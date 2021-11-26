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
    public boolean RegistrarPlayer(Peticion peticion){
        System.out.println("Funcion registrar player");

        cantidadPlayersActivos++;
        return true;
    }
    public boolean AgregarPlayer(Peticion peticion){
        if (GetCantidadPlayersActivos()<cantidadMaximaPlayers){
            //Que se le pase el player en datos entrada
            //players.add(peticion.getDatosEntrada());
            return true;
        }
        System.out.println("No puede insertar, cantidad maxima de jugadores alcanzada");
        return false;

    }
    public int GetCantidadPlayersActivos(){
        return cantidadPlayersActivos;
    }

}
