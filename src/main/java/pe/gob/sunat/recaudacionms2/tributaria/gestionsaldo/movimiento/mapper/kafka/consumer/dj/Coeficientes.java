package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.dj;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.dto.CabeceraDJ;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.dto.Metadata;
//import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.general.Auditoria;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Coeficientes {

    private Metadata metadata;

    private CabeceraDJ cabecera;

    private Data data;

    private Auditoria auditoria;

    public Auditoria getAuditoria() {
        return auditoria;
    }

    public void setAuditoria(Auditoria auditoria) {
        this.auditoria = auditoria;
    }

    private String indEstado;

    public String getIndEstado() {
        return indEstado;
    }

    public void setIndEstado(String indEstado) {
        this.indEstado = indEstado;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public CabeceraDJ getCabecera() {
        return cabecera;
    }

    public void setCabecera(CabeceraDJ cabecera) {
        this.cabecera = cabecera;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
