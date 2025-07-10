package entity;

import dao.BigliettoDAO;
import dao.UtenteDAO;

import java.util.List;
import java.util.ArrayList;


public class ProfiloUtente {

    public enum Ruolo{
        AMMINISTRATORE,
        UTENTE,
    }


    private String nome;
    private String cognome;
    private String email;
    private String password;
    private String immagine;
    private List<Biglietto> biglietti;
    private Ruolo ruolo;


    private static BigliettoDAO bigliettoDAO = new BigliettoDAO();
    private static UtenteDAO utenteDAO = new UtenteDAO();

    //Costruttore
    public ProfiloUtente(String nome, String cognome, String email, String password, Ruolo ruolo) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.biglietti = new ArrayList<>();
        this.ruolo = ruolo;
        this.immagine = null;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getPassword() {
        return password;
    }

    public Ruolo getRuolo() {
        return ruolo;
    }

    public String getEmail() {
        return email;
    }

    public void aggiornaImmagine(String immagine){
        this.immagine = immagine;
        utenteDAO.aggiornaProfilo( this);
    }

    public String trovaImmagineProfilo(){
        if(this.immagine == null){
            this.immagine = utenteDAO.getImmagineProfilo(this.email);
        }
        return this.immagine;
    }

    public void aggiungiBiglietto(Biglietto b){
        biglietti.add(b);
    }

    public int calcolaNumEventiPartecipanti(){
        if(biglietti.isEmpty()){
            biglietti = bigliettoDAO.getStoricoBiglietti(this);
        }
        int numEventi = 0;
        for (Biglietto biglietto : biglietti) {
            if (biglietto.getStato() == Biglietto.Stato.CONSUMATO) {
                numEventi++;
            }
        }

        return numEventi;
    }

    public boolean checkPassword(String password){
        return this.password.equals(password);
    }


    public List<Biglietto> listaBigliettiAssociati(){
        //ottimizzazione
        if(biglietti.isEmpty()){
            biglietti = bigliettoDAO.getStoricoBiglietti(this);
        }
        return biglietti;
    }


    public boolean trovaBiglietto(Evento e){
        if(this.biglietti.isEmpty()){

            this.biglietti = bigliettoDAO.getStoricoBiglietti(this);

        }

        for(Biglietto biglietto : biglietti){
            System.out.println(biglietto.infoEvento().getId());
            System.out.println(e.getId());
            if(biglietto.infoEvento().getId().equals(e.getId())){
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || this.getClass() != o.getClass()) return false;
        ProfiloUtente profilo = (ProfiloUtente) o;
        return this.email.equals(profilo.email);
    }

    public String toString(){
        return "ProfiloUtente{" +
                "nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", ruolo=" + ruolo +
                '}';
    }
}
