package controller;

import dto.DTO;
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


    public void RegistraUtente(String nome, String cognome, String eMail, String password,String immagine){
            ProfiloUtente p = new ProfiloUtente(nome,cognome,eMail,password,ProfiloUtente.Ruolo.UTENTE);
            if(immagine != null){
                p.aggiornaImmagine(immagine);
            }
            CatalogoUtenti.getInstance().aggiungiProfilo(p);
            utenteLoggato = p;
            ControllerGestioneProfilo.getInstance().setUtenteLoggato(utenteLoggato);
        //trovare utente by emali
        //se non lo trova lo registra
        //ritorna true
        //se lo trova manda messaggio di errore:"Utente già registrato con questa mail"

//        UtenteDAO dao = new UtenteDAO();
//        ProfiloUtente p = dao.trovaUtenteByEmail(eMail);
//        if(p == null){
//            p = new ProfiloUtente(nome,cognome,eMail,password,ProfiloUtente.Ruolo.UTENTE);
//            dao.SalvaUtente(p);
//            return true;
//        }else{
//            //generare eccezione e mandare mess di errore.
//            return false;
//        }




    }





    public void login(String eMail, String password)throws AuthenticationException{

        utenteLoggato = CatalogoUtenti.getInstance().trovaUtenteByEmail(eMail);

        if(!utenteLoggato.checkPassword(password)){
            throw new AuthenticationException("Password errata");
        }

        ControllerGestioneProfilo.getInstance().setUtenteLoggato(utenteLoggato);
    }

    public void checkCredenzialiRegistrzione(String eMail, String password) throws RegistrationException {

        CatalogoUtenti.getInstance().checkUtenteByEmail(eMail);
        //FARE COSE TESTING CARLO
    }


    public DTO getProfiloLoggato() {
        return new DTO("utente",utenteLoggato.getNome(),utenteLoggato.getCognome(),utenteLoggato.getEmail());
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

