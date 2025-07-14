package dao;

import entity.Biglietto;
import entity.Evento;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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

    private static final String ERROR_MESSAGE = "Errore nella query";




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
                    LocalDate data = rs.getDate("data").toLocalDate();
                    e.setData(data);
                    e.setOraInizio(rs.getString("orario"));
                    e.setLuogo(rs.getString("luogo"));
                    e.setCosto(rs.getFloat("costo"));
                    e.setMaxPartecipanti(rs.getInt("maxPartecipanti"));
                    e.setNumPartecipantiAttuali(rs.getInt("numPartecipantiAttuali"));
                    eventi.add(e);

                }

        }catch (SQLException _){
            Logger logger = Logger.getLogger(BigliettoDAO.class.getName());
            logger.severe(ERROR_MESSAGE);
        }

        return eventi;
    }


    // METODO DI AGGIORNAMENTO DEL DB IN CASO DI CREAZIONE DI UN EVENTO DA PARTE DELL'AMMINISTRATORE
    // METODO EFFETTUATO SOLO PER INSERIRE MANUALMENTE IN DB GLI EVENTI
    public void salvaEvento(Evento evento){
        String query = "INSERT INTO EVENTO(idEvento, TITOLO, DESCRIZIONE, DATA, ORARIO, LUOGO, COSTO, MAXPARTECIPANTI) VALUES (?,?,?,?,?,?,?,?)";
        try(Connection conn = ConnectionManager.getInstance().getConn();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, evento.getId());
            stmt.setString(2, evento.getTitolo());
            stmt.setString(3, evento.getDescrizione());
            Date sqlDate = Date.valueOf(evento.getData());
            stmt.setDate(4, sqlDate);
            stmt.setString(5, evento.getOraInizio());
            stmt.setString(6, evento.getLuogo());
            stmt.setFloat(7, evento.getCosto());
            stmt.setInt(8, evento.getMaxPartecipanti());

            stmt.executeUpdate();


        }catch (SQLException _){
            Logger logger = Logger.getLogger(BigliettoDAO.class.getName());
            logger.severe(ERROR_MESSAGE);
        }
    }

    public void aggiornaEntrata(Evento evento){

        String query = "UPDATE evento SET numPartecipantiAttuali  = ? WHERE idEvento = ?";

        try (Connection conn = ConnectionManager.getInstance().getConn();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setInt(1, evento.getNumPartecipantiAttuali());
            stmt.setString(2, evento.getId());

            stmt.executeUpdate();

        }catch(SQLException _){
            Logger logger = Logger.getLogger(BigliettoDAO.class.getName());
            logger.severe(ERROR_MESSAGE);
        }

    }

    
//  public List<Evento> filtraPerData(LocalDate data){
//        List<Evento> eventi = new ArrayList<>();
//        Date dataSQL = Date.valueOf(data);
//        String query = "SELECT * FROM EVENTO WHERE data = ?";
//        try(Connection conn = ConnectionManager.getInstance().getConn();
//            PreparedStatement stmt = conn.prepareStatement(query)){
//            stmt.setDate(1, dataSQL);
//
//            ResultSet rs = stmt.executeQuery();
//
//            while (rs.next()){
//                LocalDate dataEvento = rs.getDate("data").toLocalDate();
//                Evento e = new Evento(
//                        rs.getString("titolo"),
//                        rs.getString("descrizione"),
//                        dataEvento,
//                        rs.getString("orario"),
//                        rs.getString("luogo"),
//                        rs.getFloat("costo"),
//                        rs.getInt("maxPartecipanti"));
//                eventi.add(e);
//            }
//
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
//
//        return eventi;
//    }

    //METODO SUPERFLUO
//    private List<Evento> filtraPerLuogo(String luogo){
//        List<Evento> eventi = new ArrayList<>();
//        String query = "SELECT* FROM EVENTO WHERE luogo = ?";
//        try(Connection conn = ConnectionManager.getInstance().getConn();
//            PreparedStatement stmt = conn.prepareStatement(query)){
//
//            stmt.setString(1, luogo);
//
//            ResultSet rs = stmt.executeQuery();
//
//            if (rs.next()){
//                LocalDate dataEvento = rs.getDate("data").toLocalDate();
//                Evento e = new Evento(
//                        rs.getString("titolo"),
//                        rs.getString("descrizione"),
//                        dataEvento,
//                        rs.getString("orario"),
//                        rs.getString("luogo"),
//                        rs.getFloat("costo"),
//                        rs.getInt("maxPartecipanti"));
//                eventi.add(e);
//            }
//
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
//
//        return eventi;
//    }


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

        } catch (SQLException _) {
            Logger logger = Logger.getLogger(BigliettoDAO.class.getName());
            logger.severe(ERROR_MESSAGE);
        }

        return biglietti;
    }
}
