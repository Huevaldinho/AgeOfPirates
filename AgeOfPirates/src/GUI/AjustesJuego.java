package GUI;

import Cliente.Client;
import General.Peticion;
import General.TipoAccion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AjustesJuego extends JFrame{
    public JComboBox cmbBoxItems;
    public JButton btnListo;
    public JPanel panel;
    public JLabel lbl_TextDinero;
    public JLabel lbl_turno;
    public int id;

    public AjustesJuego() {
        super("Ajustes del Juego");
        setContentPane(panel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        btnListo.addActionListener(new ActionListener() {//BOTON LISTO JUGADOR
            @Override
            public void actionPerformed(ActionEvent e) {
                Peticion peticion= new Peticion(TipoAccion.JUGADOR_LISTO,id);
                Client conexion = new Client(peticion);
            }
        });
    }
}
