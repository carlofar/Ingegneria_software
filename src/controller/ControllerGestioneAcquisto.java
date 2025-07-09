package controller;
import dao.BigliettoDAO;
import dao.EventoDAO;
import entity.*;
import utilities.PaymentException;
import utilities.TicketException;

import java.util.UUID;

//1. REGISTRAZIONE/AUTENTICAZIONE => GUIAUTH -> 2.MOSTRA CATALOGOEVENTI => GUIHOME |||
//3.ACQUISTO => GUIACQUISTO
//4.ACCESSO => GUIGESTIONEACCESSI

public class ControllerGestioneAcquisto {


    private final SistemaPOS sistemaPOS = new SistemaPOS();
    private final BigliettoDAO bigliettoDAO = new BigliettoDAO();

    public void verificaDisponibilita(Evento e)throws TicketException{
        if (!e.verificaDisponibilita()){
            throw new TicketException("L'evento non ha più posti disponibili");
        }
    }


    public void acquistaBiglietto(ProfiloUtente p,Evento e)throws PaymentException, TicketException {

        if (sistemaPOS.autorizzaPagamento(p,e.getCosto())){
            //CONTROLLO:
            //L'EVENTO SELEZIONATO NON PUO' ESSERE ACQUISTATO DALL'UTENTE SE GIA' LO HA FATTO
            //RICERCARE EVENTUALI BIGLIETTI ACQUISTATI PER QUELL'EVENTO
            if (!p.trovaBiglietto(e)){
                Biglietto b = generaBiglietto(p,e);
                //DA VEDERE
                System.out.println("Biglietto generato: " + b.toString() + b.getProprietario().toString());
                p.aggiungiBiglietto(b);
                b.salvaBigliettoDAO();
                e.aggiungiBiglietto(b);
                System.out.println("BigliettoAcquistato");
            }else{
                throw new TicketException("Per quest'evento hai già acquistato un biglietto");
                //throw new ExceptionAcquisto("")
            }
        }

    }


    private Biglietto generaBiglietto(ProfiloUtente p,Evento e){
        String codice = "EVT-" + e.getId() + "-UT" + p.getEmail().hashCode() + "-" + UUID.randomUUID().toString().substring(0,5);
        return new Biglietto(codice,p,e);
    }



}
