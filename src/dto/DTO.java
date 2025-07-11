package dto;

import java.util.ArrayList;

public class DTO {

    private final ArrayList<String> listaCampi;
    private int dimensione;
    private String tipoDTO;

    /**
     * Costruisce un oggetto DTO avente come listaCampi la conversione in stringhe dei parametri,
     * e come dimensione il numero di campi forniti.
     * Accetta almeno un parametro.
     * @param primoCampo oggetto che funge da primo campo del DTO
     * @param altriCampi oggetti restanti pari agli altri campi del DTO
     */
    public DTO (String tipo, Object primoCampo, Object ...altriCampi) {

        this();
        this.tipoDTO = tipo;
        this.listaCampi.add(String.valueOf(primoCampo));

        for (Object campo : altriCampi) {
            this.listaCampi.add(String.valueOf(campo));
        }

        this.dimensione = this.listaCampi.size();
    }

    public DTO() {

        this.listaCampi = new ArrayList<>();
        this.dimensione = 0;
    }

    /**
     * Restituisce il campo alla posizione specificata come parametro.
     * La posizione deve essere strettamente positiva, e non maggiore della dimensione del DTO.
     * @param pos posizione nel DTO del campo da prelevare
     * @return	campo alla posizione specificata
     */
    public String getCampoInPos(int pos) {
        return listaCampi.get(pos - 1);
    }

    public int getLunghezza() {
        return dimensione;
    }

    @Override
    public String toString() {
//        String ret = "[ ";
//        if (dimensione == 0)
//        {
//            for (int i = 0; i < dimensione; i++) {
//                ret += "campo" + (i + 1) + " = " + listaCampi.get(i) + ", ";
//            }
//        }
//        ret += " ]";
        String rv = "";
        return switch (tipoDTO) {
            case "evento" ->
                    "Evento: " + listaCampi.get(1) + ", Data: " + listaCampi.get(2) + ", " + listaCampi.get(3) + ", Luogo: " + listaCampi.get(4) + ", Costo: " + listaCampi.get(5) + "\n";
            case "utente" ->
                    "ProfiloUtente: " + listaCampi.get(0) + ", Email: " + listaCampi.get(1) + ", Nome: " + listaCampi.get(2) + ", Cognome: " + listaCampi.get(3) + ", Password: " + listaCampi.get(5) + "\n";
            case "biglietto" ->
                    "Biglietto->" + "Codice: " + listaCampi.get(0) + ", Stato: " + listaCampi.get(1) + "   \nEvento Associato: " + listaCampi.get(2) + ", Data: " + listaCampi.get(3) + ", " + listaCampi.get(4) + ", Luogo: " + listaCampi.get(5) + "\n";
            default -> rv;
        };

    }
}

