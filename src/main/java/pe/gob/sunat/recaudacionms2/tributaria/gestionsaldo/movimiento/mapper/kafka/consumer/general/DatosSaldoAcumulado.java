package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.general;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DatosSaldoAcumulado {

    private double montoGenerado;

    private double montoUtilizado;

    private double montoDevuelto;

    private double montoCompenzadoUtilizado;

    public double getMontoGenerado() {
        return montoGenerado;
    }

    public void setMontoGenerado(double montoGenerado) {
        this.montoGenerado = montoGenerado;
    }

    public double getMontoUtilizado() {
        return montoUtilizado;
    }

    public void setMontoUtilizado(double montoUtilizado) {
        this.montoUtilizado = montoUtilizado;
    }

    public double getMontoDevuelto() {
        return montoDevuelto;
    }

    public void setMontoDevuelto(double montoDevuelto) {
        this.montoDevuelto = montoDevuelto;
    }

    public double getMontoCompenzadoUtilizado() {
        return montoCompenzadoUtilizado;
    }

    public void setMontoCompenzadoUtilizado(double montoCompenzadoUtilizado) {
        this.montoCompenzadoUtilizado = montoCompenzadoUtilizado;
    }
}
