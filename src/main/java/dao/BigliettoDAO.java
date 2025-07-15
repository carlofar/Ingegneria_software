package dao;


import entity.Biglietto;
import entity.Evento;
import entity.ProfiloUtente;
import utilities.TicketException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;



public class BigliettoDAO {

    private static final String ERROR_MESSAGE = "Errore nella query";

    public Biglietto trovaBigliettoByCodice(String codice){
        Biglietto b = null;
        String query = " SELECT B.codiceIdentificativo, B.stato, E.titolo, E.descrizione, E.data, E.orario, E.luogo, E.costo " +
                        " FROM BIGLIETTO B JOIN EVENTO E ON B.idEvento = E.idEvento " +
                        " WHERE B.codiceIdentificativo = ?";

        //effettuare la connessione


        try(Connection conn = ConnectionManager.getInstance().getConn();
            PreparedStatement stmt = conn.prepareStatement(query)
        ){
            stmt.setString(1, codice);
            ResultSet rs = stmt.executeQuery();
            //HP: ABBIAMO MESSO ALL'INTERNO DEL SELECT QUELLO CHE VOGLIAMO
            //titolo, descrizione, data, orario, luogo
            //DATI PERSISTENTI == MEMORIA DI MASSA
            //PROGRAMMA CORRENTE == RAM
            if(rs.next()){
                b = new Biglietto();
                b.setCodice(rs.getString("codiceIdentificativo"));
                b.setStato(Enum.valueOf(Biglietto.Stato.class, rs.getString("stato")));
                Evento e = new Evento();
                e.setTitolo(rs.getString("titolo"));
                e.setDescrizione(rs.getString("descrizione"));
                LocalDate data = rs.getDate("data").toLocalDate();
                e.setData(data);
                e.setOraInizio(rs.getString("orario"));
                e.setLuogo(rs.getString("luogo"));
                e.setCosto(rs.getFloat("costo"));
                b.setEvento(e);
            }

        }catch (SQLException _){
            Logger logger = Logger.getLogger(BigliettoDAO.class.getName());
            logger.severe(ERROR_MESSAGE);
        }
        return b;
    }

    public List<Biglietto> getStoricoBiglietti(ProfiloUtente p){
        List<Biglietto> biglietti = new ArrayList<>();
        String query = "SELECT B.codiceIdentificativo, B.stato, B.utenteEmail, E.idEvento, E.titolo, E.descrizione, E.data, E.orario, E.luogo , E.costo " +
                "FROM BIGLIETTO B JOIN EVENTO E ON B.idEvento = E.idEvento " +
                "WHERE B.utenteEmail = ?";
        try(Connection conn=ConnectionManager.getInstance().getConn();
        PreparedStatement stmt=conn.prepareStatement(query)){
            stmt.setString(1, p.getEmail());
            ResultSet rs=stmt.executeQuery();
            //Cosa mi restituiesce la query?
            //cosa devo fare con questi dati?

            while(rs.next()){
                Biglietto b=new Biglietto();
                b.setCodice(rs.getString("codiceIdentificativo"));
                b.setStato(Enum.valueOf(Biglietto.Stato.class, rs.getString("stato")));
                b.setProprietario(p);
                Evento e = new Evento();
                e.setId(rs.getString("idEvento"));
                e.setTitolo(rs.getString("titolo"));
                e.setDescrizione(rs.getString("descrizione"));
                LocalDate data = rs.getDate("data").toLocalDate();
                e.setData(data);
                e.setOraInizio(rs.getString("orario"));
                e.setLuogo(rs.getString("luogo"));
                e.setCosto(rs.getFloat("costo"));
                b.setEvento(e);
                biglietti.add(b);
            }

        }
        catch (SQLException _){
            Logger logger = Logger.getLogger(BigliettoDAO.class.getName());
            logger.severe(ERROR_MESSAGE);
        }
        return biglietti;
    }


    public void salvaBiglietto(Biglietto b) throws TicketException {

        String query = "INSERT INTO Biglietto(codiceIdentificativo, stato, utenteEmail, idEvento ) VALUES (?,?,?,?)";

        try(Connection conn = ConnectionManager.getInstance().getConn();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, b.getCodice());
            stmt.setString(2, b.getStato().name());
            stmt.setString(3, b.infoProprietario().getEmail());
            String eventoId = b.infoEvento().getId();
            stmt.setString(4, eventoId);

            stmt.executeUpdate();

        }catch (SQLException _){
            throw new TicketException("Errore nel salvataggio del biglietto");
        }


    }


    public ProfiloUtente getProprietario(Biglietto b){

        String query = "SELECT P.eMail,P.Nome, P.Cognome, P.password, P.Ruolo, P.immagineProfilo FROM Biglietto B JOIN ProfiloUtente P ON P.eMail=B.utenteEMail " +
                "WHERE B.codiceIdentificativo = ?";
        try(Connection conn=ConnectionManager.getInstance().getConn();
            PreparedStatement stmt=conn.prepareStatement(query)){
            stmt.setString(1, b.getCodice());
            ResultSet rs= stmt.executeQuery();
            if(rs.next()){
                return new ProfiloUtente(rs.getString("Nome"),rs.getString("Cognome"),rs.getString("eMail"),
                        rs.getString("password"),ProfiloUtente.Ruolo.valueOf(rs.getString("Ruolo")));

            }

        }
        catch(SQLException _) {
            Logger logger = Logger.getLogger(BigliettoDAO.class.getName());
            logger.severe(ERROR_MESSAGE);
        }
        return null;
    }


    //Cambia lo stato del DB, aggiornando l'attributo stato in CONSUMATO in quanto parte del caso d'uso ControlloAccessi
    public void aggiornaBiglietto( Biglietto b){

        String query = "UPDATE Biglietto SET stato = ? WHERE codiceIdentificativo = ?";
        try(Connection conn=ConnectionManager.getInstance().getConn();
            PreparedStatement stmt=conn.prepareStatement(query)){
            stmt.setString(1, b.getStato().name());
            stmt.setString(2, b.getCodice());
            stmt.executeUpdate();
        }
        catch(SQLException _) {
            Logger logger = Logger.getLogger(BigliettoDAO.class.getName());
            logger.severe(ERROR_MESSAGE);
        }

    }


    public void associaEvento(Biglietto biglietto) {

        String query = "UPDATE Biglietto SET idEvento = ? WHERE codiceIdentificativo = ?";
        try(Connection conn=ConnectionManager.getInstance().getConn();
            PreparedStatement stmt=conn.prepareStatement(query)){
            stmt.setString(1, biglietto.infoEvento().getId());
            stmt.setString(2, biglietto.getCodice());
            stmt.executeUpdate();
        }
        catch(SQLException _) {
            Logger logger = Logger.getLogger(BigliettoDAO.class.getName());
            logger.severe(ERROR_MESSAGE);
        }

    }

    public void cancellaBiglietto(Biglietto b){

        String query = "DELETE FROM Biglietto WHERE codiceIdentificativo = ?";
        try(Connection conn=ConnectionManager.getInstance().getConn();
            PreparedStatement stmt=conn.prepareStatement(query)){
            stmt.setString(1, b.getCodice());
            stmt.executeUpdate();
        }
        catch(SQLException _) {
            Logger logger = Logger.getLogger(BigliettoDAO.class.getName());
        }

    }

}
