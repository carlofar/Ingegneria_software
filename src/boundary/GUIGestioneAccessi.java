package boundary;
import controller.ControllerGestioneAccessi;
import controller.ControllerGestioneAccessi;
import controller.ControllerGestioneCatalogo;
import entity.Biglietto;
import entity.Evento;
import dao.BigliettoDAO;
import dao.EventoDAO;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class GUIGestioneAccessi {

    private static final ControllerGestioneCatalogo controllerGestioneCatalogo = new ControllerGestioneCatalogo();
    private static final ControllerGestioneAccessi controllerGestioneAccessi = new ControllerGestioneAccessi();
    private static final BigliettoDAO bigliettoDAO = new BigliettoDAO();



    public static void mostraTabellaEventi(){

        List<Evento> e = controllerGestioneCatalogo.getEventiOdierni();
        for (Evento evento : e) {
            System.out.println(evento.toString());
        }
    }
//supponiamo che swing se selezioniamo l'evento possa restituire il tipo di ritorno Evento
    public static Evento selezionaEvento(){
        return null;
        //dedicato a quando useremo swing;
    }
    public static String mostraInserimentoCodice(){

        System.out.println("Inserisci il codice del biglietto");
        return new java.util.Scanner(System.in).nextLine();

        //codice inserito
        //il sistema ora controlla che il codice corrisponda all'evento etc
        //VerificaCodice(codice,e);


    }


    public static void verificaIdonieta(String codice, Evento e){
        //passare per l'entity che chiede la query per trovare se esiste il biglietto associato a questo codice

        if(controllerGestioneAccessi.effettuaAccesso(codice,e)){
            System.out.println("Accesso concesso");
        }else{
            System.out.println("Accesso negato");
        }

    }


    public static void main(String[] args) {

        //GUIGestioneAccessi.mostraTabellaEventi();
        LocalDate localDate = LocalDate.of(2025, 7, 4);
        Date data = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Evento e = new Evento("GPMonza", "F1", data, "14:00", "Monza", 200,4000);
        e.setId("3");
        e.setNumPartecipantiAttuali(2000);
        GUIGestioneAccessi.verificaIdonieta("B10", e);

    }





}
