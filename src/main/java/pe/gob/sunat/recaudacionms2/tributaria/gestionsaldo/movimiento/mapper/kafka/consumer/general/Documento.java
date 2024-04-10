package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.general;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Documento {

    private String indTipoDeclaracion;
    private String codDocumento;
    private String numDocumento;
    private String indEstadoDoc;
    private Date fecDocumento;
    private String indRectificatoria;
    private String indRechazo;
    private String codTributo;
    private String numDocRectificado;

    // Getters y setters

    // Getters y setters para Documento

    public String getIndTipoDeclaracion() {
        return indTipoDeclaracion;
    }

    public void setIndTipoDeclaracion(String indTipoDeclaracion) {
        this.indTipoDeclaracion = indTipoDeclaracion;
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

    public String getIndEstadoDoc() {
        return indEstadoDoc;
    }

    public void setIndEstadoDoc(String indEstadoDoc) {
        this.indEstadoDoc = indEstadoDoc;
    }

    public Date getFecDocumento() {
        return fecDocumento;
    }

    public void setFecDocumento(Date fecDocumento) {
        this.fecDocumento = fecDocumento;
    }

    public String getIndRectificatoria() {
        return indRectificatoria;
    }

    public void setIndRectificatoria(String indRectificatoria) {
        this.indRectificatoria = indRectificatoria;
    }

    public String getIndRechazo() {
        return indRechazo;
    }

    public void setIndRechazo(String indRechazo) {
        this.indRechazo = indRechazo;
    }

    public String getCodTributo() {
        return codTributo;
    }

    public void setCodTributo(String codTributo) {
        this.codTributo = codTributo;
    }

    public String getNumDocRectificado() {
        return numDocRectificado;
    }

    public void setNumDocRectificado(String numDocRectificado) {
        this.numDocRectificado = numDocRectificado;
    }

    @Override
    public String toString() {
        return "Documento{" +
                "indTipoDeclaracion='" + indTipoDeclaracion + '\'' +
                ", codDocumento='" + codDocumento + '\'' +
                ", numDocumento=" + numDocumento +
                ", indEstadoDoc='" + indEstadoDoc + '\'' +
                ", fecDocumento='" + fecDocumento + '\'' +
                ", indRectificatoria='" + indRectificatoria + '\'' +
                ", indRechazo='" + indRechazo + '\'' +
                ", codTributo='" + codTributo + '\'' +
                ", numDocRectificado='" + numDocRectificado + '\'' +
                '}';
    }
}
