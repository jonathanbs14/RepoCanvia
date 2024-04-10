package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.dto;

import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.general.Casilla;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class DJ621 {
    private String numRuc;
    private String razSocial;
    private String codDependencia;
    private String codDocumento;

    private String desDocumento;
    private String numDocumento;
    private String perDocumento;
    private String codTributo;
    private Date fecDocumento;
    private BigDecimal mtoPagTotal;
    private String codDocAsoc;
    private String numOrdAsociado;
    private List<Casilla> numCas;

    public String getNumRuc() {
        return numRuc;
    }

    public void setNumRuc(String numRuc) {
        this.numRuc = numRuc;
    }

    public String getRazSocial() {
        return razSocial;
    }

    public void setRazSocial(String razSocial) {
        this.razSocial = razSocial;
    }

    public String getCodDependencia() {
        return codDependencia;
    }

    public void setCodDependencia(String codDependencia) {
        this.codDependencia = codDependencia;
    }

    public String getCodDocumento() {
        return codDocumento;
    }

    public void setCodDocumento(String codDocumento) {
        this.codDocumento = codDocumento;
    }

    public String getDesDocumento() {
        return desDocumento;
    }

    public void setDesDocumento(String desDocumento) {
        this.desDocumento = desDocumento;
    }

    public String getNumDocumento() {
        return numDocumento;
    }

    public void setNumDocumento(String numDocumento) {
        this.numDocumento = numDocumento;
    }

    public String getPerDocumento() {
        return perDocumento;
    }

    public void setPerDocumento(String perDocumento) {
        this.perDocumento = perDocumento;
    }

    public String getCodTributo() {
        return codTributo;
    }

    public void setCodTributo(String codTributo) {
        this.codTributo = codTributo;
    }

    public Date getFecDocumento() {
        return fecDocumento;
    }

    public void setFecDocumento(Date fecDocumento) {
        this.fecDocumento = fecDocumento;
    }

    public BigDecimal getMtoPagTotal() {
        return mtoPagTotal;
    }

    public void setMtoPagTotal(BigDecimal mtoPagTotal) {
        this.mtoPagTotal = mtoPagTotal;
    }

    public String getCodDocAsoc() {
        return codDocAsoc;
    }

    public void setCodDocAsoc(String codDocAsoc) {
        this.codDocAsoc = codDocAsoc;
    }

    public String getNumOrdAsociado() {
        return numOrdAsociado;
    }

    public void setNumOrdAsociado(String numOrdAsociado) {
        this.numOrdAsociado = numOrdAsociado;
    }


    public List<Casilla> getNumCas() {
        return numCas;    }


    public void setNumCas(List<Casilla> numCas) {
        this.numCas = numCas;
    }
}
