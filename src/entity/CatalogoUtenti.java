package entity;

import dao.UtenteDAO;
import utilities.RegistrationException;

import javax.naming.AuthenticationException;
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

    public ProfiloUtente trovaUtenteByEmail(String eMail)throws AuthenticationException {
        ProfiloUtente rv = utenteDAO.trovaUtenteByEmail(eMail);
        if(rv == null){
            throw new AuthenticationException("Utente non trovato");
        }
        return rv;
    }

    public void checkUtenteByEmail(String eMail) throws RegistrationException {
        if(utenteDAO.trovaUtenteByEmail(eMail) != null){
            throw new RegistrationException("La Email è già presente nel sistema");
        }
    }
}
