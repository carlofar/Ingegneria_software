package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class ConnectionManager {

    //scelgo il driver
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    //Parametri di Connessione al database(Tecnicamente si mettono in un file di comfigurazione esterno, non in chiaro)
    private static final String URL = "jdbc:mysql://localhost:3306/progettois";
    private static final String USER = "root";
    private static final String PASSWORD = "prova";

    //instance rappresenta l'unica istanza della classe secondo il pattern singleton
    private static ConnectionManager instance;
    private Connection conn;


    //Il costruttore privato carica il driver JDBC MySQL(necessario prima di usare DriverManager)
    private ConnectionManager(){
        try{
            Class.forName(DRIVER);
        }catch (ClassNotFoundException e){
            throw new RuntimeException("Driver non trovato",e);
        }
    }

    //Si puo mettere synchronized
    //Metodo statico per ottenere l'unica istanza
    //è il cuore del pattern singleton
    public static ConnectionManager getInstance(){
        if(instance == null){
            instance = new ConnectionManager();
        }
        return instance;
    }


    //Metodo per ottenere la connessione
    //Ogni volta che viene chiamato, restituisce una nuova connessione JDBC
    //Usa il DriverManager, che seleziona il Driver corretto(quello caricato dal costruttore)
    public Connection getConn() throws SQLException{
        return DriverManager.getConnection(URL,USER,PASSWORD);
    }

    public void closeConn()throws SQLException{
            conn.close();
    }
}
