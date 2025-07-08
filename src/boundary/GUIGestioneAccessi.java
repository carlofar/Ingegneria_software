package boundary;

import controller.ControllerGestioneAccessi;
import controller.ControllerGestioneAutenticazione;
import controller.ControllerGestioneCatalogo;
import entity.Evento;
import entity.ProfiloUtente;

import javax.naming.AuthenticationException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.AccessDeniedException;
import java.util.List;

public class GUIGestioneAccessi extends JFrame{
    ControllerGestioneCatalogo controllerGestioneCatalogo = new ControllerGestioneCatalogo();
    ControllerGestioneAccessi controllerGestioneAccessi = new ControllerGestioneAccessi();
    ControllerGestioneAutenticazione controllerGestioneAutenticazione = new ControllerGestioneAutenticazione();

    private ProfiloUtente utenteLoggato;
    private JPanel mainPanel;
    private JPanel panelEventi;
    private JTextArea areaEventi;
    private JComboBox comboEventi;
    private JButton verificaBigliettoButton;
    private JScrollPane scrollBar;
    private JPanel loginPanel;
    private JTextField campoEMail;
    private JPasswordField campoPw;
    private JButton accediButton;
    private JLabel fieldEMail;
    private JLabel fieldPw;


    public GUIGestioneAccessi(){

        initLogin();

        //initEventi();

    }


    private void initLogin(){
        loginPanel.setVisible(true);
        panelEventi.setVisible(false);
        accediButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = campoEMail.getText();
                String password = new String(campoPw.getPassword());
                try {
                    utenteLoggato = controllerGestioneAutenticazione.login(email, password);
                    JOptionPane.showMessageDialog(null, "Login effettuato!");
                    loginPanel.setVisible(false);
                    initEventi();
//                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(mainPanel);
//                    frame.dispose();
                    //NON SI SPEGNE IL SISTEMA!
                }catch (AuthenticationException exA){
                    JOptionPane.showMessageDialog(null, exA.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }


    private void initEventi(){
        panelEventi.setVisible(true);
        List<Evento> eventi = controllerGestioneCatalogo.getEventiOdierni();
        areaEventi.setText("");
        for (Evento evento : eventi) {
            areaEventi.append(evento.toString() + "\n");
        }

        assert comboEventi != null;
        comboEventi.removeAllItems();
        for (Evento evento : eventi) {
            comboEventi.addItem(evento);
        }

        verificaBiglietto();

    }


    private void verificaBiglietto(){
        verificaBigliettoButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Evento e = (Evento) comboEventi.getSelectedItem();
                String codiceInserito = JOptionPane.showInputDialog(null, "Inserisci il codice biglietto da verificare","Biglietto da verificare",JOptionPane.QUESTION_MESSAGE);
                if(codiceInserito != null) {
                    try {
                        assert e != null;
                        controllerGestioneAccessi.effettuaAccesso(codiceInserito, e, utenteLoggato);
                        JOptionPane.showMessageDialog(null,"Accesso effettuato con successo!", "Informazione", JOptionPane.INFORMATION_MESSAGE);
                    }catch (AccessDeniedException exA){
                        JOptionPane.showMessageDialog(null, exA.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Inserire un codice biglietto valido!", "Errore", JOptionPane.ERROR_MESSAGE);
                    verificaBigliettoButton.requestFocus();
                    verificaBigliettoButton.requestFocusInWindow();
                    verificaBigliettoButton.doClick();
                }
            }
        });
    }











    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            GUIGestioneAccessi guiGestioneAccessi = new GUIGestioneAccessi();
            guiGestioneAccessi.setTitle("TicketOne");//NOME DELL'APP ||ABBIAMO CREATO LA SCHERMATA
            guiGestioneAccessi.setContentPane(new GUIGestioneAccessi().mainPanel); // new [nomeClasse.nomePanel] SERVE PER BINDARE LA SCHERMATA CON IL PANEL CREATO
            guiGestioneAccessi.setDefaultCloseOperation(EXIT_ON_CLOSE);//METODO DI CHIUSURA DEL PANEL CHE CORRISPONDE ALLA CHIUSURA DEL PROGRAMMA
            //guiLogin.pack();//ALL'APERTURA DELLA SCERMATA SETTA LA GRANDEZZA DELL'INTERFACCIA IN MODO DA VEDERE TUTTE LE COSE PRESENTI
            guiGestioneAccessi.setSize(1280,720);
            guiGestioneAccessi.setLocationRelativeTo(null); //MI METTE L'INTERFACCIA AL CENTRO DELLO SCHERMO
            guiGestioneAccessi.setVisible(true);
        });
    }



}
