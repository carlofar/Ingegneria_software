package entity;

import dao.UtenteDAO;

import java.util.List;
import java.util.ArrayList;


public class CatalogoUtenti {

    private static CatalogoUtenti instance;
    private List<ProfiloUtente> listaUtenti;

    private UtenteDAO utenteDAO = new UtenteDAO();

    private CatalogoUtenti(){
        listaUtenti = new ArrayList<>();
    }

    public static CatalogoUtenti getInstance(){
        if(instance == null){
            instance = new CatalogoUtenti();
        }
        return instance;
    }


    public void aggiungiProfilo(ProfiloUtente p){
        listaUtenti.add(p);
        utenteDAO.SalvaUtente(p);
    }



    public List<ProfiloUtente> getListaUtenti(){
        return new ArrayList<>(listaUtenti);
    }

    public ProfiloUtente trovaUtenteByEmail(String eMail) {
        return utenteDAO.trovaUtenteByEmail(eMail);
    }

    public boolean ceckUtenteByEmail(String eMail) {
        return utenteDAO.trovaUtenteByEmail(eMail) != null;
    }
}
