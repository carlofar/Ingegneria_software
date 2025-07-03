package dao;


import entity.Biglietto;
import entity.Evento;
import entity.ProfiloUtente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class BigliettoDAO {

    /*

     */

    public Biglietto trovaBigliettoByCodice(String codice){
        Biglietto b = null;
        String query = "SELECT B.codiceIdentificativo, B.stato, E.titolo, E.descrizione, E.data, E.orario, E.luogo E.costo" +
                        "FROM BIGLIETTO B JOIN EVENTO E ON B.idEvento = E.idEvento" +
                        " WHERE B.codice = ?";

        //effettuare la connessione


        try(Connection conn = ConnectionManager.getInstance().getConn();
            PreparedStatement stmt = conn.prepareStatement(query);
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
                e.setData(rs.getDate("data"));
                e.setOraInizio(rs.getString("orario"));
                e.setLuogo(rs.getString("luogo"));
                e.setCosto(rs.getFloat("costo"));
                b.setEvento(e);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return b;
    }

    public List<Biglietto> getStoricoBiglietti(ProfiloUtente p){
        List<Biglietto> biglietti = new ArrayList<>();
        String query = "SELECT B.codiceIdentificativo, B.stato, E.idEvento, E.titolo, E.descrizione, E.data, E.orario, E.luogo " +
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
                Evento e = new Evento();
                e.setTitolo(rs.getString("titolo"));
                e.setDescrizione(rs.getString("descrizione"));
                e.setData(rs.getDate("data"));
                e.setOraInizio(rs.getString("orario"));
                e.setLuogo(rs.getString("luogo"));
                b.setEvento(e);
                biglietti.add(b);
            }

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return biglietti;
    }


    public void salvaBiglietto(Biglietto b){

        String query = "INSERT INTO Biglietto(codice, stato, idEvento) VALUES (?,?,?)";

        try(Connection conn = ConnectionManager.getInstance().getConn();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, b.getCodice());
            stmt.setString(2, b.getStato().name());

            //Supponiamo evento già noto.
            int eventoId = b.getEvento().getId();
            stmt.setInt(3, eventoId);

            stmt.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }


    }


    public void aggiornaBiglietto( Biglietto b){

        String query = "UPDATE Biglietto SET stato = ? WHERE codice = ?";
        try(Connection conn=ConnectionManager.getInstance().getConn();
            PreparedStatement stmt=conn.prepareStatement(query)){
            stmt.setString(1, b.getStato().name());
            stmt.setString(2, b.getCodice());
            stmt.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();


        }
    }

}
