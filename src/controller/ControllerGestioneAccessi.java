package controller;
import dao.BigliettoDAO;
import dao.EventoDAO;
import entity.*;
import java.util.Date;
public class ControllerGestioneAccessi {


    private BigliettoDAO bigliettoDAO = new BigliettoDAO();
    private EventoDAO eventoDAO = new EventoDAO();



    public boolean effettuaAccesso (String codice, Evento e){
        Biglietto b = new Biglietto();
        b.setCodice(codice);
        boolean trovato = false;
        for (int i = 0; i < CatalogoEventi.getInstance().getListaEventi().size(); i++) {
            for (int j = 0; j < CatalogoEventi.getInstance().getListaEventi().get(i).getListaBiglietti().size(); j++) {
                if (CatalogoEventi.getInstance().getListaEventi().get(i).getListaBiglietti().get(j).equals(b)){
                    Biglietto b1 = CatalogoEventi.getInstance().getListaEventi().get(i).getListaBiglietti().get(j);
                    trovato = true;
                }
            }
        }

        if (!trovato){
            return trovato;
        }

        Biglietto bigliettoTrovato = e.trovaBiglietto(codice);
        if(bigliettoTrovato == null){
            //GESTIRE ECCEZIONE
            return false;
        }


        System.out.println("Biglietto trovato " + bigliettoTrovato.toString());
        if(!bigliettoTrovato.verifificaAccesso(e)){
            //gestione eccezione

            System.out.println("Biglietto non disponibile " + bigliettoTrovato.verifificaAccesso(e));
            return false;
        }

        b.marcaComeConsumato();
        b.aggiornaDAO();
        e.aggiungiPartecipante();
        e.aggiornaEntrataDAO();
        return true;
    }





}
