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


public class GuiProva extends JFrame {

    private final ControllerGestioneAcquisto acquisti = new ControllerGestioneAcquisto();
    private final ControllerGestioneCatalogo catalogo = new ControllerGestioneCatalogo();
    private final ControllerGestioneAutenticazione autenticazione = new ControllerGestioneAutenticazione();

    private ProfiloUtente utenteLoggato;

    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextArea areaEventi;
    private JComboBox<String> comboEventi;
    private JButton acquistaButton;

    public GuiProva() {

        setTitle("Acquisto Biglietti");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);

        initLoginPanel();
    }

    private void initLoginPanel() {
        JPanel loginPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        loginPanel.setBorder(BorderFactory.createTitledBorder("Login"));

        emailField = new JTextField();
        passwordField = new JPasswordField();

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(this::handleLogin);

        loginPanel.add(new JLabel("Email:"));
        loginPanel.add(emailField);
        loginPanel.add(new JLabel("Password:"));
        loginPanel.add(passwordField);
        loginPanel.add(new JLabel());
        loginPanel.add(loginButton);

        setContentPane(loginPanel);
    }

    private void initEventPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        areaEventi = new JTextArea(10, 40);
        areaEventi.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaEventi);

        comboEventi = new JComboBox<>();
        acquistaButton = new JButton("Acquista");
        acquistaButton.addActionListener(this::handleAcquisto);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(new JLabel("Seleziona ID Evento:"));
        bottomPanel.add(comboEventi);
        bottomPanel.add(acquistaButton);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        loadEventi();
        setContentPane(panel);
        revalidate();
        repaint();
    }

    private void loadEventi() {
        List<Evento> eventi = catalogo.getEventi();
        areaEventi.setText("");

        for (Evento e : eventi) {
            areaEventi.append(e.toString() + "\n");
            comboEventi.addItem(e.getId());
        }
    }

    private void handleLogin(ActionEvent e) {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        utenteLoggato = autenticazione.login(email, password);

        if (utenteLoggato != null) {
            JOptionPane.showMessageDialog(this, "Login effettuato!");
            initEventPanel();
        } else {
            JOptionPane.showMessageDialog(this, "Credenziali non valide!", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleAcquisto(ActionEvent e) {
        String eventoId = (String) comboEventi.getSelectedItem();
        Evento evento = catalogo.getEvento(eventoId); // serve getEvento(evento e)

        if (evento == null) {
            JOptionPane.showMessageDialog(this, "Evento non trovato.");
            return;
        }

        boolean disponibile = acquisti.verificaDisponibilita(evento);
        if (!disponibile) {
            JOptionPane.showMessageDialog(this, "Biglietti esauriti!", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int conferma = JOptionPane.showConfirmDialog(this, "Vuoi acquistare il biglietto per " + evento.getTitolo() + "?", "Conferma", JOptionPane.YES_NO_OPTION);
        if (conferma == JOptionPane.YES_OPTION) {
            try {
                acquisti.acquistaBiglietto(utenteLoggato, evento);
                JOptionPane.showMessageDialog(this, "Acquisto effettuato!");
            }catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            }


        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GuiProva gui = new GuiProva();
            gui.setVisible(true);
        });
    }
}

