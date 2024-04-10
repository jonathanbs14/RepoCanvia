package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.general;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DetallePago {

    private String numPago;
    private String fecPago;
    private String perTributarioPago;
    private String numSemana;
    private String codMedioPago;
    private String indEstadoPago;
    private int annEjercicio;

    // Getters y setters

    // Getters y setters para DetallePago
    public String getNumPago() {
        return numPago;
    }

    public void setNumPago(String numPago) {
        this.numPago = numPago;
    }

    public String getFecPago() {
        return fecPago;
    }

    public void setFecPago(String fecPago) {
        this.fecPago = fecPago;
    }

    public String getPerTributarioPago() {
        return perTributarioPago;
    }

    public void setPerTributarioPago(String perTributarioPago) {
        this.perTributarioPago = perTributarioPago;
    }

    public String getNumSemana() {
        return numSemana;
    }

    public void setNumSemana(String numSemana) {
        this.numSemana = numSemana;
    }

    public String getCodMedioPago() {
        return codMedioPago;
    }

    public void setCodMedioPago(String codMedioPago) {
        this.codMedioPago = codMedioPago;
    }

    public String getIndEstadoPago() {
        return indEstadoPago;
    }

    public void setIndEstadoPago(String indEstadoPago) {
        this.indEstadoPago = indEstadoPago;
    }

    public int getAnnEjercicio() {
        return annEjercicio;
    }

    public void setAnnEjercicio(int annEjercicio) {
        this.annEjercicio = annEjercicio;
    }

    @Override
    public String toString() {
        return "DetallePago{" +
                "numPago='" + numPago + '\'' +
                ", fecPago='" + fecPago + '\'' +
                ", perTributarioPago='" + perTributarioPago + '\'' +
                ", numSemana='" + numSemana + '\'' +
                ", codMedioPago='" + codMedioPago + '\'' +
                ", indEstadoPago='" + indEstadoPago + '\'' +
                ", annEjercicio=" + annEjercicio +
                '}';
    }
}
