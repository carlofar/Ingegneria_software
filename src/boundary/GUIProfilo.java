//package boundary;
//
//import controller.ControllerGestioneAutenticazione;
//import controller.ControllerGestioneProfilo;
//import entity.Biglietto;
//import entity.ProfiloUtente;
//
//import java.util.List;
//
//
//public class GUIProfilo {
//
////    //public void apriAccount()
////    //public void mostraImmagineProfilo()
////    //public void visualizzaNumEventi()
//
//    private static ControllerGestioneProfilo controllerGestioneProfilo = new ControllerGestioneProfilo();
//
//
//    public static void main(String[] args) {
//
//
//
//
//        ProfiloUtente p = GUIProfilo.apriAccount("gennaro.derosa10@studenti.unina.it");
//        GUIProfilo.visualizzaImmagineProfilo(p);
//        System.out.println("Eventi partecipati : " + GUIProfilo.calcolaNumEventiPartecipati(p));
//
//    }
//
//    public static ProfiloUtente apriAccount(String eMail) {
//
//        ProfiloUtente p = controllerGestioneProfilo.getAccount(eMail);
//        List<Biglietto> biglietto = controllerGestioneProfilo.getStoricoBiglietti(p);
//        controllerGestioneProfilo.mostraDati(p, biglietto);
//        return p;
//
//    }
//
//
//    public static void visualizzaImmagineProfilo(ProfiloUtente p){
//
//        if(p.getImmagine() != null){
//            System.out.println("Immagine: " + p.getImmagine());
//        }else{
//            System.out.println("Immagine non presente");
//        }
//        //da chiedere se possiamo chiamare direttamente il metodo del profiloutente senza passare per il controller
//    }
//
//    public static int calcolaNumEventiPartecipati(ProfiloUtente p){
//        return controllerGestioneProfilo.calcolaNumEventiPartecipati(p);
//    }
//
//}
