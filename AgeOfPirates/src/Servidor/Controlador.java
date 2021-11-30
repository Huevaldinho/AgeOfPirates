package Servidor;

import Arma.Arma;
import General.Peticion;

import java.awt.*;

public class Controlador {
    private Admin admin;
    public Controlador() {
        admin = new Admin();
    }
    /**
     * Metodo para procesar el tipo de peticion que le entra al servidor,
     * Recibe una peticion, esta trae el tipo de peticion y
     * Los datos de entrada.
     * */
    public Peticion procesarPeticion(Peticion peticionRecibida) {
        switch (peticionRecibida.getAccion()){
            case SALUDAR: {
                //Le asigna el ID al jugador
                peticionRecibida.setDatosSalida("Hola playito!");
                break;
            }
            case REGISTRAR_PLAYER:{
                peticionRecibida.setDatosSalida(admin.RegistrarPlayer(peticionRecibida));
                break;
            }
            case GET_CANTIDAD_PLAYERS_CONECTADOS:{
                peticionRecibida.setDatosSalida(admin.GetCantidadPlayersActivos());
                break;
            }
            case ENVIAR_MENSAJE:{
                peticionRecibida.setDatosSalida(admin.EscribirEnCuadroTexto(peticionRecibida));
                break;
            }
            case AGREGAR_PANEl_CHAT:{
                admin.AgregarPanelArrayList(peticionRecibida);
                peticionRecibida.setDatosSalida(1);//No ocupa pero por aquello
                break;
            }
            case AGREGAR_ULTIMO_PUNTO:{
                admin.InsertarUltimoPunto(peticionRecibida);
                peticionRecibida.setDatosSalida(1);//No ocupa pero por aquello
                break;
            }
            case AGREGAR_JUGADOR:{
                admin.AgregarJugador(peticionRecibida);
                peticionRecibida.setDatosSalida(1);//No ocupa pero por aquello
                break;
            }
            case JUGADOR_LISTO:{
                admin.JugadorListo(peticionRecibida);
                peticionRecibida.setDatosSalida(1);//No ocupa pero por aquello
                break;
            }
            case ACTUALIZAR_DINERO:{
                peticionRecibida.setDatosSalida(admin.ActualizarDinero(peticionRecibida));
                break;
            }
            case ACTUALIZAR_ACERO:{
                peticionRecibida.setDatosSalida(admin.ActualizarAcero(peticionRecibida));
            }
            case ELIMINAR_JUGADOR_POR_ITEMS:{
                admin.EliminarJugadorPorItems();
                break;
            }
            case ELIMINAR_JUGADOR_POR_ID:{
                admin.EliminarJugadorPorID(peticionRecibida);
                break;
            }
            case SET_INVENTARIO_COMBOBOX:{
                peticionRecibida.setDatosSalida(admin.SetComboBoxInventario(peticionRecibida));
                break;
            }
            case INSERTAR_ITEM_EN_GRID:{
                peticionRecibida.setDatosSalida(admin.InsertarItemEnGrid(peticionRecibida));
                break;
            }
            case OBTENER_ULTIMO_PUNTO:{
                peticionRecibida.setDatosSalida(admin.GetUltimoPunto(peticionRecibida));
                break;
            }
            case CONSULTA_CELDA_DISPONIBLE:{
                //True es que esta disponible y false es que esta ocupada
                peticionRecibida.setDatosSalida(admin.ConsultarPuntoDisponible(peticionRecibida));
                break;
            }
            case SET_PUNTOS_ITEM:{
                admin.SetPuntosItem(peticionRecibida);
                peticionRecibida.setDatosSalida(true);
                break;
            }
            case ACTUALIZAR_CHAT:{
                peticionRecibida.setDatosSalida(admin.ActualizarChat());
                break;
            }
            case GET_JUGADOR_POR_ID:{
                peticionRecibida.setDatosSalida(admin.BuscarJugadorPorID((int)peticionRecibida.getDatosEntrada()));
                break;
            }
            case SET_CAMBIOS_JUGADOR_ITEMS:{
                admin.SetCambiosEnInventarioJugador(peticionRecibida);
                peticionRecibida.setDatosSalida(true);
                break;
            }
            case BUSCAR_ITEM_VIVO:{
                peticionRecibida.setDatosSalida(admin.BuscarItemVivo(peticionRecibida));
                break;
            }
            case REALIZAR_COMPRA_ITEM:{
                admin.ComprarItem(peticionRecibida);
                peticionRecibida.setDatosSalida(true);
                break;
            }
            case CONSULTAR_CANTIDAD_SUFICIENTE_DINERO:{
                peticionRecibida.setDatosSalida(admin.DineroSufiente((int)peticionRecibida.getDatosEntrada(),
                        (int)peticionRecibida.getDatosSalida()));
                break;
            }
            case VENDER_AL_MERCADO:{
                admin.VenderAlMercado(peticionRecibida);
                peticionRecibida.setDatosSalida(true);
                break;
            }
            case AGREGAR_ULTIMO_CONECTOR:{
                admin.InsertarUltimoPuntoConector(peticionRecibida);
                peticionRecibida.setDatosSalida(1);//No ocupa pero por aquello
                break;
            }
            case GET_ULTIMO_PUNTO_CONECTOR:{
                peticionRecibida.setDatosSalida(admin.GetUltimoPuntoConector(peticionRecibida));
                break;
            }
            case OBTENER_ITEM_POR_PUNTO:{
                peticionRecibida.setDatosSalida(admin.ObtenerItemPorPunto(peticionRecibida));
                break;
            }
            case AGREGAR_ITEM_A_CONECTOR:{
                admin.AgregarItemAConector(peticionRecibida);
                peticionRecibida.setDatosSalida(true);
                break;
            }
            case COMPRAR_ARMAS:{
                admin.comprarArmas(peticionRecibida);
                peticionRecibida.setDatosSalida(true);
                break;
            }
            case CONSULTAR_CANTIDAD_SUFICIENTE_ACERO:{
                peticionRecibida.setDatosSalida(admin.aceroSuficiente((int)peticionRecibida.getDatosEntrada(),(int)peticionRecibida.getDatosSalida()));
                break;
            }
            case ELIMINAR_ULTIMO_PUNTO:{
                admin.eliminarUltimoPunto((int)peticionRecibida.getDatosEntrada());
                peticionRecibida.setDatosSalida(null);
            }
        }
        return peticionRecibida;//Retorna la peticion con datos modificados
    }
}
