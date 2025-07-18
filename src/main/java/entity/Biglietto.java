package entity;
import dao.BigliettoDAO;
import utilities.TicketException;

import java.nio.file.AccessDeniedException;
import java.util.Objects;


public class  Biglietto {

    private static final BigliettoDAO bigliettoDAO = new BigliettoDAO();




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


    public ProfiloUtente infoProprietario() {
        if ( proprietario == null ){
            bigliettoDAO.getProprietario(this);
        }
        return this.proprietario;
    }

    public Evento infoEvento() {
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

    public boolean verificaAccesso(Evento e)throws AccessDeniedException{





        if (!this.stato.equals(Stato.VALIDO)){
            throw new AccessDeniedException("Il biglietto è stato già consumato");
        }
        if (!this.evento.equals(e)){
            throw new AccessDeniedException("L'evento selezionato non coincide!");
        }
        if (!this.evento.getData().equals(e.getData())){
            throw new AccessDeniedException("La data sul biglietto non corrisponde con la data odierna");
        }
        return true;
    }

    public void marcaComeConsumato(){
        this.stato = Stato.CONSUMATO;
        bigliettoDAO.aggiornaBiglietto(this);
    }


    public void salvaBigliettoDAO() throws TicketException {
        bigliettoDAO.salvaBiglietto(this);
    }

    public void cancellaBigliettoDAO() {
        bigliettoDAO.cancellaBiglietto(this);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codice);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Biglietto biglietto = (Biglietto) o;
        return Objects.equals(codice, biglietto.codice);
    }


    @Override
    public String toString() {
        return "Biglietto->" +
                "codice: " + codice +
                ", stato:" + stato +
                "   \nEvento Associato: " + evento.toString() + "\n";
    }



}
