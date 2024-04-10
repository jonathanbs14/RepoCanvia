package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.general;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DocumentoSustento {
    private String codDocumento ;
    private String descripcion;
    private String numOrden;
    private String periodo;
    private String codTributo;
    private String fecDoc;
    private String tipDj;
    private String tipRectificatoria;

    private String tipoDjUsuarioInterno;

    // inicio cus03
    private List<Casilla> lisCasillas ;
    private String codDocSustentoCoefPorcCalculado;
    private String nunOrdenDocSustentoCoefCalculado;
    private String perTriDocSustentoCoefCalculado;
    private String fecProcesoCalculoCoef;

    public void setLisCasillas(List<Casilla> lisCasillas) {
        this.lisCasillas = lisCasillas;
    }

    public String getCodDocSustentoCoefPorcCalculado() {
        return codDocSustentoCoefPorcCalculado;
    }

    public void setCodDocSustentoCoefPorcCalculado(String codDocSustentoCoefPorcCalculado) {
        this.codDocSustentoCoefPorcCalculado = codDocSustentoCoefPorcCalculado;
    }

    public String getNunOrdenDocSustentoCoefCalculado() {
        return nunOrdenDocSustentoCoefCalculado;
    }

    public void setNunOrdenDocSustentoCoefCalculado(String nunOrdenDocSustentoCoefCalculado) {
        this.nunOrdenDocSustentoCoefCalculado = nunOrdenDocSustentoCoefCalculado;
    }

    public String getPerTriDocSustentoCoefCalculado() {
        return perTriDocSustentoCoefCalculado;
    }

    public void setPerTriDocSustentoCoefCalculado(String perTriDocSustentoCoefCalculado) {
        this.perTriDocSustentoCoefCalculado = perTriDocSustentoCoefCalculado;
    }

    public String getFecProcesoCalculoCoef() {
        return fecProcesoCalculoCoef;
    }

    public void setFecProcesoCalculoCoef(String fecProcesoCalculoCoef) {
        this.fecProcesoCalculoCoef = fecProcesoCalculoCoef;
    }
    // fin cus03


    public String getTipoDjUsuarioInterno() {
        return tipoDjUsuarioInterno;
    }

    public void setTipoDjUsuarioInterno(String tipoDjUsuarioInterno) {
        this.tipoDjUsuarioInterno = tipoDjUsuarioInterno;
    }

    public String getCodDocumento() {
        return codDocumento;
    }

    public void setCodDocumento(String codDocumento) {
        this.codDocumento = codDocumento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNumOrden() {
        return numOrden;
    }

    public void setNumOrden(String numOrden) {
        this.numOrden = numOrden;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getFecDoc() {
        return fecDoc;
    }

    public void setFecDoc(String fecDoc) {
        this.fecDoc = fecDoc;
    }

    public String getTipDj() {
        return tipDj;
    }

    public void setTipDj(String tipDj) {
        this.tipDj = tipDj;
    }

    public String getTipRectificatoria() {
        return tipRectificatoria;
    }

    public void setTipRectificatoria(String tipRectificatoria) {
        this.tipRectificatoria = tipRectificatoria;
    }

    public String getCodTributo() {
        return codTributo;
    }

    public void setCodTributo(String codTributo) {
        this.codTributo = codTributo;
    }

    @Override
    public String toString() {
        return "DocumentoSustento{" +
                "codDocumento='" + codDocumento + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", numOrden='" + numOrden + '\'' +
                ", periodo='" + periodo + '\'' +
                ", fecDoc='" + fecDoc + '\'' +
                ", tipDj='" + tipDj + '\'' +
                ", tipRectificatoria='" + tipRectificatoria + '\'' +
                ", tipoDjUsuarioInterno='" + tipoDjUsuarioInterno + '\'' +
                '}';
    }
}
