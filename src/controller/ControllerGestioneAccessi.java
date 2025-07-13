package controller;
import dto.DTO;
import entity.*;

import javax.naming.AuthenticationException;
import java.nio.file.AccessDeniedException;

public class ControllerGestioneAccessi {

    private static ControllerGestioneAccessi instance;
    private ControllerGestioneAccessi(){}
    public static ControllerGestioneAccessi getInstance(){
        if(instance == null){
            instance = new ControllerGestioneAccessi();
        }
        return instance;
    }



    public void effettuaAccesso (String codice, DTO evento, String mailUtente) throws AccessDeniedException {
        //CHECK VALIDAZIONE INPUT
        ProfiloUtente p = null;
        try {
            p = CatalogoUtenti.getInstance().trovaUtenteByEmail(mailUtente);
        } catch (AuthenticationException e) {
            System.out.println("Errore: " + e.getMessage());
        }
        Evento e = CatalogoEventi.getInstance().getEvento(evento.getCampoInPos(1));

        Biglietto bigliettoTrovato = e.trovaBiglietto(codice);

        if(bigliettoTrovato == null){
            throw new AccessDeniedException("Il codice non è associato all'evento selezionato");
        }

        //System.out.println("Biglietto trovato " + bigliettoTrovato.toString());

        if(!bigliettoTrovato.infoProprietario().equals(p)){
            throw new AccessDeniedException("Il biglietto non è associato al profilo selezionato");
        }


        if (bigliettoTrovato.verificaAccesso(e)){
            bigliettoTrovato.marcaComeConsumato();
            e.aggiungiPartecipante();
            e.aggiornaEntrataDAO();
        }

    }

//
//    public String visualizzaInformazioniEvento(Evento e){
//        LocalDate data = e.getData();
//        //LocalDate data = ((java.sql.Date) e.getData()).toLocalDate();
//        return "Titolo: " + e.getTitolo() + "Data: " + data.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ", " + e.getOraInizio();
//
//
//    }


}
