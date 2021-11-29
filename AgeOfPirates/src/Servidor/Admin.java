package Servidor;

import GUI.Player;
import General.IConstantes;
import General.Peticion;
import ObjetosJuego.Item;

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
    private Point ultimoPuntoJugador1;
    private Point ultimoPuntoJugador2;
    private Point ultimoPuntoJugador3;
    private Point ultimoPuntoJugador4;

    public Admin(){
        ultimoPunto=null;
        players= new ArrayList<>();
        cantidadMaximaPlayers= IConstantes.MAX_PLAYERS;
        paneles = new ArrayList<>();
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
    public void InsertarUltimoPunto(Peticion peticion){
        int jugador = (int)peticion.getDatosSalida();
        Point punto =(Point)peticion.getDatosEntrada();
        switch (jugador){
            case 1:{
                ultimoPuntoJugador1=punto;
                System.out.println("Ultimo punto jugador 1: "+punto);
                break;
            }
            case 2:{
                ultimoPuntoJugador2=punto;
                System.out.println("Ultimo punto jugador 2: "+punto);
                break;
            } case 3:{
                ultimoPuntoJugador3=punto;
                System.out.println("Ultimo punto jugador 3: "+punto);
                break;
            }
            case 4:{
                ultimoPuntoJugador4=punto;
                System.out.println("Ultimo punto jugador 4: "+punto);
                break;
            }
        }
    }
    public Point GetUltimoPunto(Peticion peticion){
        int jugadorQuePide = (int) peticion.getDatosEntrada();
        switch (jugadorQuePide){
            case 1: return ultimoPuntoJugador1;
            case 2: return ultimoPuntoJugador2;
            case 3: return ultimoPuntoJugador3;
            case 4: return ultimoPuntoJugador4;
        }
        return null;
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
            if (actual.getID()==buscado){//Jugador buscado
                //Llena el array con los nombres de los items que tiene el player
                ArrayList<Item> itemsPlayer = actual.getItems();
                String [] items = new String[actual.getItems().size()];
                for (int i=0;i<items.length;i++){//Recorre los items buscando cuales estan disponibles
                    if (itemsPlayer.get(i).getAgregadoAlGrid()==false){//Si esta false es porque esta disponible
                        items[i] = actual.getItems().get(i).nombre;//Lo mete al array
                    }
                }
                return items;
            }
        }
        return null;
    }
    public boolean InsertarItemEnGrid(Peticion peticion){

        return false;
    }
    public boolean ConsultarPuntoDisponible(Peticion peticion){
        //Busca al jugador
        Player jugadorBuscado = BuscarJugadorPorID((int) peticion.getDatosEntrada());
        //Castea el punto que preguntan si esta disponible
        Point puntoBuscado = (Point)peticion.getDatosSalida();
        //For para recorrer los items del jugador
        for (Item actual:jugadorBuscado.getItems()){
            //Pregunta si el item actual tiene ese punto en sus puntos ubicacion
            ArrayList<Point> puntosDeActual = actual.getPuntosUbicacion();
            for (Point punto:puntosDeActual){
                if (punto.equals(puntoBuscado)){
                    return false;
                }
            }
        }
        return true;
    }
    public Player BuscarJugadorPorID(int id){
        for (Player actual: players){
            if (actual.getID()==id)
                return actual;
        }
        return null;
    }
    public void SetPuntosItem(Peticion peticion){
        //Puntos en donde se va a agregar el item
        ArrayList<Point> puntos = (ArrayList<Point>) peticion.getDatosSalida();
        //Jugador quien va a agregar
        Player jugador = BuscarJugadorPorID( (int) peticion.getDatosEntrada());
        //Items del jugador
        ArrayList<Item> itemsJugador = jugador.getItems();
        //Nombre del item que se va a agregar
        String nombreItem = (String) peticion.getDatosExtra();

        //Busca en los items del jugador
        for (Item actual:itemsJugador){
            //Revisa si tiene el mismo nombre y si esta disponible
            if ((actual.getNombre().compareTo(nombreItem)==0 )&&(actual.getAgregadoAlGrid()==false)){
                actual.setPuntosUbicacion(puntos);//Agrega los puntos
                actual.setAgregadoAlGrid(true);//Cambia el estado a true porque ya se le agrego al grid o se va a hacer
                System.out.println("Inserto los puntos al item: "+actual.getNombre()+" "+actual.getPuntosUbicacion());
                jugador.setCambiosEnInventario(true);
            }
        }
    }

    public String ActualizarChat(){
        return paneles.get(0).getText();
    }
    public void SetCambiosEnInventarioJugador(Peticion peticion){
        Player jugador = BuscarJugadorPorID((int)peticion.getDatosEntrada());
        jugador.setCambiosEnInventario(false);
    }
    public boolean BuscarItemVivo(Peticion peticion){
        String nombreItem = (String) peticion.getDatosEntrada();//Nombre del item que se busca (especialmente el Mercado)
        Player jugador = BuscarJugadorPorID((int) peticion.getDatosSalida());//El mae que quiere comprar o algo asi
        ArrayList<Item> itemsJugador = jugador.getItems();//Items donde busca si tiene el que buscan
        for (Item actual:itemsJugador){
            if ((actual.isVivo())&&(actual.getNombre().compareTo(nombreItem)==0)&&(actual.getAgregadoAlGrid()==true)){
                return true;
            }
        }
        return false;
    }
    public void ComprarItem(Peticion peticion){
        Item nuevoItem = (Item) peticion.getDatosEntrada();
        Player jugador = BuscarJugadorPorID((int)peticion.getDatosSalida());
        jugador.agregarNuevoItem(nuevoItem);
        //Antes de llegar a este punto ya se valido que tenga toda la plata
        QuitarDineroAJugador(jugador.getID(),nuevoItem.getPrecio());
    }
    public boolean DineroSufiente(int idJugador,int cantidadAPreguntar){
        Player jugador = BuscarJugadorPorID(idJugador);
        if (jugador.getDinero()>=cantidadAPreguntar)
            return true;//Tiene mas o igual cantidad de plata de la que preguntan
        return false;
    }
    public void QuitarDineroAJugador(int idJugador,int cantidadQuitar){
        Player jugador = BuscarJugadorPorID(idJugador);
        int dineroActual = jugador.getDinero();
        jugador.setDinero(dineroActual-cantidadQuitar);
    }
    public void VenderAlMercado(Peticion peticion){
        //Se le rebaja el 10% del valor original del item para que tenga sentido
        //Dato entrada peti = ID jugador
        //Dato salida peti = item
        Player jugador = BuscarJugadorPorID((int)peticion.getDatosEntrada());
        String nombreItem = (String) peticion.getDatosSalida();

        ArrayList<Item> itemsJugador = jugador.getItems();

        for (int i =0;i<itemsJugador.size();i++){
            if ((itemsJugador.get(i).isVivo())&&(itemsJugador.get(i).getNombre().equals(nombreItem))){
                System.out.println("ENCONTRAMOS EL ITEM QUE SE VA A VENDER: "+itemsJugador.get(i).getNombre());
                int precioItem = itemsJugador.get(i).getPrecio();
                //SE LE REBAJA 50% AL VENDER
                int precioRebajado = (int) (precioItem-(precioItem * IConstantes.PORCENTAJE_REBAJA_VENDER_ITEM));
                System.out.println("PRECIO REBAJADO"+precioRebajado+" PRECIO ITEM: "+precioItem);
                jugador.setDinero(jugador.getDinero()+precioRebajado);
                itemsJugador.remove(i);//QUITA LOS ITEMS QUE ESTAN EN EL INVENTARIO  (NO CUENTA LOS DEL GRID)
                break;
            }
        }
    }
}
