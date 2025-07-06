package controller;
import entity.CatalogoEventi;
import entity.Evento;


import java.util.List;



public class ControllerGestioneCatalogo {



    public List<Evento> getEventi(){
        return CatalogoEventi.getInstance().getListaEventi();
    }

    public Evento getEvento(String id){
        return CatalogoEventi.getInstance().getEvento(id);
    }


    public List<Evento> getEventiOdierni(){
        return CatalogoEventi.getInstance().getEventiOdierni();
    }


}
