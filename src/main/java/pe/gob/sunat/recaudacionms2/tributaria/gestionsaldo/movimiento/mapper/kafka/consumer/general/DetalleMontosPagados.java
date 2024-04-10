package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.general;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DetalleMontosPagados {
    private String codMoneda;
    private String mtoTipoCambio;
    private BigDecimal mtoPagTributo;
    private BigDecimal mtoPagInteres;
    private BigDecimal mtoPagTotal;

    // Getters y setters
    // Getters y setters para DetalleMontosPagados
    public String getCodMoneda() {
        return codMoneda;
    }

    public void setCodMoneda(String codMoneda) {
        this.codMoneda = codMoneda;
    }

    public String getMtoTipoCambio() {
        return mtoTipoCambio;
    }

    public void setMtoTipoCambio(String mtoTipoCambio) {
        this.mtoTipoCambio = mtoTipoCambio;
    }

    public BigDecimal getMtoPagTributo() {
        return mtoPagTributo;
    }

    public void setMtoPagTributo(BigDecimal mtoPagTributo) {
        this.mtoPagTributo = mtoPagTributo;
    }

    public BigDecimal getMtoPagInteres() {
        return mtoPagInteres;
    }

    public void setMtoPagInteres(BigDecimal mtoPagInteres) {
        this.mtoPagInteres = mtoPagInteres;
    }

    public BigDecimal getMtoPagTotal() {
        return mtoPagTotal;
    }

    public void setMtoPagTotal(BigDecimal mtoPagTotal) {
        this.mtoPagTotal = mtoPagTotal;
    }

    @Override
    public String toString() {
        return "DetalleMontosPagados{" +
                "codMoneda='" + codMoneda + '\'' +
                ", mtoTipoCambio='" + mtoTipoCambio + '\'' +
                ", mtoPagTributo=" + mtoPagTributo +
                ", mtoPagInteres=" + mtoPagInteres +
                ", mtoPagTotal=" + mtoPagTotal +
                '}';
    }
}
