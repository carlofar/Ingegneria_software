package controller;
import dao.BigliettoDAO;
import dao.EventoDAO;
import entity.*;
import java.util.UUID;



public class ControllerGestioneAcquisto {


    private final SistemaPOS sistemaPOS = new SistemaPOS();
    private final BigliettoDAO bigliettoDAO = new BigliettoDAO();

    public boolean verificaDisponibilita(Evento e){
        return e.verificaDisponibilita();
    }


    public void acquistaBiglietto(ProfiloUtente p,Evento e){

        if (!sistemaPOS.autorizzaPagamento(p,e.getCosto())){
            //GESTIRE ECCEZIONE
            System.out.println("Non hai abbastanza soldi per acquistare questo biglietto");
        }else{
            Biglietto b = generaBiglietto(p,e);
            b.salvaBigliettoDAO();
            e.aggiungiBiglietto(b);
            System.out.println("BigliettoAcquistato");

        }

    }


    private Biglietto generaBiglietto(ProfiloUtente p,Evento e){
        String codice = "EVT-" + e.getId() + "-UT" + p.getEmail().hashCode() + "-" + UUID.randomUUID().toString().substring(0,5);
        return new Biglietto(codice,p,e);
    }
}
