package boundary;
import controller.*;
import entity.Biglietto;
import entity.ProfiloUtente;

import java.util.Scanner;

public class GUILogin {
    private static final ControllerGestioneAutenticazione controllerGestioneAutenticazione = new ControllerGestioneAutenticazione();

    public static void main(String[] args) {
//        System.out.print("Registrazione o Autenticazione ?");//DUE BOTTONI DA PREMERE
//        GUILogin.registrazione();

        ProfiloUtente p = GUILogin.autenticazione();
        if(p == null){
            System.out.println("Errore nel Autenticazione");
        }else{
            System.out.println(p.toString());
        }
        //System.out.println(GUILogin.autenticazione().toString());
    }


    public static void registrazione(){
        Scanner input = new Scanner(System.in);
        System.out.println("Inserisci eMail: ");
        String eMail = input.nextLine();
        //FARE CHECK SUI TESTING CHE HA FATTO CARLO
        if(controllerGestioneAutenticazione.checkEmail(eMail)){
            System.out.println("Email non valida");
            //gestire e chiedere di inserire una mail valida
        } else {
            System.out.println("Inserisci password: ");
            String password = input.nextLine();
            //FARE CHECK SUI TESTING FATTI DA CARLO
            System.out.println("Inserisci nome: ");
            String nome = input.nextLine();
            System.out.println("Inserisci cognome: ");
            String cognome = input.nextLine();
            controllerGestioneAutenticazione.RegistraUtente(nome,cognome, eMail,password);
            //SE QUA STA ERRORE
            System.out.println("Registrazione con successo");
            //il print sta qua
        }
        

    }



    private static ProfiloUtente autenticazione(){

        Scanner input = new Scanner(System.in);
        System.out.println("Inserisci email: ");
        String eMail = input.nextLine();
        if(!controllerGestioneAutenticazione.checkEmail(eMail)){
            System.out.println("Email non trovata");
        }else{
            System.out.println("Inserisci password: ");
            String password = input.nextLine();
            return controllerGestioneAutenticazione.login(eMail,password);
        }
        return null;
    }

}
