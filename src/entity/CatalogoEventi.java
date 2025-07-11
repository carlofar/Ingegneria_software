package entity;

import dao.EventoDAO;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;


public class CatalogoEventi {
    private static CatalogoEventi instance;
    private List<Evento> listaEventi;

    private static final EventoDAO eventoDAO = new EventoDAO();

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

    public List<Evento> getEventiAcquistabili(){

        if ( listaEventi.isEmpty()){
            listaEventi = eventoDAO.getEventi();
            //return listaEventi;
        }
        LocalDate dataOdierna = LocalDate.now();
        List<Evento> eventi = new ArrayList<>();
        for (Evento evento : listaEventi) {
            if (dataOdierna.minusDays(1).isBefore(evento.getData())) {
                eventi.add(evento);
            }
        }

        return eventi;
    }

    //PROBLEMATICHE DI ERRORE QUI
    public List<Evento> getEventiOdierni(){
        if ( listaEventi.isEmpty()){
            listaEventi = eventoDAO.getEventi();
        }

        List<Evento> eventiOdierni = new ArrayList<>();
        LocalDate dataOdierna = LocalDate.now();
        for (Evento evento : listaEventi) {
            if (evento.getData().equals(dataOdierna)){
                eventiOdierni.add(evento);
            }
        }
        return eventiOdierni;
    }


    public List<Evento> filtraPerData(LocalDate data){
        //List<Evento> eventiOdierni = eventoDAO.getEventi();
        if ( listaEventi.isEmpty()){
            listaEventi = eventoDAO.getEventi();
        }

        List<Evento> eventi = new ArrayList<>();
        for (Evento evento : listaEventi) {
            LocalDate dataEvento = evento.getData();
            if (data.minusDays(1).isBefore(dataEvento)) {
                eventi.add(evento);
            }
        }

        return eventi;
    }

    public List<Evento> filtraPerLuogo(String luogo){
        if ( listaEventi.isEmpty()){
            listaEventi = eventoDAO.getEventi();
        }
        List<Evento> eventi = new ArrayList<>();
        for (Evento evento : listaEventi) {
            if (evento.getLuogo().contains(luogo)) {
                eventi.add(evento);
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
