package entity;

import java.util.List;

public class SistemaPOS {

    float saldo = 200;

    public boolean autorizzaPagamento(ProfiloUtente p, float costo){

        if(saldo < costo){
            return false;
        }else{
            saldo -= costo;
            return true;
        }

    }

}
