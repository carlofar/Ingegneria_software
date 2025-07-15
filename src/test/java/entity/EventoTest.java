package entity;

import org.junit.*;
import utilities.TicketException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class EventoTest {

    private List<Biglietto> listaBiglietti = new ArrayList<>(); // ci serve per test
    private static LocalDate data; // data di esempio per i test
    private static Evento eventoProvaLocal;
    private static Evento eventoProva;// dichiaro private per usarlo solo qui dentr
    private static ProfiloUtente profilo;


    private static Biglietto biglietto;


    @BeforeClass // annotazione di JUnit
    public static void setUpClass() {
        data= LocalDate.of(2025, 6, 15);
        eventoProvaLocal = new Evento();
        eventoProva = new Evento("Caparezza","concerto",data,"21:00","Ex base nato",50.00F,50000);
        profilo = new ProfiloUtente("Genny", "De Rosa", "genny.derosa03@gmail.com", "Genny@23", ProfiloUtente.Ruolo.UTENTE);
        biglietto= new Biglietto("EVT-11-UT147258369-s3t4u", profilo, eventoProva);
        //eventoProva.setId("EVENTOPROVA-1-1-1");
        CatalogoEventi.getInstance().aggiungiEvento(eventoProva);
        CatalogoUtenti.getInstance().aggiungiProfilo(profilo);
    }

    @AfterClass
    public static void tearDownClass() {

        CatalogoUtenti.getInstance().cancellaUtente(profilo);
        biglietto.cancellaBigliettoDAO();
        CatalogoEventi.getInstance().cancellaEvento(eventoProva);
        CatalogoEventi.getInstance().cancellaEvento(eventoProvaLocal);
    }


    @Before
    public void setUp() throws Exception {
        // inizializzo un oggetto Evento per i test

        // per il float uso 50.0f per indicare che è un float
       // Biglietto bigliettolocal = new Biglietto("EAV-5245243", profilo, eventoProva);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void verificaDisponibilita() {
        // Test per verificare se l'evento è disponibile per nuovi partecipanti
        assertTrue("L'evento dovrebbe essere disponibile per nuovi partecipanti", eventoProva.verificaDisponibilita());
    }

    @Test
    public void aggiungiPartecipante() {
        // Test per verificare se l'aggiunta di un partecipante incrementa il numero attuale
        int numPartecipantiIniziali = eventoProva.getNumPartecipantiAttuali();
        eventoProva.aggiungiPartecipante();

    }

    @Test
    public void aggiungiBiglietto() {
        int nBigliettiIniziali = eventoProva.getNumPartecipantiTotali();
        eventoProva.aggiungiBiglietto(biglietto);
        assertEquals(nBigliettiIniziali + 1, eventoProva.getNumPartecipantiTotali());
    }

    @Test
    public void getId() {
        eventoProvaLocal.setId("EVENTOPROVA-1-1-1");
        assertEquals(eventoProvaLocal.getId(), "EVENTOPROVA-1-1-1");
    }

    @Test
    public void setId(){
        eventoProvaLocal.setId("1");
        assertEquals("1", eventoProvaLocal.getId());
    }

    @Test
    public void getNumPartecipantiTotali() {
        //Test per verificare il numero totale di partecipanti
        int numIniziale = eventoProva.getNumPartecipantiTotali();
        eventoProva.aggiungiBiglietto(biglietto);
        assertEquals("Il numero totale di partecipanti dovrebbe essere 0 inizialmente", numIniziale + 1, eventoProva.getNumPartecipantiTotali());
    }

    @Test
    public void getTitolo() {
        // Test per verificare il titolo dell'evento
        assertEquals("Il titolo dell'evento dovrebbe essere 'Caparazza'", "Caparezza", eventoProva.getTitolo());
    }

    @Test
    public void getData() {
        // Test per verificare la data dell'evento
        LocalDate data = eventoProva.getData();
        assertEquals("La data dell'evento dovrebbe essere 2025-06-15", LocalDate.of(2025, 6, 15), data);
    }

    @Test
    public void getOraInizio() {
        // Test per verificare l'ora di inizio dell'evento
        assertEquals("L'ora di inizio dell'evento dovrebbe essere '20:00'", "21:00", eventoProva.getOraInizio());
    }

    @Test
    public void getLuogo() {
        assertEquals("Stadio Olimpico, Roma", "Ex base nato", eventoProva.getLuogo());
    }

    @Test
    public void getCosto() {
        // Test per verificare il costo dell'evento
        float costo = eventoProva.getCosto();
        assertEquals("Il costo dell'evento dovrebbe essere 50.00", 50.00f, costo, 0.01);
    }

    @Test
    public void getMaxPartecipanti() {
        assertEquals(50000, eventoProva.getMaxPartecipanti());
    }

    @Test
    public void getNumPartecipantiAttuali() {
        // Test per verificare il numero attuale di partecipanti
        int numPartecipantiAttuali = eventoProva.getNumPartecipantiAttuali();
        assertEquals(0, numPartecipantiAttuali);
    }

/*
    @Test
    public void listaBigliettiAssociati() {
        // Test per verificare la lista dei biglietti associati all'evento
        // assertNotNull(eventoProva.listaBigliettiAssociati());
        // assertTrue(eventoProva.listaBigliettiAssociati().isEmpty());
        eventoProva.aggiungiBiglietto(biglietto);
        assertFalse(eventoProva.listaBigliettiAssociati().isEmpty());
        assertTrue(eventoProva.listaBigliettiAssociati().contains(biglietto));
    }
*/

    @Test
    public void getDescrizione() {
        assertEquals("concerto", eventoProva.getDescrizione());
    }

    @Test
    public void setTitolo() {
        eventoProvaLocal.setTitolo("Vasco Live 2025");
        assertEquals("Vasco Live 2025", eventoProvaLocal.getTitolo());
    }

    @Test
    public void setDescrizione() {
        eventoProvaLocal.setDescrizione("Vasco Live tour 2025 torna finalmente negli stadi!");
        assertEquals("Vasco Live tour 2025 torna finalmente negli stadi!", eventoProvaLocal.getDescrizione());
    }

    @Test
    public void setData() {
        eventoProvaLocal.setData(LocalDate.of(2025, 6, 15));
        assertEquals(LocalDate.of(2025, 6, 15), eventoProvaLocal.getData());
    }

    @Test
    public void setOraInizio() {
        eventoProvaLocal.setOraInizio("20:00");
        assertEquals("20:00", eventoProvaLocal.getOraInizio());
    }

    @Test
    public void setLuogo() {
        eventoProvaLocal.setLuogo("Stadio Diego Armando Maradona, Napoli");
        assertEquals("Stadio Diego Armando Maradona, Napoli", eventoProvaLocal.getLuogo());
    }

    @Test
    public void setMaxPartecipanti() {
        eventoProvaLocal.setMaxPartecipanti(60000);
        assertEquals(60000, eventoProvaLocal.getMaxPartecipanti());
    }

    @Test
    public void setCosto() {
        eventoProvaLocal.setCosto(60.0f);
        assertEquals(60.0f, eventoProvaLocal.getCosto(), 0.01);
    }

    @Test
    public void setNumPartecipantiAttuali() {
        eventoProvaLocal.setNumPartecipantiAttuali(100);
        assertEquals(100, eventoProvaLocal.getNumPartecipantiAttuali());
    }

    @Test
    public void testEquals() {
        Evento evento1 = new Evento("Evento Test", "Descrizione Test", LocalDate.of(2025, 6, 15), "20:00", "Luogo Test", 50.0f, 50000);
        Evento evento2 = new Evento("Evento Test", "Descrizione Test", LocalDate.of(2025, 6, 15), "20:00", "Luogo Test", 50.0f, 50000);
        assertEquals(evento1, evento2);
    }

    @Test
    public void testToString() {
        String expectedString = "Titolo: Caparezza,  Descrizione: concerto,  Data: 15/06/2025, 21:00,  Luogo: Ex base nato,  Costo: 50.0"+ "\n";

        assertEquals(expectedString, eventoProva.toString());
    }

    @Test
    public void trovaBiglietto() {
        //eventoProva.setId("EVENTOPROVA-1-1-1");
        try{
            biglietto.salvaBigliettoDAO();
            assertEquals(biglietto, eventoProva.trovaBiglietto(biglietto.getCodice()));
        } catch (TicketException exception){
            fail("Il biglietto è già stato salvato " + exception.getMessage() );
        }


    }

/*
    @Test
    public void aggiornaEntrataDAO() {
    }*/
}