package entity;

import org.junit.*;
import utilities.RegistrationException;

import static org.junit.Assert.*;



import javax.naming.AuthenticationException;
import java.util.List;

public class CatalogoUtentiTest {

    private CatalogoUtenti catalogoutenti = CatalogoUtenti.getInstance();

    ProfiloUtente profiloSteve = new ProfiloUtente("Steve", "Jobs", "stevejobs@apple.com", "@ppleThinkDifferent", ProfiloUtente.Ruolo.UTENTE);
    ProfiloUtente profiloAnna = new ProfiloUtente("Annarita", "Fasolino", "an.fasolino@unina.it", "#INGsoft25.30L", ProfiloUtente.Ruolo.UTENTE);

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
        //catalogoutenti = CatalogoUtenti.getInstance();
        catalogoutenti.reset();
    }

    @After
    public void tearDown() throws Exception {
        // Pulisce il catalogo utenti dopo ogni test

        catalogoutenti.reset();

    }

    @Test
    public void getInstance() {
        assertNotNull("L'istanza di CatalogoUtenti non deve essere null", CatalogoUtenti.getInstance());
    }

    @Test
    public void aggiungiProfilo() {
        try {
            //ProfiloUtente profilo = new ProfiloUtente("Annarita", "Fasolino", "an.fasolino@unina.it", "#INGsoft25.30L", ProfiloUtente.Ruolo.UTENTE);

            // Aggiungo un profilo utente al catalogo
            catalogoutenti.aggiungiProfilo(profiloAnna);

            // Verifica che il profilo sia stato aggiunto usando trovaUtenteByEmail
            ProfiloUtente utenteAggiunto = catalogoutenti.trovaUtenteByEmail("an.fasolino@unina.it");
            assertNotNull("L'utente dovrebbe essere presente nel catalogo", utenteAggiunto);
            assertEquals("L'email dovrebbe corrispondere", "an.fasolino@unina.it", utenteAggiunto.getEmail());

        } catch (Exception e) {
            fail("Errore durante l'aggiunta del profilo: " + e.getMessage());
        }
    }
/*
    @Test
    public void getListaUtenti() {

        // Aggiungo un profilo utente per testare
        // Abbiamo aggiunto con successo tramite aggiungiProfilo, il profilo di Annarita Fasolino
        // qui andiamo a testare direttamente il metodo getListaUtenti
        // catalogoUtenti.aggiungiProfilo(profiloSteve);
        // Verifica che la lista degli utenti contenga il profilo aggiunto

        // salvo la dimensione inizialie della lista utenti
        int dimensioneIniziale = catalogoutenti.getListaUtenti().size();

        // aggiungo un profilo utente

        catalogoutenti.aggiungiProfilo(profiloSteve);
        // controllo che la dimensione della lista sia aumentata di 1
        List<ProfiloUtente> listaAggiornata = catalogoutenti.getListaUtenti();
        assertEquals(dimensioneIniziale + 1, listaAggiornata.size());
//        assertTrue(listaAggiornata.contains(profiloSteve));
      //  assertEquals(1, listaAggiornata.size());
        assertTrue(listaAggiornata.contains(profiloSteve));
        assertEquals("stevejobs@apple.com", listaAggiornata.get(0).getEmail());

        // List<ProfiloUtente> listaAggiornata = catalogoUtenti.getListaUtenti();
        //assertEquals("La lista degli utenti dovrebbe contenere un profilo", 1, listaAggiornata.size());
       //  assertTrue("La lista contiene l'utente aggiunto", listaAggiornata.contains( ));
        //assertEquals("stevejobs@apple.com", listaAggiornata.get(0).getEmail());
    }
    */



    @Test
    public void getListaUtenti() {

        // Aggiungo un profilo utente per testare
        // Abbiamo aggiunto con successo tramite aggiungiProfilo, il profilo di Annarita Fasolino
        // qui andiamo a testare direttamente il metodo getListaUtenti
        // catalogoUtenti.aggiungiProfilo(profiloSteve);
        // Verifica che la lista degli utenti contenga il profilo aggiunto

        // salvo la dimensione inizialie della lista utenti
        int dimensioneIniziale = catalogoutenti.getListaUtenti().size();
        // aggiungo un profilo utente
        catalogoutenti.aggiungiProfilo(profiloSteve);
        // controllo che la dimensione della lista sia aumentata di 1
        List<ProfiloUtente> listaAggiornata = catalogoutenti.getListaUtenti();
        assertEquals(dimensioneIniziale + 1, listaAggiornata.size());
        assertTrue(listaAggiornata.contains(profiloSteve));

        // List<ProfiloUtente> listaAggiornata = catalogoUtenti.getListaUtenti();
        //assertEquals("La lista degli utenti dovrebbe contenere un profilo", 1, listaAggiornata.size());
        //  assertTrue("La lista contiene l'utente aggiunto", listaAggiornata.contains( ));
        assertEquals("stevejobs@apple.com", listaAggiornata.get(0).getEmail());
    }








    @Test
    public void trovaUtenteByEmail() throws AuthenticationException {
        // Aggiungo un profilo utente per testare
        ProfiloUtente profilotrovato = catalogoutenti.trovaUtenteByEmail("stevejobs@apple.com");

        assertEquals("stevejobs@apple.com", profilotrovato.getEmail());

    }

    @Test
    public void checkUtenteByEmail()  {

        try {
            catalogoutenti.checkUtenteByEmail("an.fasolino@unina.it");
        } catch (RegistrationException e)
        {

            assertEquals("La Email è già presente nel sistema", e.getMessage());
        }
    }


}