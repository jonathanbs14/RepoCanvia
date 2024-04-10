package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonIgnoreProperties(
        ignoreUnknown = true
)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class    CabeceraDJ {

    private String numIdEventoPadre;
    private String numIdEventoSaldo;
    private String codEventoSaldo;
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS",
            timezone = "GMT-5:00"
    )
    private Date fecEventoSaldo;
    private String codNemoEventoSaldo;
    private String perDocumento;
    private String numRuc;
    private String codTipoEvento;
    private String codDocumento;
    private String numDocumento;
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS",
            timezone = "GMT-5:00"
    )
    private Date fecDocumento;
    private String codDependencia;
    private String indComponente;

    private String codDocumentoAsociado;


    public String getCodDocumentoAsociado() {
        return codDocumentoAsociado;
    }

    public void setCodDocumentoAsociado(String codDocumentoAsociado) {
        this.codDocumentoAsociado = codDocumentoAsociado;
    }

    public String getNumIdEventoSaldo() {
        return numIdEventoSaldo;
    }

    public void setNumIdEventoSaldo(String numIdEventoSaldo) {
        this.numIdEventoSaldo = numIdEventoSaldo;
    }

    public String getCodEventoSaldo() {
        return codEventoSaldo;
    }

    public void setCodEventoSaldo(String codEventoSaldo) {
        this.codEventoSaldo = codEventoSaldo;
    }

    public Date getFecEventoSaldo() {
        return fecEventoSaldo;
    }

    public void setFecEventoSaldo(Date fecEventoSaldo) {
        this.fecEventoSaldo = fecEventoSaldo;
    }

    public String getCodNemoEventoSaldo() {
        return codNemoEventoSaldo;
    }

    public void setCodNemoEventoSaldo(String codNemoEventoSaldo) {
        this.codNemoEventoSaldo = codNemoEventoSaldo;
    }

    public String getPerDocumento() {
        return perDocumento;
    }

    public void setPerDocumento(String perDocumento) {
        this.perDocumento = perDocumento;
    }

    public String getNumRuc() {
        return numRuc;
    }

    public void setNumRuc(String numRuc) {
        this.numRuc = numRuc;
    }

    public String getCodTipoEvento() {
        return codTipoEvento;
    }

    public void setCodTipoEvento(String codTipoEvento) {
        this.codTipoEvento = codTipoEvento;
    }

    public String getCodDocumento() {
        return codDocumento;
    }

    public void setCodDocumento(String codDocumento) {
        this.codDocumento = codDocumento;
    }

    public String getNumDocumento() {
        return numDocumento;
    }

    public void setNumDocumento(String numDocumento) {
        this.numDocumento = numDocumento;
    }

    public Date getFecDocumento() {
        return fecDocumento;
    }

    public void setFecDocumento(Date fecDocumento) {
        this.fecDocumento = fecDocumento;
    }

    public String getCodDependencia() {
        return codDependencia;
    }

    public void setCodDependencia(String codDependencia) {
        this.codDependencia = codDependencia;
    }

    public String getIndComponente() {
        return indComponente;
    }

    public void setIndComponente(String indComponente) {
        this.indComponente = indComponente;
    }

    public String getNumIdEventoPadre() {
        return numIdEventoPadre;
    }

    public void setNumIdEventoPadre(String numIdEventoPadre) {
        this.numIdEventoPadre = numIdEventoPadre;
    }
}
