package boundary;

import controller.ControllerGestioneAutenticazione;
import controller.ControllerGestioneProfilo;
import dto.DTO;
import utilities.ProfileException;
import utilities.RegistrationException;

import javax.naming.AuthenticationException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.util.List;

public class GUILogin extends JFrame {
//    private static final ControllerGestioneAutenticazione controllerGestioneAutenticazione = new ControllerGestioneAutenticazione();
//    private static final ControllerGestioneProfilo controllerGestioneProfilo = new ControllerGestioneProfilo();
//    private static final ControllerGestioneCatalogo controllerGestioneCatalogo = new ControllerGestioneCatalogo();

    //private ProfiloUtente utenteLoggato;
    private JPanel mainPanel;
    private JPanel tabLogin;
    private JPanel tabSignIn;
    private JTextField campoMailAcc;
    private JPasswordField campoPwAcc;
    private JButton accediButton;
    private JLabel labelMailAcc;
    private JLabel labelPwAcc;
    private JTextField campoMailReg;
    private JPasswordField campoPwReg;
    private JButton registratiButton;
    private JLabel LabelMailReg;
    private JLabel labelPwReg;
    private JPanel panelReg;
    private JButton registraButton;
    private JTextField campoNomeReg;
    private JTextField campoCognomeReg;
    private JTextField campoImmagineReg;
    private JLabel labelNomeReg;
    private JLabel labelImmagineReg;
    private JLabel genericLabel;
    private JTabbedPane tabLogSign;
    private JLabel labelCognomeReg;
    private JTabbedPane tabProfilo;
    private JPanel panelDatiPersonali;
    private JPanel panelStoricoBiglietti;
    private JPanel tabVisualizzaImmagine;
    private JPanel tabCalcoloNumEventi;
    private JTextField nameField;
    private JTextField surnameField;
    private JTextField mailField;
    private JPasswordField passwordField1;
    private JButton esciButton;
    private JButton visualizzaImmagineButton;
    private JButton calcolaNumeroEventiPartecipatiButton;
    private JTextArea listaBiglietti;
    private JComboBox<DTO> comboBiglietti;
    private JButton scaricaButton;
    private JButton aggiungiCambiaImmagineButton;

    public GUILogin() {
        initMainPanel();
    }


    private void initMainPanel(){
        handleAccesso();
        handleRegistrazione();
    }

    private void handleAccesso(){
        accediButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = campoMailAcc.getText();
                char[] passwordChars = campoPwAcc.getPassword();
                String passwordHash;
                try {
                    passwordHash = hashPassword(passwordChars);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Errore di sicurezza nella codifica della password.");
                    return;
                }
                try {
                    ControllerGestioneAutenticazione.getInstance().login(email, passwordHash);
                    JOptionPane.showMessageDialog(null, "Login effettuato!");
                    tabLogSign.setVisible(false);
                    initTabProfilo();
//                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(mainPanel);
//                    frame.dispose();
                    //NON SI SPEGNE IL SISTEMA!
                }catch (AuthenticationException exA){
                    JOptionPane.showMessageDialog(null, exA.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void handleRegistrazione(){
        registratiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String eMail = campoMailReg.getText();
                char[] passwordChars = campoPwReg.getPassword();
                String passwordHash;
                try {
                    passwordHash = hashPassword(passwordChars);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Errore nella codifica della password.");
                    return;
                }
                try {
                    ControllerGestioneAutenticazione.getInstance().checkCredenzialiRegistrzione(eMail, passwordHash);
                    tabLogSign.setVisible(false);
                    handleCompletaRegistrazione(eMail, passwordHash);
                }catch (RegistrationException exR){
                    JOptionPane.showMessageDialog(null, exR.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
    }

    private void handleCompletaRegistrazione(String eMail, String password){
        panelReg.setVisible(true);
        registraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = campoNomeReg.getText();
                String cognome = campoCognomeReg.getText();
                String immagine = campoImmagineReg.getText();
                if(immagine.isBlank()){
                    immagine = null;
                }
                if(nome.isBlank() || cognome.isBlank()){
                    JOptionPane.showMessageDialog(null, "Inserire i campi nome e cognome!");
                    return;
                }
                ControllerGestioneAutenticazione.getInstance().RegistraUtente(nome,cognome,eMail,password,immagine);
                JOptionPane.showMessageDialog(null, "Registrazione effettuata!");
                panelReg.setVisible(false);
                initTabProfilo();
//                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(mainPanel);
//                frame.dispose();
            }
        });
    }

    private void initTabProfilo() {

        tabProfilo.setVisible(true);
        ControllerGestioneAutenticazione controllerGestioneAutenticazione = ControllerGestioneAutenticazione.getInstance();
        nameField.setText(controllerGestioneAutenticazione.getNomeUtente());
        surnameField.setText(controllerGestioneAutenticazione.getCognomeUtente());
        mailField.setText(controllerGestioneAutenticazione.getEmailUtente());
        passwordField1.setText("*********");
        handleEsci();
        tabVisualizzaCambiaImmagine();
        handleVisualizzaNumEventiPartecipati();
        mostraStoricoBiglietti();
    }

    private void tabVisualizzaCambiaImmagine(){
        visualizzaImmagineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    //String eMail = ControllerGestioneAutenticazione.getInstance().getEmailUtente();
                    JOptionPane.showMessageDialog(null, "Immagine profilo:\n" + ControllerGestioneProfilo.getInstance().trovaImmagineProfilo() , "Immagine profilo", JOptionPane.INFORMATION_MESSAGE);
                }catch (ProfileException exP){
                    JOptionPane.showMessageDialog(null, exP.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        aggiungiCambiaImmagineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String immagine = JOptionPane.showInputDialog(
                        null,
                        "Inserisci la nuova immagine come URL:",
                        "Aggiungi/Modifica immagine",
                        JOptionPane.QUESTION_MESSAGE
                );
                if (immagine != null && !immagine.isBlank()) {

                    JOptionPane.showMessageDialog(null, "Hai inserito: " + immagine);

                    ControllerGestioneProfilo.getInstance().setImmagineProfilo(immagine);
                    JOptionPane.showMessageDialog(null, "Immagine aggiornata!");

                } else {
                    JOptionPane.showMessageDialog(null, "Località non inserita o vuota.");
                }
            }
        });

    }





