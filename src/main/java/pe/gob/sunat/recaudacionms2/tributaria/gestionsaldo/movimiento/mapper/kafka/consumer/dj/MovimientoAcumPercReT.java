package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.dj;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.domain.general.MetadataGral;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.domain.movimientossa.Cabecera;
//import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.domain.movimientossa.Metadata;
import pe.gob.sunat.tecnologiams2.arquitectura.framework.microservices.mongo.document.Auditoria;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovimientoAcumPercReT {

    private MetadataGral metadata;

    private  Cabecera cabecera;

    private DataMovimientoAcumPercReT dataMovimientoAcumPercReT;


//    public Metadata getMetadata() {
//        return metadata;
//    }
//
//    public void setMetadata(Metadata metadata) {
//        this.metadata = metadata;
//    }

    public MetadataGral getMetadata() {
        return metadata;
    }

    public void setMetadata(MetadataGral metadata) {
        this.metadata = metadata;
    }

    public Cabecera getCabecera() {
        return cabecera;
    }

    public void setCabecera(Cabecera cabecera) {
        this.cabecera = cabecera;
    }

    public DataMovimientoAcumPercReT getDataMovimientoAcumPercReT() {
        return dataMovimientoAcumPercReT;
    }

    public void setDataMovimientoAcumPercReT(DataMovimientoAcumPercReT dataMovimientoAcumPercReT) {
        this.dataMovimientoAcumPercReT = dataMovimientoAcumPercReT;
    }

}
