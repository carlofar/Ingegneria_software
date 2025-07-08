package controller;
import dao.BigliettoDAO;
import dao.EventoDAO;
import entity.*;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class ControllerGestioneAccessi {


    private BigliettoDAO bigliettoDAO = new BigliettoDAO();
    private EventoDAO eventoDAO = new EventoDAO();



    public void effettuaAccesso (String codice, Evento e, ProfiloUtente p) throws AccessDeniedException {
//        Biglietto b = new Biglietto();
//        b.setCodice(codice);
//        boolean trovato = false;
//        List<Evento> listaEventi = CatalogoEventi.getInstance().getListaEventi();
//        //RICERCO SE IL BIGLIETTO ESISTE CON CODICE = codice
//        for (int i = 0; i < listaEventi.size(); i++) {
//            for (int j = 0; j < listaEventi.get(i).getListaBiglietti().size(); j++) {
//                if (CatalogoEventi.getInstance().getListaEventi().get(i).getListaBiglietti().get(j).equals(b)){
//                    Biglietto b1 = CatalogoEventi.getInstance().getListaEventi().get(i).getListaBiglietti().get(j);
//                    trovato = true;
//                }
//            }
//        }
//
//        if (!trovato){
//            throw new AccessDeniedException("Il codice non è associato a nessun evento");
//        }

        //IL BIGLIETTO ESISTE
        //RICERCHIAMO IL BIGLIETTO CON CODICE = codice NELLA LISTA DI BIGLIETTI DELL'EVENTO SELEZIONATO

        Biglietto bigliettoTrovato = e.trovaBiglietto(codice);
        //bigliettoTrovato.getProprietario == null
        if(bigliettoTrovato == null){
            throw new AccessDeniedException("Il codice non è associato all'evento selezionato");
        }

        //System.out.println("Biglietto trovato " + bigliettoTrovato.toString());

        if(!bigliettoTrovato.getProprietario().equals(p)){
            throw new AccessDeniedException("Il biglietto non è associato al profilo selezionato");
        }


        if (bigliettoTrovato.verifificaAccesso(e)){
            bigliettoTrovato.marcaComeConsumato();
            bigliettoTrovato.aggiornaDAO();
            e.aggiungiPartecipante();
            e.aggiornaEntrataDAO();
        }

    }


    public String visualizzaInformazioniEvento(Evento e){
        LocalDate data = ((java.sql.Date) e.getData()).toLocalDate();
        return "Titolo: " + e.getTitolo() + "Data: " + data.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ", " + e.getOraInizio();


    }


}
