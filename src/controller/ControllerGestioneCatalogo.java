package controller;
import entity.CatalogoEventi;
import entity.Evento;


import java.time.LocalDate;
import java.util.List;



public class ControllerGestioneCatalogo {



    public List<Evento> getEventi(){
        return CatalogoEventi.getInstance().getEventiAcquistabili();
    }

    public Evento getEvento(String id){
        return CatalogoEventi.getInstance().getEvento(id);
    }


    public List<Evento> getEventiOdierni(){
        return CatalogoEventi.getInstance().getEventiOdierni();
    }

    public List<Evento> filtraEventiPerData(LocalDate data) {
        return CatalogoEventi.getInstance().filtraPerData(data);
    }

    public List<Evento> filtraEventiPerLuogo(String luogo) {
        return CatalogoEventi.getInstance().filtraPerLuogo(luogo);
    }

}
