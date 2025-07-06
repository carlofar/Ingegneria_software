package controller;

import dao.UtenteDAO;
import entity.CatalogoUtenti;
import entity.ProfiloUtente;

import java.util.Scanner;

public class ControllerGestioneAutenticazione {

    //Tale controller è fatto solo per far "funzionare" il sistema

    public boolean RegistraUtente(String nome, String cognome, String eMail, String password){


        if(!CatalogoUtenti.getInstance().ceckUtenteByEmail(eMail)){
            CatalogoUtenti.getInstance().aggiungiProfilo(new ProfiloUtente(nome,cognome,eMail,password,ProfiloUtente.Ruolo.UTENTE));
            return true;
        }else{
            return false;
        }

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





    public ProfiloUtente login(String eMail, String password){

        if(CatalogoUtenti.getInstance().ceckUtenteByEmail(eMail)){
            ProfiloUtente p = CatalogoUtenti.getInstance().trovaUtenteByEmail(eMail);
            if(p.checkPassword(password)){
                System.out.println("Login effettuato con successo");
                return p;
            }else{
                System.out.println("Password errata");
                //gestire eccezione
                return null;
            }
        }else{
            //gestireEccezione
            System.out.println("Utente non registrato");
            return null;
        }


    }
}

