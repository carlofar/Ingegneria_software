package controller;

import dao.BigliettoDAO;
import entity.*;
import utilities.ProfileException;

import javax.naming.AuthenticationException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

public class ControllerGestioneProfilo {

    private final BigliettoDAO bigliettoDAO = new BigliettoDAO();

    public ProfiloUtente getAccount(String eMail)throws AuthenticationException {

        return CatalogoUtenti.getInstance().trovaUtenteByEmail(eMail);
    }

    public int calcolaNumEventiPartecipati(ProfiloUtente p){

        return p.calcolaNumEventiPartecipanti();
    }

    public List<Biglietto> getStoricoBiglietti(ProfiloUtente p){
        return p.listaBigliettiAssociati();
    }

    public void mostraDati(ProfiloUtente p, List<Biglietto> biglietti){
        System.out.println(p.toString());
        for (Biglietto biglietto : biglietti) {
            System.out.println(biglietto.toString());
        }
    }

    public String getImmagineProfilo(ProfiloUtente p)throws ProfileException{
        if(p.trovaImmagineProfilo() != null){
            return p.trovaImmagineProfilo();
        }else{
            throw new ProfileException("Non hai un'immagine associata al profilo");
        }
    }

    public void scaricaBiglietto(Biglietto b){
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("Biglietto.pdf"));
            document.open();
            document.add(new Paragraph(b.toString()));
            document.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public String toStringBiglietto(Biglietto b){
        String str = b.toString();
        int start = str.indexOf("Biglietto->") + "Biglietto->".length();
        int end = str.indexOf("Evento",start);
        String toStringBiglietto = str.substring(start,end);
        return toStringBiglietto + " nome evento: " + b.infoEvento().getTitolo();

    }

    public void setImmagineProfilo(ProfiloUtente p, String immagine){
        p.aggiornaImmagine(immagine);
    }

}
