package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.general;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DatosGestionControl {

    private List<Casilla> castillas ;

    private double saldoAntesMovimiento;

    private double saldoDespuesMovimeinto;

    private String indRegistroManual;


    public List<Casilla> getCastillas() {
        return castillas;
    }

    public void setCastillas(List<Casilla> castillas) {
        this.castillas = castillas;
    }

    public double getSaldoDespuesMovimeinto() {
        return saldoDespuesMovimeinto;
    }

    public void setSaldoDespuesMovimeinto(double saldoDespuesMovimeinto) {
        this.saldoDespuesMovimeinto = saldoDespuesMovimeinto;
    }

    public String getIndRegistroManual() {
        return indRegistroManual;
    }

    public void setIndRegistroManual(String indRegistroManual) {
        this.indRegistroManual = indRegistroManual;
    }

    public double getSaldoAntesMovimiento() {
        return saldoAntesMovimiento;
    }

    public void setSaldoAntesMovimiento(double saldoAntesMovimiento) {
        this.saldoAntesMovimiento = saldoAntesMovimiento;
    }
}
