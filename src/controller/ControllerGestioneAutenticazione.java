package controller;

import dao.UtenteDAO;
import entity.CatalogoUtenti;
import entity.ProfiloUtente;
import utilities.RegistrationException;

import javax.naming.AuthenticationException;
import java.util.Scanner;

public class ControllerGestioneAutenticazione {

    //Tale controller è fatto solo per far "funzionare" il sistema



    public ProfiloUtente RegistraUtente(String nome, String cognome, String eMail, String password,String immagine){
            ProfiloUtente p = new ProfiloUtente(nome,cognome,eMail,password,ProfiloUtente.Ruolo.UTENTE);
            if(immagine != null){
                p.setImmagine(immagine);
            }
            CatalogoUtenti.getInstance().aggiungiProfilo(p);
            return p;

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





    public ProfiloUtente login(String eMail, String password)throws AuthenticationException{

        ProfiloUtente p = CatalogoUtenti.getInstance().trovaUtenteByEmail(eMail);

        if(p.checkPassword(password)){
            System.out.println("Login effettuato con successo");
            return p;
        }else{
            throw new AuthenticationException("Password errata");
        }


    }

    public void checkCredenzialiRegistrzione(String eMail, String password)throws RegistrationException {

        CatalogoUtenti.getInstance().ceckUtenteByEmail(eMail);
        //FARE COSE TESTING CARLO
    }


}

