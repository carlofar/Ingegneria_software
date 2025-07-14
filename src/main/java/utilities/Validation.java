package utilities;

public class Validation {

    private Validation(){}

    public static boolean isEmailValid(String email){
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    public static boolean isValidPassword(char[] password) {
        if (password == null || password.length == 0) {
            return false; // Stringa vuota
        }

        boolean hasDigit = false;
        boolean hasSpecial = false;
        boolean allAlphanumeric = true;

        for (char c : password) {
            if (Character.isDigit(c)) {
                hasDigit = true;
            }

            if (!Character.isLetterOrDigit(c)) {
                hasSpecial = true;
            }

            if (!Character.isLetterOrDigit(c)) {
                allAlphanumeric = false;
            }
        }

        if (!hasDigit) {
            return false; // Nessun numero
        }

        if (!hasSpecial) {
            return false; // Nessun carattere speciale
        }

        if (allAlphanumeric && password.length < 10) {
            return false; // Solo alfanumerici e troppo corta
        }

        return true; // Tutte le condizioni rispettate
    }

    public static boolean isValidName(String name){
        return !name.isEmpty() && name.length() <= 35;
    }

    public static boolean isValidSurname(String surname){
        return !surname.isEmpty() && surname.length() <= 35;
    }

    public static boolean isValidImageExtension(String filename) {
        return filename.toLowerCase().matches(".*\\.(jpg|jpeg|png|webp)$");
    }

    public static boolean isValidCodiceBiglietto(String str) {
        // Condizione 1: almeno 3 trattini '-'
        long countTrattini = str.chars().filter(c -> c == '-').count();
        if (countTrattini < 3) {
            return false; // [ERROR] meno di 3 trattini
        }

        // Condizione 2: deve contenere tutte le sottostringhe specificate
        if (!str.contains("EVT-") || !str.contains("-UT")) {
            return false; // [ERROR] manca EVT- o -UT
        }

        return true;
    }





}
