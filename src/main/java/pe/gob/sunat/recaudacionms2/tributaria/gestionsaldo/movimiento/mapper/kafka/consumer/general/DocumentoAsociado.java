package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.general;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DocumentoAsociado {
    private String codDocumento ;
    private String descripcion;
    private String numOrden;
    private String periodo;
    private String fecDoc;
    private String tipDj;
    private String tipRectificatoria;

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

    @Override
    public String toString() {
        return "DocumentoAsociado{" +
                "codDocumento='" + codDocumento + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", numOrden='" + numOrden + '\'' +
                ", periodo='" + periodo + '\'' +
                ", fecDoc='" + fecDoc + '\'' +
                ", tipDj='" + tipDj + '\'' +
                ", tipRectificatoria='" + tipRectificatoria + '\'' +
                '}';
    }
}
