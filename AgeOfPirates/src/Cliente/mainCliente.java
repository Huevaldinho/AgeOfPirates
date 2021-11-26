package Cliente;

import General.Peticion;
import General.TipoAccion;
import Servidor.Server;

/**
 * @author Felipe Obando Y Sebastian Bermudez
 */
public class mainCliente {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("\n\tCliente");

        /*
        * Validar que haya al menos 2 players conectados
        *
        * */

        //PRUEBAS DE SERVIDOR Y TIPOS DE ACCION, asi se hacen las peticiones desde
        //la interfaz grafica.

        Peticion peticionSaluda = new Peticion(TipoAccion.SALUDAR, null);
        Client conexion = new Client(peticionSaluda);
        Object respuestaServidor =conexion.getRespuestaServer();
        System.out.println("Respuesta servidor saludar: "+respuestaServidor);

        Peticion peticionRegistrarPlayer = new Peticion(TipoAccion.REGISTRAR_PLAYER, null);
        Client conexion2 = new Client(peticionRegistrarPlayer);
        Object respuestaServidor2 =conexion2.getRespuestaServer();
        System.out.println("Respuesta servidor registrar player: "+respuestaServidor2);

        Peticion peticionGetCantidadDePlayers = new Peticion(TipoAccion.GET_CANTIDAD_PLAYERS_CONECTADOS, null);
        Client conexion3 = new Client(peticionGetCantidadDePlayers);
        Object respuestaServidor3 =conexion3.getRespuestaServer();
        System.out.println("Respuesta servidor get players: "+respuestaServidor3);

        /*
        * Usar GetPlayersConectados para validar si ya hay de 2-4 jugadores conectados
        * */

        Thread.sleep(10000);
    }
 }
