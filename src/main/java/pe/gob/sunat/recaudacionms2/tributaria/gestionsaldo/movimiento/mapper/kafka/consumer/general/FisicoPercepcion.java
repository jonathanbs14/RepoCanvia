package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.general;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FisicoPercepcion {



    private  double mtomtoPercepcion;
    private int cantidxad;

    public double getMtomtoPercepcion() {
        return mtomtoPercepcion;
    }

    public void setMtomtoPercepcion(double mtomtoPercepcion) {
        this.mtomtoPercepcion = mtomtoPercepcion;
    }

    public int getCantidxad() {
        return cantidxad;
    }

    public void setCantidxad(int cantidxad) {
        this.cantidxad = cantidxad;
    }
}
