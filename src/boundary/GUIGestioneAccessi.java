package boundary;


import controller.ControllerGestioneAccessi;
import controller.ControllerGestioneAutenticazione;
import controller.ControllerGestioneCatalogo;
import dto.DTO;

import javax.naming.AuthenticationException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.AccessDeniedException;
import java.security.MessageDigest;
import java.util.List;

public class GUIGestioneAccessi extends JFrame{
//    ControllerGestioneCatalogo controllerGestioneCatalogo = new ControllerGestioneCatalogo();
//    ControllerGestioneAccessi controllerGestioneAccessi = new ControllerGestioneAccessi();
//    ControllerGestioneAutenticazione controllerGestioneAutenticazione = new ControllerGestioneAutenticazione();

    //private ProfiloUtente utenteLoggato;
    private String mailUtente;
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
        initMainPanel();
    }


    private void initMainPanel(){
        assert loginPanel != null;
        loginPanel.setVisible(true);
        assert panelEventi != null;
        panelEventi.setVisible(false);
        assert accediButton != null;
        accediButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = campoEMail.getText();
                char[] passwordChars = campoPw.getPassword();
                String passwordHash;
                try {
                    passwordHash = hashPassword(passwordChars);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Errore nella codifica della password.");
                    return;
                }
                try {
                    ControllerGestioneAutenticazione.getInstance().login(email, passwordHash);
                    JOptionPane.showMessageDialog(null, "Login effettuato!");
                    mailUtente = email;
                    loginPanel.setVisible(false);
                    initPanelEventi();
//                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(mainPanel);
//                    frame.dispose();
                    //NON SI SPEGNE IL SISTEMA!
                }catch (AuthenticationException exA){
                    JOptionPane.showMessageDialog(null, exA.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }


    private void initPanelEventi(){
        panelEventi.setVisible(true);
        List<DTO> eventi = ControllerGestioneCatalogo.getInstance().getEventiOdierniDTO();
        areaEventi.setText("");
        for (DTO evento : eventi) {
            areaEventi.append(evento.toString() + "\n");
        }

        assert comboEventi != null;
        comboEventi.removeAllItems();
        for (DTO evento : eventi) //noinspection unchecked
            comboEventi.addItem(evento);

        verificaBiglietto();

    }


    private void verificaBiglietto(){
        verificaBigliettoButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DTO evento = (DTO) comboEventi.getSelectedItem();
                String codiceInserito = JOptionPane.showInputDialog(null, "Inserisci il codice biglietto da verificare","Biglietto da verificare",JOptionPane.QUESTION_MESSAGE);
                if(codiceInserito != null) {
                    try {
                        assert evento != null;
                        ControllerGestioneAccessi.getInstance().effettuaAccesso(codiceInserito, evento, mailUtente);
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

    private String hashPassword(char[] password) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] passwordBytes = new String(password).getBytes("UTF-8");
        byte[] hashBytes = md.digest(passwordBytes);

        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }

        // Pulisce la password dalla memoria
        java.util.Arrays.fill(password, '0');

        return sb.toString();
    }

}
