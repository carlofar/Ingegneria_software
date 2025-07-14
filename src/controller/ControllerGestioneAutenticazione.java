package controller;


import entity.CatalogoUtenti;
import entity.ProfiloUtente;
import utilities.RegistrationException;

import javax.naming.AuthenticationException;

public class ControllerGestioneAutenticazione {

    //Tale controller è fatto solo per far "funzionare" il sistema
    private static ControllerGestioneAutenticazione instance;
    private ProfiloUtente utenteLoggato;

    private ControllerGestioneAutenticazione(){
        this.utenteLoggato = null;
    }

    public static ControllerGestioneAutenticazione getInstance() {
        if(instance == null){
            instance = new ControllerGestioneAutenticazione();
        }
        return instance;
    }


    public void registraUtente(String nome, String cognome, String eMail, String password, String immagine){
            ProfiloUtente p = new ProfiloUtente(nome,cognome,eMail,password,ProfiloUtente.Ruolo.UTENTE);
            if(immagine != null){
                p.aggiornaImmagine(immagine);
            }
            CatalogoUtenti.getInstance().aggiungiProfilo(p);
            utenteLoggato = p;
            ControllerGestioneProfilo.getInstance().setUtenteLoggato(utenteLoggato);




    }





    public void login(String eMail, String password)throws AuthenticationException{

        utenteLoggato = CatalogoUtenti.getInstance().trovaUtenteByEmail(eMail);

        if(!utenteLoggato.checkPassword(password)){
            throw new AuthenticationException("Password errata");
        }

        ControllerGestioneProfilo.getInstance().setUtenteLoggato(utenteLoggato);
    }

    public void checkCredenzialiRegistrzione(String eMail) throws RegistrationException {
        CatalogoUtenti.getInstance().checkUtenteByEmail(eMail);
    }




    public String getNomeUtente() {
        return utenteLoggato.getNome();
    }
    public String getCognomeUtente() {
        return utenteLoggato.getCognome();
    }
    public String getEmailUtente() {
        return utenteLoggato.getEmail();
    }
}

