package controller;
import entity.CatalogoEventi;
import entity.Evento;


import java.util.List;



public class ControllerGestioneCatalogo {



    public List<Evento> getEventi(){
        return CatalogoEventi.getInstance().getListaEventi();
    }


    public List<Evento> getEventiOdierni(){
        return CatalogoEventi.getInstance().getEventiOdierni();
    }


}
