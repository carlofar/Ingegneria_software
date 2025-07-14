package entity;
import static org.junit.Assert.*;
import org.junit.*;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

import dao.BigliettoDAO;
import dao.EventoDAO;
import utilities.TicketException;


public class BigliettoTest {

    private Biglietto bigliettoTB; // TB sta per Test Biglietto
    ProfiloUtente christian = new ProfiloUtente("Christian", "De Sica", "chrisde@cinepanettoni.it", "VacanzeDiNatal&", ProfiloUtente.Ruolo.UTENTE);
    Evento MerryChristian = new Evento("Merry Christian", "Show di Natale di Christian De Sica", LocalDate.of(2025, 12, 25), "20:00", "Teatro Augusteo", 25.0f, 1000);

    private static final BigliettoDAO bigliettoDAO = new BigliettoDAO();
    private static final EventoDAO EVENTODAO = new EventoDAO();



    @Before
    public void setUp() throws Exception {
        try {
            MerryChristian.setId("EV-7e3--1295781447");
            bigliettoTB = new Biglietto("EAV-52452435", christian, MerryChristian);
        } catch (Exception e) {
            fail("Errore durante l'inizializzazione del biglietto: " + e.getMessage());
        }
    }
    @After
    public void tearDown() throws Exception {
        bigliettoTB.setStato(Biglietto.Stato.VALIDO); // resetto lo stato del biglietto per i test successivi
    }

    @Test
    public void salvaBigliettoDAO() {
        try {
            bigliettoTB.salvaBigliettoDAO();
            Biglietto bigliettors = bigliettoDAO.trovaBigliettoByCodice(bigliettoTB.getCodice());
            assertEquals(bigliettors, bigliettoTB);
        } catch (TicketException e) {
            //Nel DB è già presente il biglietto
            fail("Errore durante il salvataggio del biglietto: " + e.getMessage());
        }
    }

    @Test
    public void getCodice() {
        assertEquals("EAV-52452435", bigliettoTB.getCodice());
    }

    @Test
    public void getStato() {
        assertEquals(Biglietto.Stato.VALIDO, bigliettoTB.getStato()); // se da errore nel frattempo è stato consumato

    }

    @Test
    public void infoProprietario() {
        assertEquals(christian, bigliettoTB.infoProprietario());
    }

    @Test
    public void infoEvento() {
        assertEquals(MerryChristian, bigliettoTB.infoEvento());
    }

    @Test
    public void setStato() {
        Biglietto bigliettoLocal = new Biglietto();
        bigliettoLocal.setStato(Biglietto.Stato.CONSUMATO);
        assertEquals(Biglietto.Stato.CONSUMATO, bigliettoLocal.getStato());
    }

    @Test
    public void setProprietario() {
        ProfiloUtente proprietarioTestLocal = new ProfiloUtente();
        proprietarioTestLocal.setNome("Test");
        assertEquals("Test", proprietarioTestLocal.getNome());
    }

    @Test
    public void setCodice() {
        Biglietto bigliettoLocal = new Biglietto();
        bigliettoLocal.setCodice("EAV-12345678");
        assertEquals("EAV-12345678", bigliettoLocal.getCodice());
    }

    @Test
    public void setEvento() {
        Evento eventoTestLocal = new Evento("Evento Test", "Descrizione Test", LocalDate.of(2025, 1, 1), "10:00", "Teatro Test", 20.0f, 500);
        Biglietto bigliettoLocal = new Biglietto();
        bigliettoLocal.setEvento(eventoTestLocal);
        assertEquals(eventoTestLocal, bigliettoLocal.infoEvento());
    }

    @Test
    public void verifificaAccesso() throws Exception {
        //ricordiamo che questo è il biglietto, che contiene anche le informazioni sull'evento

        assertTrue(bigliettoTB.verificaAccesso(MerryChristian));

    }

    @Test
    public void marcaComeConsumato() {
        Biglietto bigliettoLocal = new Biglietto();
        bigliettoLocal.marcaComeConsumato();
        assertEquals(Biglietto.Stato.CONSUMATO, bigliettoLocal.getStato());
    }

    @Test
    public void testEquals() {
    }

    @Test
    public void testToString() {
    }
}