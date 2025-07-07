package boundary;

import controller.ControllerGestioneAutenticazione;
import controller.ControllerGestioneProfilo;
import entity.ProfiloUtente;
import utilities.ProfileException;
import utilities.RegistrationException;

import javax.naming.AuthenticationException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUILogin extends JFrame {
    private static final ControllerGestioneAutenticazione controllerGestioneAutenticazione = new ControllerGestioneAutenticazione();
    private static final ControllerGestioneProfilo controllerGestioneProfilo = new ControllerGestioneProfilo();

    private ProfiloUtente utenteLoggato;
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

    public GUILogin() {

        handleLogin();


    }


    private void handleLogin(){
        handleAccesso();
        handleRegistrazione();
    }

    private void handleAccesso(){
        accediButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = campoMailAcc.getText();
                String password = new String(campoPwAcc.getPassword());
                try {
                    utenteLoggato = controllerGestioneAutenticazione.login(email, password);
                    JOptionPane.showMessageDialog(null, "Login effettuato!");
                    tabLogSign.setVisible(false);
                    initProfilo(utenteLoggato);
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
                String password = new String(campoPwReg.getPassword());
                try {
                    controllerGestioneAutenticazione.checkCredenzialiRegistrzione(eMail, password);
                    tabLogSign.setVisible(false);
                    handleCompletaRegistrazine(eMail, password);
                }catch (RegistrationException exR){
                    JOptionPane.showMessageDialog(null, exR.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
    }

    private void handleCompletaRegistrazine(String eMail, String password){
        panelReg.setVisible(true);
        registraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = campoNomeReg.getText();
                String cognome = campoCognomeReg.getText();
                String immagine = campoImmagineReg.getText();
                utenteLoggato = controllerGestioneAutenticazione.RegistraUtente(nome,cognome,eMail,password,immagine);
                JOptionPane.showMessageDialog(null, "Registrazione effettuata!");
                panelReg.setVisible(false);
                initProfilo(utenteLoggato);
//                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(mainPanel);
//                frame.dispose();
            }
        });
    }

    private void initProfilo(ProfiloUtente p) {

        tabProfilo.setVisible(true);
        assert p != null;
        nameField.setText(p.getNome());
        surnameField.setText(p.getCognome());
        mailField.setText(p.getEmail());
        passwordField1.setText(p.getPassword());
        handleEsci();
        handleVisualizzaImmagine(p);
    }

    private void handleVisualizzaImmagine(ProfiloUtente p){
        visualizzaImmagineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{

                    JOptionPane.showMessageDialog(null, "Immagine profilo:\n" + controllerGestioneProfilo.getImmagineProfilo(p) , "Immagine profilo", JOptionPane.INFORMATION_MESSAGE);
                }catch (ProfileException exP){
                    JOptionPane.showMessageDialog(null, exP.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void handleEsci(){
        esciButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Grazie per aver usato TicketOne!");
                //                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(mainPanel);
//                frame.dispose();
                System.exit(0);
            }
        });
    }
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            GUILogin guiLogin = new GUILogin();
            guiLogin.setTitle("TicketOne");//NOME DELL'APP ||ABBIAMO CREATO LA SCHERMATA
            guiLogin.setContentPane(new GUILogin().mainPanel); // new [nomeClasse.nomePanel] SERVE PER BINDARE LA SCHERMATA CON IL PANEL CREATO
            guiLogin.setDefaultCloseOperation(EXIT_ON_CLOSE);//METODO DI CHIUSURA DEL PANEL CHE CORRISPONDE ALLA CHIUSURA DEL PROGRAMMA
            guiLogin.pack();//ALL'APERTURA DELLA SCERMATA SETTA LA GRANDEZZA DELL'INTERFACCIA IN MODO DA VEDERE TUTTE LE COSE PRESENTI
            guiLogin.setSize(400,200);
            guiLogin.setLocationRelativeTo(null); //MI METTE L'INTERFACCIA AL CENTRO DELLO SCHERMO
            guiLogin.setVisible(true);
        });

    }





}
