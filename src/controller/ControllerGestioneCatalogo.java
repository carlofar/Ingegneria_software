package controller;
import dto.DTO;
import entity.CatalogoEventi;
import entity.Evento;


import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


public class ControllerGestioneCatalogo {

    private static ControllerGestioneCatalogo instance;
    private ControllerGestioneCatalogo(){}
    public static ControllerGestioneCatalogo getInstance(){
        if(instance == null){
            instance = new ControllerGestioneCatalogo();
        }
        return instance;
    }


    private List<Evento> getEventi(){
        return CatalogoEventi.getInstance().getEventiAcquistabili();
    }

//    public Evento getEvento(String id){
//        return CatalogoEventi.getInstance().getEvento(id);
//    }


    private List<Evento> getEventiOdierni(){
        return CatalogoEventi.getInstance().getEventiOdierni();
    }

    private List<Evento> filtraEventiPerData(LocalDate data) {
        return CatalogoEventi.getInstance().filtraPerData(data);
    }

    private List<Evento> filtraEventiPerLuogo(String luogo) {
        return CatalogoEventi.getInstance().filtraPerLuogo(luogo);
    }


//    public List<DTO> getEventiDTO() {
//        List<Evento> eventi = this.getEventi(); // usa il metodo esistente
//        List<DTO> eventiDTO = new ArrayList<>();
//        for (Evento evento : eventi) {
//            DTO dto = new DTO(
//                    evento.getTitolo(),
//                    evento.getData().toString(),
//                    evento.getLuogo(),
//                    String.valueOf(evento.getCosto())
//            );
//            eventiDTO.add(dto);
//        }
//        return eventiDTO;
//    }

    public List<DTO> getEventiDTO() {
        return toDTOList(getEventi());
    }

    public List<DTO> filtraPerDataDTO(LocalDate data) {
        return toDTOList(filtraEventiPerData(data));
    }

    public List<DTO> filtraPerLuogoDTO(String localita) {
        return toDTOList(filtraEventiPerLuogo(localita));
    }

    private List<DTO> toDTOList(List<Evento> eventi) {
        return eventi.stream()
                .map(e -> new DTO("evento",
                        e.getId(),
                        e.getTitolo(),
                        e.getData().toString(),
                        e.getOraInizio(),
                        e.getLuogo(),
                        String.valueOf(e.getCosto())
                ))
                .collect(Collectors.toList());
    }

    public List<DTO> getEventiOdierniDTO() {
        return toDTOList(getEventiOdierni());
    }
}
