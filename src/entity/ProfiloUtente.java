package entity;

import dao.BigliettoDAO;

import java.util.List;
import java.util.ArrayList;
import java.util.function.UnaryOperator;


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

    public void setImmagine(String immagine){
        this.immagine = immagine;
    }

    public String getImmagine(){
        return this.immagine;
    }

    public void aggiungiBiglietto(Biglietto b){
        biglietti.add(b);
    }

    public int calcolaNumEventiPartecipanti(){
        return biglietti.size();
    }

    public boolean checkPassword(String password){
        return this.password.equals(password);
    }


    public List<Biglietto> getBiglietti(){
        //ottimizzazione
        return bigliettoDAO.getStoricoBiglietti(this);
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
