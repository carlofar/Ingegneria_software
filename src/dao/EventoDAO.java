package dao;

import entity.Biglietto;
import entity.Evento;
import entity.ProfiloUtente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
titolo varchar(40)
descrizione text
data date
orario time
luogo varchar(150)
costo float
maxPartecipanti int
numPartecipantiAttuali int



 */



public class EventoDAO{






    public List<Evento> getEventi(){
        List<Evento> eventi = new ArrayList<>();
        //1. Creo la query
        String query = "SELECT * FROM EVENTO";
        //2. Apro la connessione tramite ConnectionManager
        try(Connection conn = ConnectionManager.getInstance().getConn();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query)){
            //EVENTO:TITOLO,DESCRIZIONE,DATA,ORARIO,LUOGO,COSTO,MAXPARTECIPANTI,NUMPARTECIPANTIATTUALI
            //COSTRUTTORE: titolo, descrizione, data, oraInizio, luogo,  maxPartecipanti,  costo
                while (rs.next()){
                    Evento e = new Evento();
                    e.setId(rs.getString("idEvento"));
                    e.setTitolo(rs.getString("titolo"));
                    e.setDescrizione(rs.getString("descrizione"));
                    e.setData(rs.getDate("data"));
                    e.setOraInizio(rs.getString("orario"));
                    e.setLuogo(rs.getString("luogo"));
                    e.setCosto(rs.getFloat("costo"));
                    e.setMaxPartecipanti(rs.getInt("maxPartecipanti"));
                    e.setNumPartecipantiAttuali(rs.getInt("numPartecipantiAttuali"));
                    eventi.add(e);
//                    String id = rs.getString("idEvento");
//                    Evento e = new Evento(
//                            rs.getString("titolo"),
//                            rs.getString("descrizione"),
//                            rs.getDate("data"),
//                            rs.getString("orario"),
//                            rs.getString("luogo"),
//                            rs.getFloat("costo"),
//                            rs.getInt("maxPartecipanti"));
//                            rs.getInt("numPartecipantiAttuali");
//
//                    eventi.add(e);
                }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return eventi;
    }


    public void salvaEvento(Evento evento){
        String query = "INSERT INTO EVENTO(TITOLO, DESCRIZIONE, DATA, ORARIO, LUOGO, COSTO, MAXPARTECIPANTI) VALUES (?,?,?,?,?,?,?)";
        try(Connection conn = ConnectionManager.getInstance().getConn();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, evento.getTitolo());
            stmt.setString(2, evento.getDescrizione());
            Date data = new Date(evento.getData().getTime());
            stmt.setDate(3, data );
            stmt.setString(4, evento.getOraInizio());
            stmt.setString(5, evento.getLuogo());
            stmt.setFloat(6, evento.getCosto());
            stmt.setInt(7, evento.getMaxPartecipanti());

            stmt.executeUpdate();


        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void aggiornaEntrata(Evento evento){

        String query = "UPDATE evento SET numPartecipantiAttuali  = ? WHERE id = ?";

        try (Connection conn = ConnectionManager.getInstance().getConn();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setInt(1, evento.getNumPartecipantiAttuali());
            stmt.setString(2, evento.getId());

            stmt.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    public List<Evento> filtraPerData(java.util.Date data){
        List<Evento> eventi = new ArrayList<>();
        String query = "SELECT* FROM EVENTO WHERE data = ?";
        try(Connection conn = ConnectionManager.getInstance().getConn();
            PreparedStatement stmt = conn.prepareStatement(query)){

            Date d = new Date(data.getTime());
            stmt.setDate(1, d);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()){
                Evento e = new Evento(
                        rs.getString("titolo"),
                        rs.getString("descrizione"),
                        rs.getDate("data"),
                        rs.getString("orario"),
                        rs.getString("luogo"),
                        rs.getFloat("costo"),
                        rs.getInt("maxPartecipanti"));
                eventi.add(e);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return eventi;
    }


    public List<Evento> filtraPerLuogo(String luogo){
        List<Evento> eventi = new ArrayList<>();
        String query = "SELECT* FROM EVENTO WHERE luogo = ?";
        try(Connection conn = ConnectionManager.getInstance().getConn();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, luogo);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()){
                Evento e = new Evento(
                        rs.getString("titolo"),
                        rs.getString("descrizione"),
                        rs.getDate("data"),
                        rs.getString("orario"),
                        rs.getString("luogo"),
                        rs.getFloat("costo"),
                        rs.getInt("maxPartecipanti"));
                eventi.add(e);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return eventi;
    }


    public List<Biglietto> getBigliettiAssociati(Evento evento) {
        List<Biglietto> biglietti = new ArrayList<>();
        String query = "SELECT * FROM BIGLIETTO WHERE idEvento = ?";
        try (Connection conn = ConnectionManager.getInstance().getConn();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, evento.getId());
            stmt.executeQuery();

            ResultSet rs = stmt.getResultSet();
            if (rs.next()) {
                Biglietto b = new Biglietto();
                b.setCodice(rs.getString("codiceIdentificativo"));
                b.setStato(Enum.valueOf(Biglietto.Stato.class, rs.getString("stato")));
                //mancherebbe info sul proprietario
                b.setEvento(evento);
                biglietti.add(b);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return biglietti;
    }
}
