package entity;
import java.time.LocalDate;


public class  Biglietto {

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

        return this.stato == Stato.VALIDO &&
                this.evento.equals(e) &&
                this.evento.getData().equals(LocalDate.now());

    }

    public void marcaComeConsumato(){
        this.stato = Stato.CONSUMATO;
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

}
