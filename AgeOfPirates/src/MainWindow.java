import Cliente.Client;
import General.IConstantes;
import General.Peticion;
import General.TipoAccion;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class MainWindow extends JFrame {
    JFrame frame;
    JPanel mapaJugador;
    JPanel mensajes;
    JPanel mapaRival;

    MainWindow(){
        //ANTES QUE HAGA CUALQUIER COSA DEBE VERIFICAR QUE PUEDE JUGAR (Hay menos de 4 players)
        Peticion peticionParaVerSiContinua = new Peticion(TipoAccion.GET_CANTIDAD_PLAYERS_CONECTADOS,null);
        Client petiParaContinuar = new Client(peticionParaVerSiContinua);
        Object puedeSeguir = petiParaContinuar.getRespuestaServer();
        if ((int)puedeSeguir >= IConstantes.MAX_PLAYERS){
            System.out.println("\nYA ESTAN TODOS  LOS JUGADORES CONECTADOS, ASI QUE NO PUEDE JUGAR");
            JOptionPane.showMessageDialog(this,"Maxima cantidad  de usuarios alcanzada","Error de cantidad de usuarios",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }else{
            System.out.println("\nPuede jugar, actualmente hay: "+(int)puedeSeguir+" jugadores conectados");
        }
        createUIComponents();
    }
    /**
     * Se crean los widgets aquí debajo
     */
    private void createUIComponents() {
        // TODO: place custom component creation code here
        frame = new JFrame("Age of Pirates");
        JTabbedPane tabbedPane = new JTabbedPane();

        //Mi mapa
        Dimension dimension = new Dimension(800,800);
        mapaJugador = new Grid();
        mapaJugador.setBackground(Color.CYAN);
        mapaJugador.setEnabled(true);
        mapaJugador.setPreferredSize(dimension);
        mapaJugador.setMinimumSize(dimension);
        mapaJugador.setMaximumSize(dimension);
        //Chat
        mensajes = new JPanel();
        mensajes.setBackground(Color.gray);
        mensajes.setEnabled(true);
        mensajes.setPreferredSize(dimension);
        mensajes.setMinimumSize(dimension);
        mensajes.setMaximumSize(dimension);
        //Mapa enemigos
        mapaRival = new JPanel();
        mapaRival.setBackground(Color.RED);
        mapaRival.setEnabled(true);
        mapaRival.setPreferredSize(dimension);
        mapaRival.setMinimumSize(dimension);
        mapaRival.setMaximumSize(dimension);

        tabbedPane.setBounds(0,0,800,800);
        tabbedPane.add("Mapa",mapaJugador);
        tabbedPane.add("Mensajes",mensajes);
        tabbedPane.add("Mapa Rival",mapaRival);

        frame.add(tabbedPane);
        frame.setSize(new Dimension(800,850));
        frame.setBackground(Color.BLUE);
        frame.setLayout(null);
        frame.setVisible(true);


        //REGISTRA EL JUGADOR 1
        Peticion peticionRegistrarJugador = new Peticion(TipoAccion.REGISTRAR_PLAYER,null);
        Client conexionRegistrarJugador = new Client(peticionRegistrarJugador);
        Object respuestaRegistrarJugador = conexionRegistrarJugador.getRespuestaServer();
        System.out.println("\nRespuesta servidor registrar player: "+respuestaRegistrarJugador);
        //ESTE ES EL ID QUE SE LE VA A ASIGNAR AL PLAYER
        Player jugador = new Player();
        jugador.setID((int)respuestaRegistrarJugador);
        System.out.println("Jugador tiene ID:"+jugador.getID());

    }
}