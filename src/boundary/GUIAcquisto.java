package boundary;
import controller.ControllerGestioneAcquisto;
import controller.ControllerGestioneAutenticazione;
import controller.ControllerGestioneCatalogo;
import dao.BigliettoDAO;
import entity.Biglietto;
import entity.Evento;
import entity.ProfiloUtente;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class GUIAcquisto {

    private static final ControllerGestioneAcquisto controllerGestioneAcquisto = new ControllerGestioneAcquisto();
    private static final ControllerGestioneCatalogo controllerGestioneCatalogo = new ControllerGestioneCatalogo();
    private static final ControllerGestioneAutenticazione controllerGestioneAutenticazione = new ControllerGestioneAutenticazione();

    public static void main(String[] args){

        ProfiloUtente p = controllerGestioneAutenticazione.login("gennaro.derosa10@studenti.unina.it","GennaroDR3");
        //GUIAcquisto.mostraListaEventi();
        //DA INTERFACCIA SELEZIONA L'EVENTO
        LocalDate localDate = LocalDate.of(2025, 7, 4);
        Date data = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Evento e = new Evento("GPMonza", "F1", data, "14:00", "Monza", 200,4000);
        e.setId("3");
        if(GUIAcquisto.verificaDisponibilita(e)){

            System.out.println("Disponibile, Procedere all'acquisto?");
            Scanner input = new Scanner(System.in);
            String risposta = input.nextLine();
//            if (Objects.equals(risposta, "SI")){
//                GUIAcquisto.acquistaBiglietto(p,e);
//            }

        }

    }



    public static boolean verificaDisponibilita(Evento e){
        return controllerGestioneAcquisto.verificaDisponibilita(e);
    }

//    public static void acquistaBiglietto(ProfiloUtente p, Evento e){
//        controllerGestioneAcquisto.acquistaBiglietto(p,e);
//    }

    public static void mostraListaEventi(){

        List<Evento> evento = controllerGestioneCatalogo.getEventi();
        for (Evento evento1 : evento) {
            System.out.println(evento1.toString());
        }

    }
}
