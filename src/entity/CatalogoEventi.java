package entity;

import dao.EventoDAO;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;


public class CatalogoEventi {
    private static CatalogoEventi instance;
    private List<Evento> listaEventi;

    private static EventoDAO eventoDAO = new EventoDAO();

    private CatalogoEventi(){
        listaEventi = new ArrayList<>();
    }

    public static CatalogoEventi getInstance(){
        if(instance == null){
            instance = new CatalogoEventi();
        }
        return instance;
    }


    public void aggiungiEvento(Evento e){
        listaEventi.add(e);
    }




    //PROBLEMATICHE DI ERRORE QUI
    public List<Evento> getListaEventi(){

        if ( listaEventi.isEmpty()){
            listaEventi = eventoDAO.getEventi();
            return listaEventi;
        }
        LocalDate dataOdierna = LocalDate.now();
        List<Evento> eventi = new ArrayList<>();
        for (int i = 0; i < listaEventi.size(); i++) {
            if (listaEventi.get(i).getData().after(Date.from(dataOdierna.minusDays(1).atStartOfDay(java.time.ZoneId.systemDefault()).toInstant()))){

                eventi.add(listaEventi.get(i));

            }
        }

        return eventi;
    }

    //PROBLEMATICHE DI ERRORE QUI
    public List<Evento> getEventiOdierni(){
        List<Evento> eventiOdierni;
        if ( listaEventi.isEmpty()){
            listaEventi = eventoDAO.getEventi();
        }
        Date dataOdierna = Date.from(LocalDate.now().atStartOfDay(java.time.ZoneId.systemDefault()).toInstant());
        eventiOdierni = eventoDAO.filtraPerData(dataOdierna);//POSSIAMO FARLO ANCHE IN LOCALE
        return eventiOdierni;

//        Date dataOdierna = Date.from(LocalDate.now().atStartOfDay(java.time.ZoneId.systemDefault()).toInstant());
//        List<Evento> eventiOdierni = new ArrayList<>();
//        for (int i = 0; i < listaEventi.size(); i++) {
//            if (listaEventi.get(i).getData().equals(dataOdierna)){
//                eventiOdierni.add(listaEventi.get(i));
//            }
//        }
//        return eventiOdierni;
    }


    public List<Evento> filtraPerData(LocalDate data){
        //List<Evento> eventiOdierni = eventoDAO.getEventi();
        List<Evento> eventi = new ArrayList<>();
        for (int i = 0; i < listaEventi.size(); i++) {
            if (listaEventi.get(i).getData().equals(data)){

                eventi.add(listaEventi.get(i));

            }
        }

        return eventi;
    }

    public List<Evento> filtraPerLuogo(String luogo){
        List<Evento> eventi = new ArrayList<>();
        for (int i = 0; i < listaEventi.size(); i++) {
            if (listaEventi.get(i).getLuogo().equals(luogo)){

                eventi.add(listaEventi.get(i));

            }
        }
        return eventi;
    }


    public Evento getEvento(String id){
        if (listaEventi.isEmpty()){
            listaEventi = eventoDAO.getEventi();
        }
        for (Evento e: listaEventi){
            if (e.getId().equals(id)){
                return e;
            }
        }
        return null;
    }

}
