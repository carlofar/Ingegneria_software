package boundary;

import controller.ControllerGestioneAcquisto;
import controller.ControllerGestioneAutenticazione;
import controller.ControllerGestioneCatalogo;
import entity.Evento;
import entity.ProfiloUtente;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class ProvaGUI {

    private final ControllerGestioneAcquisto acquisti = new ControllerGestioneAcquisto();
    private final ControllerGestioneCatalogo catalogo = new ControllerGestioneCatalogo();
    private final ControllerGestioneAutenticazione autenticazione = new ControllerGestioneAutenticazione();



    private JPanel Login;
    private JTextField eMail;
    private JPasswordField password;
    private JButton button1;

    public ProvaGUI() {

        setTitle("Acquisto Biglietti");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);

        initLoginPanel();
    }
}
