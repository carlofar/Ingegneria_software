package entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Date;

public class Evento {

    private static int idCounter = 0;
    private int id;
    private String titolo;
    private String descrizione;
    private Date data;
    private String oraInizio;
    private String luogo;
    private int maxPartecipanti;
    private int numPartecipantiAttuali = 0;
    private float costo;
    private List<Biglietto> listaBiglietti;

    //Costruttore
    public Evento(){
        Evento.idCounter ++;
        this.id = Evento.idCounter;
        this.titolo = null;
        this.descrizione = null;
        this.data = null;
        this.oraInizio = null;
        this.luogo = null;
        this.maxPartecipanti = 0;
    }


    public Evento(String titolo, String descrizione, Date data, String oraInizio, String luogo, float costo, int maxPartecipanti) {
        Evento.idCounter ++;
        this.id = Evento.idCounter;
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.data = data;
        this.oraInizio = oraInizio;
        this.luogo = luogo;
        this.maxPartecipanti = maxPartecipanti;
        this.costo = costo;
    }

    public boolean verificaDisponibilita(){
        return this.listaBiglietti.size() < maxPartecipanti;
    }

    public void aggiungiPartecipante(){
        this.numPartecipantiAttuali++;
    }



    //getter & setter

    public int getId() {
        return id;
    }

    public int getNumPartecipantiTotali(){
        return listaBiglietti.size();
    }

    public String getTitolo() {
        return titolo;
    }

    public Date getData() {
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

    public void setData(Date data) {
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

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || this.getClass() != o.getClass()) return false;
        Evento evento = (Evento) o;
        return this.titolo.equals(evento.titolo) &&
                this.data.equals(evento.data) &&
                this.luogo.equals(evento.luogo);
    }

    @Override
    public String toString() {
        return "Evento{" +
                "titolo='" + titolo + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", data=" + data +
                ", oraInizio=" + oraInizio +
                ", luogo='" + luogo + '\'' +
                ", costo=" + costo +
                '}';
    }



}