    private void handleVisualizzaNumEventiPartecipati(){
        calcolaNumeroEventiPartecipatiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int numEventi = ControllerGestioneProfilo.getInstance().calcolaNumEventiPartecipati();
                if(numEventi == 0){
                    JOptionPane.showMessageDialog(null, "Non hai partecipato a nessun evento!");

                }else {
                    JOptionPane.showMessageDialog(null, "Hai partecipato a " + numEventi + " eventi!");
                }
            }
        });
    }
    private void handleEsci(){
        esciButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Grazie per aver usato TicketOne!");
//              JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(mainPanel);
//              frame.dispose();
                System.exit(0);
            }
        });
    }


    private void mostraStoricoBiglietti(){
        List<DTO> biglietti = ControllerGestioneProfilo.getInstance().getStoricoBigliettiDTO();
        listaBiglietti.setText("");
        //listaBiglietti.append("Biglietti acquistati:\n");
        for (DTO biglietto : biglietti) {
            listaBiglietti.append(biglietto.toString() + "\n");
        }

        scaricaBiglietto(biglietti);
        //handleFiltraPerData();
    }













    private void scaricaBiglietto(List<DTO> biglietti){
        comboBiglietti.removeAllItems();
        for (DTO biglietto : biglietti) {
            comboBiglietti.addItem(biglietto);
        }
        scaricaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DTO biglietto = (DTO) comboBiglietti.getSelectedItem();
                assert biglietto != null;
                ControllerGestioneProfilo.getInstance().scaricaBiglietto(biglietto);
                JOptionPane.showMessageDialog(null, "Biglietto scaricato:\n" + biglietto.toString());
            }
        });
    }


    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            GUILogin guiLogin = new GUILogin();
            guiLogin.setTitle("TicketOne");//NOME DELL'APP ||ABBIAMO CREATO LA SCHERMATA
            guiLogin.setContentPane(new GUILogin().mainPanel); // new [nomeClasse.nomePanel] SERVE PER BINDARE LA SCHERMATA CON IL PANEL CREATO
            guiLogin.setDefaultCloseOperation(EXIT_ON_CLOSE);//METODO DI CHIUSURA DEL PANEL CHE CORRISPONDE ALLA CHIUSURA DEL PROGRAMMA
            //guiLogin.pack();//ALL'APERTURA DELLA SCERMATA SETTA LA GRANDEZZA DELL'INTERFACCIA IN MODO DA VEDERE TUTTE LE COSE PRESENTI
            guiLogin.setSize(848,480);
            guiLogin.setLocationRelativeTo(null); //MI METTE L'INTERFACCIA AL CENTRO DELLO SCHERMO
            guiLogin.setVisible(true);
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

        // Pulisci array password
        java.util.Arrays.fill(password, '0');

        return sb.toString();
    }


}
