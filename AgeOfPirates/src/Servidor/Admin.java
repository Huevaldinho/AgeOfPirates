package Servidor;

import Arma.Arma;
import Comodines.Escudo;
import Comodines.Kraken;
import GUI.Player;
import General.IConstantes;
import General.Intercambio;
import General.Peticion;
import ObjetosJuego.Conector;
import ObjetosJuego.Item;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Admin {
    private int cantidadPlayersActivos;
    private int cantidadMaximaPlayers;
    private ArrayList<JTextArea> paneles;
    private ArrayList<Player> players;
    private Point ultimoPuntoJugador1;
    private Point ultimoPuntoJugador2;
    private Point ultimoPuntoJugador3;
    private Point ultimoPuntoJugador4;
    //ultimo click conector
    private Point ultimoConector1;
    private Point ultimoConector2;
    private Point ultimoConector3;
    private Point ultimoConector4;


    //Jugador que acepta o declina la oferta
    private boolean yaRespondio;
    private Intercambio solicitudDeIntercambio1;
    private Intercambio solicitudDeIntercambio2;
    private Intercambio solicitudDeIntercambio3;
    private Intercambio solicitudDeIntercambio4;
    private  int numeroItemComprado =0;

    private boolean kraken1;
    private boolean kraken2;
    private boolean kraken3;
    private boolean kraken4;



    //Turnos
    private int turno;
    private Player jugadorTurno;

    public Admin(){
        yaRespondio=false;
        //turno=1;
        turno=0;
        solicitudDeIntercambio1=null;
        solicitudDeIntercambio2=null;
        solicitudDeIntercambio3=null;
        solicitudDeIntercambio4=null;
        kraken1=false;
        kraken2=false;
        kraken3=false;
        kraken4=false;

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
                //Arrastrar el previo como conector
                ultimoConector1=ultimoPuntoJugador1;
                ultimoPuntoJugador1=punto;
                System.out.println("Ultimo punto jugador 1: "+punto);
                break;
            }
            case 2:{
                ultimoConector2=ultimoPuntoJugador2;
                ultimoPuntoJugador2=punto;
                System.out.println("Ultimo punto jugador 2: "+punto);
                break;
            } case 3:{
                ultimoConector3=ultimoPuntoJugador3;
                ultimoPuntoJugador3=punto;
                System.out.println("Ultimo punto jugador 3: "+punto);
                break;
            }
            case 4:{
                ultimoConector4=ultimoPuntoJugador4;
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
    public void BorrarJugador(Peticion peticion){
        int idJugador = (int) peticion.getDatosEntrada();
        for (int i =0;i<players.size();i++){
            if (players.get(i).getID()==idJugador){
                players.remove(i);
                cantidadPlayersActivos--;
                break;
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
                actual.jugador = jugador.getID();
                break;
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
        nuevoItem.setID(numeroItemComprado);
        numeroItemComprado++;

        Player jugador = BuscarJugadorPorID((int)peticion.getDatosSalida());
        jugador.agregarNuevoItem(nuevoItem);
        //Antes de llegar a este punto ya se valido que tenga toda la plata
        QuitarDineroAJugador(jugador.getID(),nuevoItem.getPrecio());
    }
    public void comprarArmas(Peticion peticion){
        Arma arma = (Arma) peticion.getDatosEntrada();
        Player jugador = BuscarJugadorPorID((int)peticion.getDatosSalida());
        jugador.agregarArma(arma);
        System.out.println("\n\n\nARMA NUEVA JUGADOR: "+jugador.getArmas().size()+"\n\n\n");
        jugador.setAcero(jugador.getAcero()-arma.costo);
    }
    public boolean aceroSuficiente(int id , int costo){
        Player jugador = BuscarJugadorPorID(id);
        return jugador.getAcero()>=costo;
    }
    public void eliminarUltimoPunto(int id){
        switch (id){
            case 1: ultimoPuntoJugador1 = null;
            case 2: ultimoPuntoJugador2 = null;
            case 3: ultimoPuntoJugador3 = null;
            case 4: ultimoPuntoJugador4 = null;
        }
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
                int precioItem = itemsJugador.get(i).getPrecio();
                //SE LE REBAJA 50% AL VENDER
                int precioRebajado = (int) (precioItem-(precioItem * IConstantes.PORCENTAJE_REBAJA_VENDER_ITEM));
                jugador.setDinero(jugador.getDinero()+precioRebajado);
                itemsJugador.remove(i);//QUITA LOS ITEMS QUE ESTAN EN EL INVENTARIO  (NO CUENTA LOS DEL GRID)
                //EL COMBOBOX SE ACTUALIZA SOLO, TAMBIEN EL DINERO
                break;
            }
        }
    }
    public void InsertarUltimoPuntoConector(Peticion peticion){
        Point punto =(Point)peticion.getDatosEntrada();//Conector supuestamente seleccionado
        int jugador = (int)peticion.getDatosSalida();//ID jugador
        switch (jugador){
            case 1:{//Jugador 1
                ultimoConector1=punto;
                break;
            }
            case 2:{//Jugador 2
                ultimoConector2=punto;
                break;
            } case 3:{//Jugador 3
                ultimoConector3=punto;
                break;
            }
            case 4:{//Jugador 4
                ultimoConector4=punto;
                break;
            }
        }
    }
    public Point GetUltimoPuntoConector(Peticion peticion){
        int jugadorQuePide = (int) peticion.getDatosEntrada();
        switch (jugadorQuePide){
            case 1: return ultimoConector1;
            case 2: return ultimoConector2;
            case 3: return ultimoConector3;
            case 4: return ultimoConector4;
        }
        return null;
    }
    public Item ObtenerItemPorPunto(Peticion peticion){
        Point punto = (Point) peticion.getDatosEntrada();//Entrada punto
        Player jugador = BuscarJugadorPorID((int)peticion.getDatosSalida());//Salida id
        ArrayList<Item> itemsJugador = jugador.getItems();
        ArrayList<Point> puntosItem=null;
        for (Item actual:itemsJugador){
            puntosItem = actual.getPuntosUbicacion();
            for (Point puntoActual:puntosItem){
                if (puntoActual.equals(punto)){
                    return actual;
                }
            }
        }
        return null;
    }
    public Item BuscarItemPorID(int idItemBuscado, int idJugador,String nombre,Item conector){
        Player jugador = BuscarJugadorPorID(idJugador);
        ArrayList<Item> itemsJugador = jugador.getItems();
        for (Item actual:itemsJugador){
            if (actual.getNombre().equals("Mina")){
                if (actual.getNumero()==idItemBuscado+1 &&(idJugador==jugador.getID())){
                    return actual;
                }
            }
            if ((actual.getNumero()==idItemBuscado )&&(actual.getNombre().compareTo(nombre)==0)){
                System.out.println("Item buscado por ID: "+actual.getNumero()+" Nombre: "+actual.getNombre());
                if (conector != null) {
                    if (conector.equals(actual)){
                        System.out.println("IGUALES POR EQUALS "+conector+" - "+actual);
                        return actual;
                    }
                }
                return actual;
            }
        }
        return null;
    }
    public void AgregarItemAConector(Peticion peticion){

        Item itemAAgregar = (Item) peticion.getDatosEntrada();//Item a agregar
        itemAAgregar.setYaTieneConector(true);
        Item conector = (Item) peticion.getDatosSalida();//Conector seleccionado
        Player jugador = BuscarJugadorPorID((int)peticion.getDatosExtra());//Jugador
        conector.agregarItemConectado(itemAAgregar);
        Item buscado = BuscarItemPorID(conector.getNumero(),jugador.getID(),conector.getNombre(),conector);
        buscado.agregarItemConectado(itemAAgregar);
        ArrayList<Item> itemsConector = buscado.getItemsConectados();
        for (Item actual: itemsConector){
            System.out.println("ITEM ACTUAL EN CONECTOR:"+ actual.getNombre()+" NUMERO: "+actual.getNumero());
        }
    }
    public void SumarAceroAJugador(int jugadorID, int acero){
        Player jugador = BuscarJugadorPorID(jugadorID);
        jugador.setAcero(jugador.getAcero()+acero);
    }
    public void AgregarComodin(Peticion peticion){
        //Entrada es jugador
        //Salida es el comodin (kraken o escudo) sacar por instace of pa que no pete
        Player jugador = BuscarJugadorPorID((int)peticion.getDatosEntrada());
        if ((int)peticion.getDatosExtra()==1){
            jugador.setEscudo((Escudo) peticion.getDatosSalida());
        }else{
            jugador.setKraken((Kraken)peticion.getDatosSalida());

        }
    }
    public void IntercambioArma(Peticion peticion){
        //Jugador vendedor //Solo hace la peticion de intercambio
        Player jugadorVendedor = BuscarJugadorPorID((int)peticion.getDatosEntrada());
        //Jugador comprador
        Player jugadorComprador = BuscarJugadorPorID((int) peticion.getDatoComprador());
        //Precio de venta establecido
        int precioVenta = (int) peticion.getDatosSalida();
        //Arma a vender
        Arma armaAIntercambiar = (Arma) peticion.getDatosExtra();

        //Mandar solicitud al usuario y esperar respuesta
        crearSolicitudDeIntercambio(jugadorVendedor.getID(),jugadorComprador.getID(),precioVenta,armaAIntercambiar);

//        System.out.println("\n\n\n");
//        System.out.println("Jugador vendedor: "+jugadorVendedor.toString());
//        System.out.println("Jugador comprador: "+jugadorComprador.toString());
//        System.out.println("Arma a vender: "+armaAIntercambiar.toString());
//        System.out.println("Precio: "+precioVenta);
//        System.out.println("\n\n\n");
    }
    public void crearSolicitudDeIntercambio(int vendedor,int comprador,int precio, Arma arma){
        Intercambio intercambio = new Intercambio(vendedor,comprador,precio,arma);
        switch (comprador){
            case 1:{
                solicitudDeIntercambio1=intercambio;
                break;
            }
            case 2:{
                solicitudDeIntercambio2=intercambio;
                break;
            }
            case 3:{
                solicitudDeIntercambio3=intercambio;
                break;
            }
            case 4:{
                solicitudDeIntercambio4=intercambio;
                break;
            }
        }
    }
    public void IntercambioAcero(Peticion peticion){
        //Entrada ID jugador
        //Acero
        //Salida precio
        //Jugador
        Player jugadorVendedor = BuscarJugadorPorID((int)peticion.getDatosEntrada());
        Player jugadorComprador = BuscarJugadorPorID((int)peticion.getDatoComprador());
        int precioVenta = (int)peticion.getDatosSalida();
        int aceroAVender = (int)peticion.getDatosExtra();
        CrearSolicitudDeIntercambioAcero(jugadorVendedor.getID(),jugadorComprador.getID(),precioVenta,aceroAVender);
    }
    public void CrearSolicitudDeIntercambioAcero(int vendedor,int comprador,int precio,int acero){
        Intercambio intercambio = new Intercambio(vendedor,comprador,precio,acero);
        switch (comprador){
            case 1:{
                solicitudDeIntercambio1=intercambio;
                break;
            }
            case 2:{
                solicitudDeIntercambio2=intercambio;
                break;
            }
            case 3:{
                solicitudDeIntercambio3=intercambio;
                break;
            }
            case 4:{
                solicitudDeIntercambio4=intercambio;
                break;
            }
        }
    }
    public void respuestaOferta(boolean respuesta,Intercambio intercambio){
        if (respuesta){
            realizarIntercambio(intercambio);
        }else{
            switch (intercambio.getJugadorComprador()){
                case 1:{
                    solicitudDeIntercambio1=null;
                    break;
                }
                case 2:{
                    solicitudDeIntercambio2=null;
                    break;
                }
                case 3:{
                    solicitudDeIntercambio3=null;
                    break;
                }
                case 4:{
                    solicitudDeIntercambio4=null;
                    break;
                }
            }
        }
    }
    public void realizarIntercambio(Intercambio intercambio){
        Player jugadorComprador = BuscarJugadorPorID(intercambio.getJugadorComprador());
        Player jugadorVendedor = BuscarJugadorPorID(intercambio.getJugadorVendedor());
        if (intercambio.getNombreArma()!=null){//Para el intercambio de acero  no se puede realizar esto o peta
            jugadorComprador.agregarArma(intercambio.getNombreArma());
            jugadorVendedor.eliminarArma(intercambio.getNombreArma());
        }
        jugadorComprador.setDinero(jugadorComprador.getDinero()-intercambio.getPrecio());
        //Quita y pone el acero en caso que sea necesario
        jugadorVendedor.setAcero(jugadorVendedor.getAcero()-intercambio.getAcero());
        jugadorComprador.setAcero(jugadorComprador.getAcero()+intercambio.getAcero());

        System.out.println("Jugador vendedor despues de cambio: "+jugadorVendedor.toString());
        System.out.println("Jugador comprador despues de cambio: "+jugadorComprador.toString());
        switch (intercambio.getJugadorComprador()){
            case 1:{
                solicitudDeIntercambio1=null;
                break;
            }
            case 2:{
                solicitudDeIntercambio2=null;
                break;
            }
            case 3:{
                solicitudDeIntercambio3=null;
                break;
            }
            case 4:{
                solicitudDeIntercambio4=null;
                break;
            }
        }
    }
    public Intercambio getSolicitudDeIntercambio(int jugadorQuePreguntaSiTieneSolicidud){
        switch (jugadorQuePreguntaSiTieneSolicidud){
            case 1:{return solicitudDeIntercambio1;}
            case 2:{return solicitudDeIntercambio2;}
            case 3:{return solicitudDeIntercambio3;}
            case 4:{return solicitudDeIntercambio4;}
        }
        return null;
    }
    public boolean RevisarSiYaPerdio(Peticion peticion){
        Player jugador = BuscarJugadorPorID((int) peticion.getDatosEntrada());
        if (jugador.getItems().size()==0)
            return true;//Ya mamo
        return false;//Todavia juega
    }
    public boolean TodosListos(){
        for (Player actual:players){
            if (actual.isListo()==false){
                return false;
            }
        }
        return true;
    }
    public int TurnoDeJugador(){
        //Determinar de quien es el turno
//        for (int i =0;i<players.size();i++){
//            if (players.get(i).isJustAttacked()){
//                if (i==3){
//                    turno =players.get(0).getID();
//                    break;
//                }
//                turno = players.get(i+1).getID();
//                break;
//            }
//        }
//        return turno;
        int turnoDe=0;
        Player jugador = null;
        if (turno < players.size()){
            jugador = players.get(turno);
            turnoDe=jugador.getID();
            turno++;
        }else{
            turno=0;
            jugador = players.get(turno);
            turnoDe=jugador.getID();
            turno++;
        }

        jugador.setEstado(true);//Cuando ataca se cambia
        jugador.setJustAttacked(false);//Se pone true cuando ataca
        System.out.println("TURNO DE: "+turnoDe);
        return turnoDe;
    }
    public void CambiarEstadoAtaque(int idJugador){
        Player jugador = BuscarJugadorPorID(idJugador);
        jugador.setEstado(false);
        jugador.setJustAttacked(true);
    }
    public ArrayList<Item> GetConectores(Peticion peticion){
        Player jugador = BuscarJugadorPorID((int)peticion.getDatosEntrada());
        ArrayList<Item> itemsJugador = jugador.getItems();
        ArrayList<Item> arrayConectores = new ArrayList<>();
        for (Item actual:itemsJugador){
            if (actual.getNombre().equals("Conector")){
                System.out.println("ENCONTRE UN CONECTOR");
                arrayConectores.add(actual);
            }
        }
        return arrayConectores;
    }
    public boolean preguntarKrakenDisponible(int idJugador){
        Player jugador = BuscarJugadorPorID(idJugador);
        if (jugador.getKraken()!=null){
            return true;
        }
        return false;
    }
    public void respuestaKreken(int jugadorQuienRecibeAtaque, int quienAtaca){
        Player victima = BuscarJugadorPorID(jugadorQuienRecibeAtaque);
        Player atacante = BuscarJugadorPorID(quienAtaca);
        ataqueKraken(victima);//Ataca
        atacante.setKraken(null);//Se gasta el kraken
    }
    public void ataqueKraken(Player victima){
        Random random = new Random();
        int r = random.nextInt(victima.getItems().size());
        victima.getItems().remove(r);
    }
}
