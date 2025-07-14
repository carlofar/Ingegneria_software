package entity;

import org.junit.*;
import dao.BigliettoDAO;
import dao.EventoDAO;
import java.time.LocalDate;

import static org.junit.Assert.*;

public class ProfiloUtenteTest {
    private static final BigliettoDAO bigliettoDAO = new BigliettoDAO();
    private static final EventoDAO eventoDAO = new EventoDAO();
    ProfiloUtente profiloLocal = new ProfiloUtente();
    private ProfiloUtente profilo = new ProfiloUtente("Bill", "Gates", "bill@microsoft.com", "micros@ft@fficE0", ProfiloUtente.Ruolo.UTENTE);
    private ProfiloUtente profiloesistente;


    @BeforeClass // annotazione di JUnit
    public static void setUpClass() {
        // Eseguito una volta prima dell'inizio dei test nella classe
        // Inizializza risorse condivise
        // o esegui altre operazioni di setup
    }

    @AfterClass
    public static void tearDownClass() {
        // Eseguito una volta alla fine di tutti i test nella classe
        // Effettua la pulizia delle risorse condivise
        // o esegui altre operazioni di teardown
    }




    @Before
    public void setUp() throws Exception {

        profiloesistente= CatalogoUtenti.getInstance().trovaUtenteByEmail("utente3@example.com");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void setNome() {
        profiloLocal.setNome("Luciano");
        assertEquals("Luciano", profiloLocal.getNome());
    }

    @Test
    public void setCognome() {
        profiloLocal.setCognome("Esposito");
        assertEquals("Esposito", profiloLocal.getCognome());
    }

    @Test
    public void setPassword() {
        profiloLocal.setPassword("UNinaPass555555@@");
        assertEquals("UNinaPass555555@@", profiloLocal.getPassword());
    }

    @Test
    public void setRuolo() {
        profiloLocal.setRuolo("UTENTE");
        assertEquals(ProfiloUtente.Ruolo.UTENTE, profiloLocal.getRuolo());
    }

    @Test
    public void setEmail() {
        profiloLocal.setEmail("lucianoespo@studentiunina.it");
        assertEquals("lucianoespo@studentiunina.it", profiloLocal.getEmail());
    }

    @Test
    public void getNome() {

        assertEquals("Bill",profilo.getNome());

    }

    @Test
    public void getCognome() {
        assertEquals("Gates", profilo.getCognome());
    }

    @Test
    public void getPassword() {
        assertEquals("micros@ft@fficE0", profilo.getPassword());
    }

    @Test
    public void getRuolo() {
        assertEquals("UTENTE", profilo.getRuolo().toString());
    }

    @Test
    public void getEmail() {
        assertEquals("bill@microsoft.com", profilo.getEmail());
    }

    @Test
    public void aggiornaImmagine() {
        profiloLocal.aggiornaImmagine("www.esempio.it/immagine.jpg");
        assertEquals("www.esempio.it/immagine.jpg",profiloLocal.trovaImmagineProfilo());
    }

    @Test
    public void trovaImmagineProfilo() {
        assertNull(profilo.trovaImmagineProfilo());

    }

    @Test
    public void aggiungiBiglietto() {
        // Test per verificare se l'aggiunta di un biglietto funziona correttamente
        Evento DegustazioneVini = CatalogoEventi.getInstance().getEvento("EV-4-4683019");
        Biglietto bigliettoProva = new Biglietto("EAV-12345678", profilo, DegustazioneVini);

        profiloLocal.aggiungiBiglietto(bigliettoProva);
        assertTrue(profiloLocal.listaBigliettiAssociati().contains(bigliettoProva));
    }

    @Test
    public void calcolaNumEventiPartecipati() throws Exception {
        //BISOGNA METTERE DEVI EVENTI DOVE HA PARTECIPATO L'UTENTE


        Biglietto biglietto1 = new Biglietto("BIG-1-123-123", profiloesistente,CatalogoEventi.getInstance().getEvento("MENGONI-1-2-3") );


        biglietto1.marcaComeConsumato();
        profiloesistente.aggiungiBiglietto(biglietto1);

        assertEquals(1, profiloesistente.calcolaNumEventiPartecipati());
    }

    @Test
    public void checkPassword() {
        String PasswordDaVerificare = "micros@ft@fficE0";
        boolean isPasswordCorretta = profilo.checkPassword(PasswordDaVerificare);
        assertTrue("La password dovrebbe essere corretta", isPasswordCorretta);

    }

    @Test
    public void listaBigliettiAssociati() {
        assertNotNull("La lista dei biglietti associati non dovrebbe essere null", profilo.listaBigliettiAssociati());

    }

    @Test
    public void trovaBiglietto() {
        Evento DegustazioneVini = CatalogoEventi.getInstance().getEvento("EV-4-4683019");
        assertTrue(profiloesistente.trovaBiglietto(DegustazioneVini));

    }

    @Test
    public void testEquals() {
        ProfiloUtente profiloutentecopia=new ProfiloUtente("Francesco","Totti","bill@microsoft.com", "Passwordcopia", ProfiloUtente.Ruolo.UTENTE);
        assertTrue(profilo.equals(profiloutentecopia));

    }

    @Test
    public void testToString() {

    }
}