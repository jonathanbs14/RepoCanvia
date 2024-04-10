package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.general;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GestionYControl {

    private BigDecimal saldoAnterior;
    private BigDecimal saldoRegistrado;
    private String actManual;

    private String codEstado;

    private String indicadorRegistro;

    private String dividendo;
    private  String divisor;
    private String infCalculo;

    public BigDecimal getSaldoRegistrado() {
        return saldoRegistrado;
    }

    public void setSaldoRegistrado(BigDecimal saldoRegistrado) {
        this.saldoRegistrado = saldoRegistrado;
    }

    public String getActManual() {
        return actManual;
    }

    public void setActManual(String actManual) {
        this.actManual = actManual;
    }

    public BigDecimal getSaldoAnterior() {
        return saldoAnterior;
    }

    public void setSaldoAnterior(BigDecimal saldoAnterior) {
        this.saldoAnterior = saldoAnterior;
    }

    public String getCodEstado() {
        return codEstado;
    }

    public void setCodEstado(String codEstado) {
        this.codEstado = codEstado;
    }

    public String getIndicadorRegistro() {
        return indicadorRegistro;
    }

    public void setIndicadorRegistro(String indicadorRegistro) {
        this.indicadorRegistro = indicadorRegistro;
    }

    public String getDividendo() {
        return dividendo;
    }

    public void setDividendo(String dividendo) {
        this.dividendo = dividendo;
    }

    public String getDivisor() {
        return divisor;
    }

    public void setDivisor(String divisor) {
        this.divisor = divisor;
    }

    public String getInfCalculo() {
        return infCalculo;
    }

    public void setInfCalculo(String infCalculo) {
        this.infCalculo = infCalculo;
    }

    @Override
    public String toString() {
        return "GestionYControl{" +
                "saldoAnterior=" + saldoAnterior +
                ", saldoRegistrado=" + saldoRegistrado +
                ", actManual='" + actManual + '\'' +
                ", codEstado='" + codEstado + '\'' +
                '}';
    }
}
