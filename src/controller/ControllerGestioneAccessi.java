package controller;
import dao.BigliettoDAO;
import dao.EventoDAO;
import entity.*;
import java.util.Date;
public class ControllerGestioneAccessi {


    private BigliettoDAO bigliettoDAO = new BigliettoDAO();
    private EventoDAO eventoDAO = new EventoDAO();



    public boolean effettuaAccesso (String codice, Evento e){

        Biglietto b = bigliettoDAO.trovaBigliettoByCodice(codice);
        if(b == null){
            //GESTIRE ECCEZIONE
            return false;
        }
        if(!b.verifificaAccesso(e)){
            //gestione eccezione
            return false;
        }
        b.marcaComeConsumato();
        bigliettoDAO.aggiornaBiglietto(b);
        e.aggiungiPartecipante();
        eventoDAO.aggiornaEntrata(e);
        return true;
    }





}
