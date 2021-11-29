package GUI;

import Cliente.Client;
import General.IConstantes;
import General.Peticion;
import General.TipoAccion;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainWindow extends JFrame implements ActionListener {
    JFrame frame;
    JPanel mapaJugador;
    JPanel mensajes;
    JPanel mapaRival;
    private int jugadorID;
    private JTextArea chat;
    private final int DELAY = 3000;//3 segundo
    private Timer timer;

    MainWindow(){
        //ANTES QUE HAGA CUALQUIER COSA DEBE VERIFICAR QUE PUEDE JUGAR (Hay menos de 4 players)
        Peticion peticionParaVerSiContinua = new Peticion(TipoAccion.GET_CANTIDAD_PLAYERS_CONECTADOS,null);
        Client petiParaContinuar = new Client(peticionParaVerSiContinua);
        Object puedeSeguir = petiParaContinuar.getRespuestaServer();
        if ((int)puedeSeguir >= IConstantes.MAX_PLAYERS){
            JOptionPane.showMessageDialog(this,"Maxima cantidad  de usuarios alcanzada","Error de cantidad de usuarios",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        jugadorID=(int)puedeSeguir+1;
        timer = new Timer(DELAY,this);
        timer.start();
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
        //Etiqueta mensajes enviados
        JLabel etiquetaMensajes=new JLabel();
        etiquetaMensajes.setText("Mensajes enviados");
        etiquetaMensajes.setPreferredSize(new Dimension(160,100));
        mensajes.add(etiquetaMensajes);

        //Cuadro texto chat de todos
        JTextArea cuadroTextoChat = new JTextArea();
        cuadroTextoChat.setPreferredSize(new Dimension(300,200));
        cuadroTextoChat.setEditable(false);
        mensajes.add(cuadroTextoChat);
        chat=cuadroTextoChat;

        Peticion petiAgregarPanel = new Peticion(TipoAccion.AGREGAR_PANEl_CHAT,cuadroTextoChat);
        Client clienteAgregarPanel = new Client(petiAgregarPanel);

        JTextArea mensaje = new JTextArea();
        mensaje.setPreferredSize(new Dimension(150,75));
        mensajes.add(mensaje);

        //Boton enviar mensaje
        JButton btnEnviar = new JButton();
        btnEnviar.setPreferredSize(new Dimension(70,50));
        btnEnviar.setText("Send");
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

                Client cliente = new Client(peticioEnviarMensaje);//
                Object respuestaServer = cliente.getRespuestaServer();
                objetos = (ArrayList<JTextArea>) respuestaServer;//Castea el panel actualizado

                cuadroTextoChat.setText(objetos.get(0).getText()+'\n');//Actualiza el cuadro de texto
                mensaje.setText("");//Quita el mensaje del cuadro

            }
        });



        //Parte para ingresar los datos del juego
        JTextArea bitacora = new JTextArea();
        bitacora.setPreferredSize(new Dimension(300,200));
        bitacora.setText("Aqui van los datos del juego");
        bitacora.setEditable(false);
        mensajes.add(bitacora);


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

    @Override
    public void actionPerformed(ActionEvent e) {//ACTUALIZAR CHAT EN TIEMPO REAL
        //Hilo para actualizar el chat
        Peticion actualizarChat = new Peticion(TipoAccion.ACTUALIZAR_CHAT,null);
        Client conexion = new Client(actualizarChat);
        String texto = (String) conexion.getRespuestaServer();
        chat.setText(texto);

        //ACTUALIZAR LA BITACORA
    }
}