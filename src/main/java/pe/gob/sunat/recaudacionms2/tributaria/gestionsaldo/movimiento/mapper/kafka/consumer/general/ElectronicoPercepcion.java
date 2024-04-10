package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.general;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ElectronicoPercepcion {

    private  double mtoPercepcion;
    private int cantidxad;

    public double getMtoPercepcion() {
        return mtoPercepcion;
    }

    public void setMtoPercepcion(double mtoPercepcion) {
        this.mtoPercepcion = mtoPercepcion;
    }

    public int getCantidxad() {
        return cantidxad;
    }

    public void setCantidxad(int cantidxad) {
        this.cantidxad = cantidxad;
    }
}
