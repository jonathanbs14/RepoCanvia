package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.general;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.utils.FormatoFecha;

import java.math.BigDecimal;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DatosMovimiento {
    private String codSaldoAcumulado;
    private String codSaldAsoc;
    private String codEvt;
    private String codMov;
    private String descMov;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FormatoFecha.yyyyMMddTHHmmssSSSXXX, timezone = FormatoFecha.ZONA_HORARIA)
    private Date fecMov;

    private Double montoMov;

    private String estadoMov;

    private String indInconMov;
    private String indRegInconMov;

    private Double totalPag;
    private Double totalImpTri;
    private Double montoInt;
    private Double montoNoImp;

    private String venGra100;
    private String venGra154;
    private String venGra160;
    private String desCon102;
    private String desDev162;
    private String venNacGra;
    private String expFac106;
    private String venNoGra105;

    public String getCodSaldoAcumulado() {
        return codSaldoAcumulado;
    }

    public void setCodSaldoAcumulado(String codSaldoAcumulado) {
        this.codSaldoAcumulado = codSaldoAcumulado;
    }

    public String getCodSaldAsoc() {
        return codSaldAsoc;
    }

    public void setCodSaldAsoc(String codSaldAsoc) {
        this.codSaldAsoc = codSaldAsoc;
    }

    public String getCodEvt() {
        return codEvt;
    }

    public void setCodEvt(String codEvt) {
        this.codEvt = codEvt;
    }

    public String getCodMov() {
        return codMov;
    }

    public void setCodMov(String codMov) {
        this.codMov = codMov;
    }

    public String getDescMov() {
        return descMov;
    }

    public void setDescMov(String descMov) {
        this.descMov = descMov;
    }

    public Date getFecMov() {
        return fecMov;
    }

    public void setFecMov(Date fecMov) {
        this.fecMov = fecMov;
    }

    public Double getMontoMov() {
        return montoMov;
    }

    public void setMontoMov(Double montoMov) {
        this.montoMov = montoMov;
    }

    public String getEstadoMov() {
        return estadoMov;
    }

    public void setEstadoMov(String estadoMov) {
        this.estadoMov = estadoMov;
    }

    public String getIndInconMov() {
        return indInconMov;
    }

    public void setIndInconMov(String indInconMov) {
        this.indInconMov = indInconMov;
    }

    public String getIndRegInconMov() {
        return indRegInconMov;
    }

    public void setIndRegInconMov(String indRegInconMov) {
        this.indRegInconMov = indRegInconMov;
    }

    public Double getTotalPag() {
        return totalPag;
    }

    public void setTotalPag(Double totalPag) {
        this.totalPag = totalPag;
    }

    public Double getTotalImpTri() {
        return totalImpTri;
    }

    public void setTotalImpTri(Double totalImpTri) {
        this.totalImpTri = totalImpTri;
    }

    public Double getMontoInt() {
        return montoInt;
    }

    public void setMontoInt(Double montoInt) {
        this.montoInt = montoInt;
    }

    public Double getMontoNoImp() {
        return montoNoImp;
    }

    public void setMontoNoImp(Double montoNoImp) {
        this.montoNoImp = montoNoImp;
    }

    public String getVenGra100() {
        return venGra100;
    }

    public void setVenGra100(String venGra100) {
        this.venGra100 = venGra100;
    }

    public String getVenGra154() {
        return venGra154;
    }

    public void setVenGra154(String venGra154) {
        this.venGra154 = venGra154;
    }

    public String getVenGra160() {
        return venGra160;
    }

    public void setVenGra160(String venGra160) {
        this.venGra160 = venGra160;
    }

    public String getDesCon102() {
        return desCon102;
    }

    public void setDesCon102(String desCon102) {
        this.desCon102 = desCon102;
    }

    public String getDesDev162() {
        return desDev162;
    }

    public void setDesDev162(String desDev162) {
        this.desDev162 = desDev162;
    }

    public String getVenNacGra() {
        return venNacGra;
    }

    public void setVenNacGra(String venNacGra) {
        this.venNacGra = venNacGra;
    }

    public String getExpFac106() {
        return expFac106;
    }

    public void setExpFac106(String expFac106) {
        this.expFac106 = expFac106;
    }

    public String getVenNoGra105() {
        return venNoGra105;
    }

    public void setVenNoGra105(String venNoGra105) {
        this.venNoGra105 = venNoGra105;
    }

    @Override
    public String toString() {
        return "DatosMovimiento{" +
                "codSaldAsoc='" + codSaldAsoc + '\'' +
                ", codEvt='" + codEvt + '\'' +
                ", codMov='" + codMov + '\'' +
                ", descMov='" + descMov + '\'' +
                ", fecMov=" + fecMov +
                ", montoMov=" + montoMov +
                ", estadoMov='" + estadoMov + '\'' +
                ", indInconMov='" + indInconMov + '\'' +
                ", indRegInconMov='" + indRegInconMov + '\'' +
                '}';
    }


}