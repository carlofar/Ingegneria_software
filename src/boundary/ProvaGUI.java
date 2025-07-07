
package boundary;

import controller.ControllerGestioneAcquisto;
import controller.ControllerGestioneAutenticazione;
import controller.ControllerGestioneCatalogo;
import entity.Evento;
import entity.ProfiloUtente;
import jdk.jfr.Event;
import utilities.PaymentException;
import utilities.TicketException;

import javax.naming.AuthenticationException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProvaGUI extends JFrame{

    private static final ControllerGestioneAutenticazione controllerGestioneAutenticazione = new ControllerGestioneAutenticazione();
    private static final ControllerGestioneAcquisto controllerGestioneAcquisto = new ControllerGestioneAcquisto();
    private static final ControllerGestioneCatalogo controllerGestioneCatalogo = new ControllerGestioneCatalogo();
    private ProfiloUtente utenteLoggato;

    private JPanel mainPanel;
    private JPanel loginPanel;
    private JPanel EventPanel;
    private JTextField campoEMail;
    private JPasswordField campoPw;
    private JButton loginButton;
    private JLabel labelEmail;
    private JLabel labelPw;
    private JComboBox<Evento> comboEventi;
    private JTextArea areaEventi;
    private JScrollPane scrollEventi;
    private JButton bottoneAcquista;

    public ProvaGUI() {
        handleLogin();

        initEventPanel();
        handleAcquista();

    }


    private void handleLogin(){
        assert loginButton != null;
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = campoEMail.getText();
                String password = new String(campoPw.getPassword());

                try {
                    utenteLoggato = controllerGestioneAutenticazione.login(email, password);
                    JOptionPane.showMessageDialog(null, "Login effettuato!");
                    loginPanel.setVisible(false);
                }catch (AuthenticationException authEx){
                    JOptionPane.showMessageDialog(null, authEx.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
                //JOptionPane.showMessageDialog(null, "Email: " + email + "\nPassword: " + password, "Login", JOptionPane.QUESTION_MESSAGE);
            }
        });
    }

    private void initEventPanel(){
        assert EventPanel != null;
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, "EventPanel");
        //((JFrame) SwingUtilities.getWindowAncestor(EventPanel)).setSize(800,600);
        //((JFrame) SwingUtilities.getWindowAncestor(EventPanel)).setLocationRelativeTo(null);
        EventPanel.setVisible(true);
        caricaEventi();
    }

    private void caricaEventi(){
        List<Evento> eventi = controllerGestioneCatalogo.getEventi();
        areaEventi.setText("");
        comboEventi.removeAllItems();
        for (Evento evento : eventi) {
            comboEventi.addItem(evento);
            areaEventi.append(evento.toString() + "\n");
        }

    }

    private void handleAcquista(){
        bottoneAcquista.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Evento evento = (Evento) comboEventi.getSelectedItem();
                assert evento != null;
                try{
                    controllerGestioneAcquisto.verificaDisponibilita(evento);
                    //int conferma = JOptionPane.showConfirmDialog(null, "Vuoi acquistare il biglietto per " + evento.getTitolo() + "?", "Conferma", JOptionPane.YES_NO_OPTION);
                } catch (TicketException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int conferma = JOptionPane.showConfirmDialog(null, "Vuoi acquistare il biglietto per " + evento.getTitolo() + "?", "Conferma", JOptionPane.YES_NO_OPTION);
                if(conferma != JOptionPane.YES_OPTION){
                    JOptionPane.showMessageDialog(null, "Acquisto annullato", "Informazione", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }else {
                    try {
                        controllerGestioneAcquisto.acquistaBiglietto(utenteLoggato,evento);
                        JOptionPane.showMessageDialog(null, "Acquisto effettuato!");
                    }catch (TicketException | PaymentException exT){
                        JOptionPane.showMessageDialog(null, exT.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                }


            }
        });
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            ProvaGUI GUI = new ProvaGUI();
            GUI.setTitle("TicketOne");//NOME DELL'APP ||ABBIAMO CREATO LA SCHERMATA
            GUI.setContentPane(new ProvaGUI().mainPanel); // new [nomeClasse.nomePanel] SERVE PER BINDARE LA SCHERMATA CON IL PANEL CREATO
            GUI.setDefaultCloseOperation(EXIT_ON_CLOSE);//METODO DI CHIUSURA DEL PANEL CHE CORRISPONDE ALLA CHIUSURA DEL PROGRAMMA
            GUI.pack();//ALL'APERTURA DELLA SCERMATA SETTA LA GRANDEZZA DELL'INTERFACCIA IN MODO DA VEDERE TUTTE LE COSE PRESENTI
            GUI.setSize(400,200);
            GUI.setLocationRelativeTo(null); //MI METTE L'INTERFACCIA AL CENTRO DELLO SCHERMO
            GUI.setVisible(true);
       });

    }


}