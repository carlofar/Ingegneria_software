package controller;

import dao.BigliettoDAO;
import entity.*;
import java.util.List;
public class ControllerGestioneProfilo {

    private final BigliettoDAO bigliettoDAO = new BigliettoDAO();

    public ProfiloUtente getAccount(String eMail){

        return CatalogoUtenti.getInstance().trovaUtenteByEmail(eMail);
    }

    public int calcolaNumEventiPartecipati(ProfiloUtente p){

        return p.calcolaNumEventiPartecipanti();
    }

    public List<Biglietto> getStoricoBiglietti(ProfiloUtente p){
        return p.getBiglietti();
    }

    public void mostraDati(ProfiloUtente p, List<Biglietto> biglietti){
        System.out.println(p.toString());
        for (Biglietto biglietto : biglietti) {
            System.out.println(biglietto.toString());
        }
    }
}
