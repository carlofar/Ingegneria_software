package entity;

import java.sql.Date;
import java.time.LocalDate;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
//    Biglietto b = new Biglietto("123456789",null,null);
//    System.out.println(b.toString());
//
//    Evento e = new Evento("Evento","Descrizione",null,null,null,10,100);
//    e.aggiungiPartecipante();
//
//    ProfiloUtente profilo = new ProfiloUtente("nome","cognome","email","password",ProfiloUtente.Ruolo.UTENTE);
//    profilo.setImmagine("sada");

        String orario = "12:16";
        char a = orario.charAt(2);
        System.out.println(a);
        String ora = orario.substring(0,2);

    }


}