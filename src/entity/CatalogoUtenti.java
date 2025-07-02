package entity;

import java.util.List;
import java.util.ArrayList;


public class CatalogoUtenti {

    private static CatalogoUtenti instance;
    private List<ProfiloUtente> listaUtenti;

    private CatalogoUtenti(){
        listaUtenti = new ArrayList<>();
    }

    public static CatalogoUtenti getInstance(){
        if(instance == null){
            instance = new CatalogoUtenti();
        }
        return instance;
    }


    public void aggiungiProfilo(ProfiloUtente p){
        listaUtenti.add(p);
    }

    public List<ProfiloUtente> getListaUtenti(){
        return new ArrayList<>(listaUtenti);
    }

    public ProfiloUtente trovaUtenteByEmail(String eMail){
        for(ProfiloUtente p : listaUtenti){
            if(p.getEmail().equalsIgnoreCase(eMail)){
                return p;
            }
        }
            return null; //NO! DEVE LANCIARE UN ECCEZIONE
    }


}
