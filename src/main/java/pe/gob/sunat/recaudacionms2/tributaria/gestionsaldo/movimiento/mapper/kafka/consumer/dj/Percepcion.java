package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.dj;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.general.ComprobantesPercepcion;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Percepcion {

    private int totalComprobantes;

    private ComprobantesPercepcion comprobantesPercepcion;
    private  double mtoTotal;


    public int getTotalComprobantes() {
        return totalComprobantes;
    }

    public void setTotalComprobantes(int totalComprobantes) {
        this.totalComprobantes = totalComprobantes;
    }

    public ComprobantesPercepcion getComprobantes() {
        return comprobantesPercepcion;
    }

    public void setComprobantes(ComprobantesPercepcion comprobantesPercepcion) {
        this.comprobantesPercepcion = comprobantesPercepcion;
    }

    public double getMtoTotal() {
        return mtoTotal;
    }

    public void setMtoTotal(double mtoTotal) {
        this.mtoTotal = mtoTotal;
    }
}
