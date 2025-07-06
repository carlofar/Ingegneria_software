package entity;
import dao.BigliettoDAO;
import dao.EventoDAO;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Objects;


public class  Biglietto {

    private static final BigliettoDAO bigliettoDAO = new BigliettoDAO();
    private static final EventoDAO eventoDAO = new EventoDAO();
    public void salvaBigliettoDAO() {
        bigliettoDAO.salvaBiglietto(this);
    }

    public enum Stato{
        VALIDO,
        CONSUMATO,
    }

    private String codice;
    private Stato stato;
    private ProfiloUtente proprietario;
    private Evento evento;

    //Constructor
    public Biglietto(){
        this.codice = null;
        this.stato = Stato.VALIDO;
        this.proprietario = null;
        this.evento = null;
    }


    public Biglietto(String codice,ProfiloUtente proprietario,Evento evento){
        this.codice=codice;
        this.proprietario=proprietario;
        this.evento=evento;
        this.stato=Stato.VALIDO;
    }


    //getter and setter

    public String getCodice() {
        return codice;
    }

    public Stato getStato() {
        return stato;
    }

    public ProfiloUtente getProprietario() {
        return proprietario;
    }

    public Evento getEvento() {
        if(evento == null){
            bigliettoDAO.associaEvento(this);
        }
        return evento;
    }

    public void setStato(Stato stato) {
        this.stato = stato;
    }

    public void setProprietario(ProfiloUtente proprietario) {
        this.proprietario = proprietario;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public boolean verifificaAccesso(Evento e){


        LocalDate localDate = LocalDate.now();
        java.util.Date data = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        System.out.println("Stato: " + this.stato.equals(Stato.VALIDO) + " Evento: " + this.evento.equals(e) + " Data: " + this.evento.getData().equals(data)) ;

        return this.stato.equals(Stato.VALIDO) &&
                this.evento.equals(e) &&
                this.evento.getData().equals(data);
    }

    public void marcaComeConsumato(){
        this.stato = Stato.CONSUMATO;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Biglietto biglietto = (Biglietto) o;
        return Objects.equals(codice, biglietto.codice);
    }


    @Override
    public String toString() {
        return "Biglietto{" +
                "codice='" + codice + '\'' +
                ", stato=" + stato +
                ", proprietario=" + proprietario +
                ", evento=" + evento +
                '}';
    }


    public void aggiornaDAO() {
        bigliettoDAO.aggiornaBiglietto(this);
    }


}
