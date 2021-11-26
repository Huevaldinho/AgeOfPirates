import Cliente.Client;
import General.IConstantes;
import General.Peticion;
import General.TipoAccion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainWindow extends JFrame {
    JFrame frame;
    JPanel mapaJugador;
    JPanel mensajes;
    JPanel mapaRival;
    private int jugadorID;

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
        jugadorID=(int)puedeSeguir+1;
        createUIComponents();
    }
    /**
     * Se crean los widgets aquí debajo
     */
    private void createUIComponents() {
        // TODO: place custom component creation code here
        frame = new JFrame("Age of Pirates"+"- Jugador: "+jugadorID);
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
        //Cuadro texto chat de todos
        JTextArea cuadroTextoChat = new JTextArea();
        cuadroTextoChat.setPreferredSize(new Dimension(300,200));
        mensajes.add(cuadroTextoChat);

        Peticion petiAgregarPanel = new Peticion(TipoAccion.AGREGAR_PANEl_CHAT,cuadroTextoChat);
        Client clienteAgregarPanel = new Client(petiAgregarPanel);
        System.out.println("Respuesta: "+clienteAgregarPanel.getRespuestaServer());


        //Mensaje a enviar
        JTextArea mensaje = new JTextArea();
        mensaje.setPreferredSize(new Dimension(500,200));
        mensajes.add(mensaje);

        //Boton enviar mensaje
        JButton btnEnviar = new JButton();
        btnEnviar.setPreferredSize(new Dimension(50,50));
        btnEnviar.setText("Enviar Mensaje");
        mensajes.add(btnEnviar);
        btnEnviar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Peticion peticionParaVerSiContinua = new Peticion(TipoAccion.GET_CANTIDAD_PLAYERS_CONECTADOS,null);
                Client petiParaContinuar = new Client(peticionParaVerSiContinua);
                Object puedeSeguir = petiParaContinuar.getRespuestaServer();

                ArrayList<JTextArea> objetos = new ArrayList<>();

                objetos.add(cuadroTextoChat);//Chat
                objetos.add(mensaje);//Mensaje

                Peticion peticioEnviarMensaje = new Peticion(TipoAccion.ENVIAR_MENSAJE,objetos);
                peticioEnviarMensaje.setDatosSalida(jugadorID);

                Client cliente = new Client(peticioEnviarMensaje);
                Object respuestaServer = cliente.getRespuestaServer();
                objetos = (ArrayList<JTextArea>) respuestaServer;

                cuadroTextoChat.setText(objetos.get(0).getText()+'\n');

            }
        });


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

    }
}