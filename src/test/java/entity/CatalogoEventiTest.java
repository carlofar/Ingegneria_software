package entity;
import dao.EventoDAO;
import org.junit.*;
import java.time.LocalDate;
import static org.junit.Assert.*;
public class CatalogoEventiTest {


    private CatalogoEventi catalogoeventiTEST;
    private Evento eventoProva1 = new Evento("Caparezza", "concerto", LocalDate.of(2025, 6, 15), "21:00", "Ex base nato", 50.00F, 50000);
    private Evento eventoProva2 = new Evento("Marco Mengoni", "concerto", LocalDate.of(2025, 7, 26), "21:00", "Stadio Maradona", 70.00F, 500000);
    private Evento eventoOggi= new Evento("Fabri Fibra","concerto",LocalDate.of(2025,07,13),"21:00","Stadio Olimpico",50.00F,50000);
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
        catalogoeventiTEST = CatalogoEventi.getInstance();
        //cambio gli id degli eventi perché non conosco l'id senza che venga salvato nel database
        eventoProva1.setId("CAPAREZZA-7-0-9");
        eventoProva2.setId("MENGONI-1-2-3");
        eventoOggi.setId("FABRIFIBRA-1-2-3");
    }

    @After
    public void tearDown() throws Exception {


    }



    @Test
    public void getInstance() {
        assertNotNull("L'istanza di CatalogoEventi non dovrebbe essere null", CatalogoEventi.getInstance());
    }

    @Test
    public void aggiungiEvento() {
        //da vedere perché aggiungievento lo aggiungi solo nella variabile privata non nel database, vedere quando viene chiamato il dao per aggiungerlo nel db
        catalogoeventiTEST.aggiungiEvento(eventoProva1);
        assertNotNull("Evento non aggiunto con successo",catalogoeventiTEST.getEvento("CAPAREZZA-7-0-9"));

    }

    @Test
    public void getEventiAcquistabili() {
        //catalogoeventiTEST.aggiungiEvento(eventoProva2); commento perché voglio che testi tutte le righe di codice
        assertNotNull("La lista di eventi acquistabili non dovrebbe essere null", catalogoeventiTEST.getEventiAcquistabili());

    }

    @Test
    public void getEventiOdierni() {
        assertNotNull("La lista di eventi acquistabili non dovrebbe essere null", catalogoeventiTEST.getEventiOdierni());
        //bisogna mettere degli assert per controllare il contenuto vero e proprio, ricorda che un giorno potresti non avere nel db

    }

    @Test
    public void filtraPerData() {
        LocalDate data = LocalDate.of(2025, 6, 15);
        assertNotNull("La lista di eventi filtrati per data non dovrebbe essere null", catalogoeventiTEST.filtraPerData(data));

    }

    @Test
    public void filtraPerLuogo() {
        assertNotNull("la lista di eventi non deve essere vuota",catalogoeventiTEST.filtraPerLuogo("Ex base nato"));

    }

    @Test
    public void getEvento() {
        assertNotNull("non deve essere null",catalogoeventiTEST.getEvento("FABRIFIBRA-1-2-3"));

    }
}