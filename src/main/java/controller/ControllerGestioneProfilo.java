package controller;

import dto.DTO;
import entity.*;
import utilities.ProfileException;

import javax.naming.AuthenticationException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

public class ControllerGestioneProfilo {

    private static ProfiloUtente utenteLoggato;

    private static ControllerGestioneProfilo instance;
    private ControllerGestioneProfilo(){
    }



    public static ControllerGestioneProfilo getInstance(){
        if(instance == null){
            instance = new ControllerGestioneProfilo();
        }
        return instance;
    }

    public static void setUtenteLoggato(ProfiloUtente utente) {
        utenteLoggato = utente;
    }


    public ProfiloUtente getAccount(String eMail)throws AuthenticationException {

        return CatalogoUtenti.getInstance().trovaUtenteByEmail(eMail);
    }

    public int calcolaNumEventiPartecipati(){

        return utenteLoggato.calcolaNumEventiPartecipati();
    }

    private List<Biglietto> getStoricoBiglietti(){
        return utenteLoggato.listaBigliettiAssociati();
    }

    public List<DTO> getStoricoBigliettiDTO() {
        List<Biglietto> listaBiglietti = this.getStoricoBiglietti();

        List<DTO> listaDTO = new ArrayList<>();

        for (Biglietto b : listaBiglietti) {
            DTO dto = new DTO(
                    "biglietto",
                    b.getCodice(),
                    b.getStato().toString(),
                    b.infoEvento().getTitolo(),
                    b.infoEvento().getData().toString(),
                    b.infoEvento().getOraInizio(),
                    b.infoEvento().getLuogo()
            );
            listaDTO.add(dto);
        }

        return listaDTO;
    }



    public String trovaImmagineProfilo()throws ProfileException{

        if(utenteLoggato.trovaImmagineProfilo() != null){
            return utenteLoggato.trovaImmagineProfilo();
        }else{
            throw new ProfileException("Non hai un'immagine associata al profilo");
        }
    }

    public void scaricaBiglietto(DTO bigliettoDTO){
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("Biglietto.pdf"));
            document.open();
            document.add(new Paragraph(bigliettoDTO.toString()));
            document.close();
        } catch (FileNotFoundException _) {
            Logger logger = Logger.getLogger(ControllerGestioneProfilo.class.getName());
            logger.severe("Errore durante la scrittura del file PDF");
        }
    }



    public void setImmagineProfilo(String immagine){
        utenteLoggato.aggiornaImmagine(immagine);
    }





}
