package controller;
import dto.DTO;
import entity.*;
import utilities.PaymentException;
import utilities.TicketException;

import javax.naming.AuthenticationException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;



public class ControllerGestioneAcquisto {


    private final SistemaPOS sistemaPOS = new SistemaPOS();

    private static ControllerGestioneAcquisto instance;

    private ControllerGestioneAcquisto(){}

    public static ControllerGestioneAcquisto getInstance(){
        if (instance == null){
            ControllerGestioneAcquisto.instance = new ControllerGestioneAcquisto();
        }
        return instance;
    }

    private void verificaDisponibilita(Evento e)throws TicketException{
        if (!e.verificaDisponibilita()){
            throw new TicketException("L'evento non ha più posti disponibili");
        }
    }

    public void verificaDisponibilita(DTO eventoDTO)throws TicketException{
        Evento evento = getEventoFromDTO(eventoDTO);
        verificaDisponibilita(evento);
    }


    private void acquistaBiglietto(ProfiloUtente p,Evento e)throws PaymentException, TicketException {

        if (!p.trovaBiglietto(e)) {

            throw new TicketException("Per quest'evento hai già acquistato un biglietto");
            //CONTROLLO:
            //L'EVENTO SELEZIONATO NON PUO' ESSERE ACQUISTATO DALL'UTENTE SE GIA' LO HA FATTO
            //RICERCARE EVENTUALI BIGLIETTI ACQUISTATI PER QUELL'EVENTO
        }    //
        if (sistemaPOS.autorizzaPagamento(p,e.getCosto())){
            Biglietto b = generaBiglietto(p,e);
            p.aggiungiBiglietto(b);
            b.salvaBigliettoDAO();
            e.aggiungiBiglietto(b);
        }

    }


    private Biglietto generaBiglietto(ProfiloUtente p,Evento e){
        String codice = "EVT-" + e.getId() + "-UT" + p.getEmail().hashCode() + "-" + UUID.randomUUID().toString().substring(0,5);
        return new Biglietto(codice,p,e);
    }

    public Evento getEventoFromDTO(DTO dto) throws TicketException {
        String titolo = dto.getCampoInPos(2);
        String dataStr = dto.getCampoInPos(3);
        String luogo = dto.getCampoInPos(5);
        LocalDate data = LocalDate.parse(dataStr);

        // ricerca evento corrispondente nel catalogo
        List<Evento> eventi = CatalogoEventi.getInstance().getEventiAcquistabili();
        for (Evento evento : eventi) {
            if (evento.getTitolo().equals(titolo) &&
                    evento.getData().equals(data) &&
                    evento.getLuogo().equals(luogo)) {
                return evento;
            }
        }

        throw new TicketException("Evento non trovato.");
    }

    public void processaAcquisto(String mailUtente, DTO eventoDTO) throws TicketException, PaymentException {
        Evento evento = getEventoFromDTO(eventoDTO); // metodo privato interno
        ProfiloUtente utente;
        try {
            utente = ControllerGestioneProfilo.getInstance().getAccount(mailUtente);
        } catch (AuthenticationException e) {
            System.out.println("Errore: " + e.getMessage());
            throw new TicketException("Errore nel login");
        }
        acquistaBiglietto(utente, evento);

    }
}
