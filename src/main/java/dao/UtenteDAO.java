package dao;

import entity.ProfiloUtente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;


public class UtenteDAO {


    //DELETE * FROM [NOMETABELLA] WHERE [CONDIZIONE]
    //"DELETE * FROM PROFILOUTENTE WHERE eMail = ?"
    private static final String ERROR_MESSAGE = "Errore nella query";

    public ProfiloUtente trovaUtenteByEmail(String eMail){
        //Obiettivo: recuperare un ProfiloUtente dal DB dato il suo indirizzo eMail

        //PASSI DA SEGUIRE

        //1. Creo la query SQL con i parametri ? = PARAMETRO/PARAMETRI DI INGRESSO
        String query = "SELECT * FROM PROFILOUTENTE WHERE EMAIL = ?";

        //2.Apro la connessione tramite la classe <<singleton>> ConnectionManager
        try(Connection conn = ConnectionManager.getInstance().getConn();
            //"Preparo" la query con un prepareStatement, impostando il parametro eMail
            /*Metodo prepareStatement:
            * Crea un oggetto PreparedStatement per l'invio di istruzioni SQL parametrizzate al database.
            * Un'istruzione SQL con o senza parametri IN può essere precompilata e archiviata in un oggetto PreparedStatement.
            * Questo oggetto può quindi essere utilizzato per eseguire in modo efficiente questa istruzione più volte.
            * */
            PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1,eMail);

            //Eseguo la query ed ottengo un ResultSet
            ResultSet rs = stmt.executeQuery();
            //RESULT SET:
            //Se il ResultSet ha un risultato (rs.next()),
            //creo un oggetto ProfiloUtetne e popolo tutti i campo del DB
            //DB = DATI PERSISTENTI == MEMORIA DI MASSA
            //SISTEMA = DATI VOLATILI == RAM
            if(rs.next()){
                return new ProfiloUtente(
                        rs.getString("NOME"),
                        rs.getString("COGNOME"),
                        rs.getString("EMAIL"),
                        rs.getString("PASSWORD"),
                        ProfiloUtente.Ruolo.valueOf(rs.getString("RUOLO")));
            }
            //Poichè rs.next() puo lanciare un'eccezione, andiamo a catcharla
            //Si potrebbe fare di meglio
        } catch (SQLException _) {
            Logger logger = Logger.getLogger(BigliettoDAO.class.getName());
            logger.severe(ERROR_MESSAGE);
        }
        return null;

    }

    public void aggiornaProfilo(ProfiloUtente utente){
        String sql = "UPDATE PROFILOUTENTE SET immagineProfilo = ? WHERE EMAIL = ?";
        try(Connection conn = ConnectionManager.getInstance().getConn();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1,utente.trovaImmagineProfilo());
            stmt.setString(2,utente.getEmail());
            stmt.executeUpdate();
        } catch (SQLException _) {
            Logger logger = Logger.getLogger(BigliettoDAO.class.getName());
            logger.severe(ERROR_MESSAGE);
        }
    }

    public void salvaUtente(ProfiloUtente utente){
        //Obiettivo: Inserire un nuovo utente nella tabella ProfiloUtente

        //pasi da seguire:
        //1. Creo la query SQL con i parametri
        String query = "INSERT INTO PROFILOUTENTE(email, nome, cognome, password, ruolo, immagineProfilo) VALUES (?,?,?,?,?,?)";
        //2. Apro la connessione tramite la classe <<singleton>> ConnectionManager
        //NB.QUESTO è UN TRY-WITH-RESOURCES
        try (Connection conn = ConnectionManager.getInstance().getConn();
             PreparedStatement stmt = conn.prepareStatement(query)){
                 //3.Preparo lo statement e setto i valori
                    stmt.setString(1, utente.getEmail());
                    stmt.setString(2, utente.getNome());
                    stmt.setString(3, utente.getCognome());
                    stmt.setString(4, utente.getPassword());
                    stmt.setString(5, utente.getRuolo().toString());
                    stmt.setString(6, utente.trovaImmagineProfilo());
                    //4.Eseguo la query
                    stmt.executeUpdate();
                    //TECNICAMENTE MANCA ANCHE IMMAGINE PROFILO, MA POI NE DISCUTIAMO
        }catch (SQLException _){
            Logger logger = Logger.getLogger(BigliettoDAO.class.getName());
            logger.severe(ERROR_MESSAGE);
        }
    }


    public String getImmagineProfilo(String email){
        String query = "SELECT immagineProfilo FROM ProfiloUtente WHERE email = ?";
        try(Connection conn = ConnectionManager.getInstance().getConn();
            PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return rs.getString("immagineProfilo");
            }
        }catch (SQLException _){
            Logger logger = Logger.getLogger(BigliettoDAO.class.getName());
            logger.severe(ERROR_MESSAGE);
        }
        return null;
    }


    public void cancellaUtente(ProfiloUtente utente){
        String query = "DELETE FROM PROFILOUTENTE WHERE email = ?";
        try(Connection conn = ConnectionManager.getInstance().getConn();
            PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1, utente.getEmail());
            stmt.executeUpdate();
        }catch (SQLException _){
            Logger logger = Logger.getLogger(BigliettoDAO.class.getName());
        }
    }


}





