package entity;

import utilities.PaymentException;


public class SistemaPOS {

    float saldo = 200;

    public boolean autorizzaPagamento(ProfiloUtente p, float costo)throws PaymentException {

        if(saldo < costo){
            throw new PaymentException("Il saldo sul conto non è sufficiente");
        }else{
            saldo -= costo;
            return true;
        }

    }

}
