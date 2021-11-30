package GUI;

import Cliente.Client;
import General.Peticion;
import General.TipoAccion;
import ObjetosJuego.Armeria;
import ObjetosJuego.ArmeriaBomba;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ComprarArmas extends JFrame{
    private JLabel lbl_cuantasQuiere;
    private JLabel lbl_TextArmeria;
    private JSpinner spinner_cantidadArmas;
    private JButton btn_comprar;
    private JPanel panel;
    private Armeria armeria;
    private boolean termino;
    private int id;

    public ComprarArmas(Armeria armeriaLlega) {
        super("Comprar Armas");
        setContentPane(panel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        setTermino(false);

        armeria=armeriaLlega;
        try{
            try {
                Thread.sleep(1000);//esto es para esperar a que llegue el dato de atras por si acaso
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            lbl_TextArmeria.setText("Armería de "+getArmeria().getTipoArma().nombre+"(s)");

            btn_comprar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if ((int)spinner_cantidadArmas.getValue()==0) {
                        setVisible(false);
                        setTermino(true);
                    }
                    else {
                        Peticion peticionPuedeAcero = new Peticion(TipoAccion.CONSULTAR_CANTIDAD_SUFICIENTE_ACERO, id);
                        peticionPuedeAcero.setDatosSalida(getArmeria().getTipoArma().costo * (int) spinner_cantidadArmas.getValue());
                        Client conexion = new Client(peticionPuedeAcero);
                        if ((boolean) conexion.getRespuestaServer()) {
                            comprarItems();
                            JOptionPane.showMessageDialog(null, "Compra realizada.");
                            setTermino(true);
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "No tiene recursos suficientes para adquirir esa cantidad de " + getArmeria().getTipoArma().nombre + "(s).");
                            setTermino(true);
                        }
                    }
                    setVisible(false);
                }
            });

        }catch(NullPointerException e){
            System.out.println("NULL la armería pinga");
        }

    }

    public void comprarItems(){
        for (int i = 0 ; i<(int)spinner_cantidadArmas.getValue();i++) {
            Peticion peticionInsertarCompraNueva = new Peticion(TipoAccion.COMPRAR_ARMAS, getArmeria().getTipoArma());
            peticionInsertarCompraNueva.setDatosSalida(id);
            Client conexionCompra = new Client(peticionInsertarCompraNueva);
        }
    }

    public Armeria getArmeria() {
        return armeria;
    }
    public void setArmeria(Armeria armeria) {
        this.armeria = armeria;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public boolean isTermino() {
        return termino;
    }

    public void setTermino(boolean termino) {
        this.termino = termino;
    }
}
