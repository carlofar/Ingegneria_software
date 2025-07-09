package entity;

import dao.BigliettoDAO;
import dao.EventoDAO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Date;
import java.util.UUID;

public class Evento {

    private static final EventoDAO eventoDAO = new EventoDAO();
    private static final BigliettoDAO bigliettoDAO = new BigliettoDAO();

    private String id;
    private String titolo;
    private String descrizione;
    private LocalDate data;
    private String oraInizio;
    private String luogo;
    private int maxPartecipanti;
    private int numPartecipantiAttuali = 0;
    private float costo;
    private List<Biglietto> listaBiglietti;

    //Costruttore
    public Evento(){
        this.id = "EV-" + UUID.randomUUID().toString().substring(0,3) + "-" + LocalDateTime.now().hashCode();
        this.titolo = null;
        this.descrizione = null;
        this.data = null;
        this.oraInizio = null;
        this.luogo = null;
        this.maxPartecipanti = 0;
    }


    public Evento(String titolo, String descrizione, Date data, String oraInizio, String luogo, float costo, int maxPartecipanti) {
        this.id = "EV-" + UUID.randomUUID().toString().substring(0,3) + LocalDateTime.now().hashCode();
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.data = data;
        this.oraInizio = oraInizio;
        this.luogo = luogo;
        this.maxPartecipanti = maxPartecipanti;
        this.costo = costo;
    }

    public boolean verificaDisponibilita(){
        if (listaBiglietti == null){
            this.listaBiglietti = eventoDAO.getBigliettiAssociati(this);
        }
        return this.listaBiglietti.size() < maxPartecipanti;
    }

    public void aggiungiPartecipante(){

        this.numPartecipantiAttuali++;
    }


    public void aggiungiBiglietto(Biglietto b){
        listaBiglietti.add(b);
    }

    //getter & setter

    public String getId() {

        return id;
    }

    public void setId(String id){

        this.id = id;
    }


    public int getNumPartecipantiTotali(){

        return listaBiglietti.size();
    }

    public String getTitolo() {
        return titolo;
    }

    public LocalDate getData() {
        return data;
    }

    public String getOraInizio() {
        return oraInizio;
    }

    public String getLuogo() {
        return luogo;
    }

    public float getCosto() {
        return costo;
    }

    public int getMaxPartecipanti() {
        return maxPartecipanti;
    }

    public int getNumPartecipantiAttuali() {
        return numPartecipantiAttuali;
    }

    public List<Biglietto> getListaBiglietti() {
        if (listaBiglietti == null){
            return eventoDAO.getBigliettiAssociati(this);
        }
        return listaBiglietti;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setOraInizio(String oraInizio) {
        this.oraInizio = oraInizio;
    }

    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }

    public void setMaxPartecipanti(int maxPartecipanti) {
        this.maxPartecipanti = maxPartecipanti;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public void setNumPartecipantiAttuali(int numPartecipantiAttuali) {
        this.numPartecipantiAttuali = numPartecipantiAttuali;
    }


    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || this.getClass() != o.getClass()) return false;
        Evento evento = (Evento) o;
        System.out.println("this:" + this.toString() + "\nother: " + evento.toString());
        return this.titolo.equals(evento.titolo) &&
                this.data.equals(evento.data) &&
                this.luogo.equals(evento.luogo);
    }

    @Override
    public String toString() {
            //LocalDate data = ((java.sql.Date) this.getData()).toLocalDate();
            LocalDate data = this.getData();

        return  "Titolo: " + titolo +
                ",  Descrizione: " + descrizione +
                ",  Data: " + data.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                ", " + oraInizio +
                ",  Luogo: " + luogo +
                ",  Costo: " + costo + "\n";
    }


    public Biglietto trovaBiglietto(String codice) {
        Biglietto b =  bigliettoDAO.trovaBigliettoByCodice(codice);
        ProfiloUtente p = bigliettoDAO.getProprietario(b);
        b.setProprietario(p);
        return b;
    }

    public void aggiornaEntrataDAO() {
        eventoDAO.aggiornaEntrata(this);
    }
}
