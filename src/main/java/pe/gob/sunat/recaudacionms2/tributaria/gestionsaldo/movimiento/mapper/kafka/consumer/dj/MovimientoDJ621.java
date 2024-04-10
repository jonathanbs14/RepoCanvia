package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.dj;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.dto.CabeceraDJ;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.dto.Metadata;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovimientoDJ621 {

    private Metadata  metadata;

    private CabeceraDJ cabeceraDJ;

    private DataMovimientoDJ621 dataMovimiento;


    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public CabeceraDJ getCabecera() {
        return cabeceraDJ;
    }

    public void setCabecera(CabeceraDJ cabeceraDJ) {
        this.cabeceraDJ = cabeceraDJ;
    }

    public DataMovimientoDJ621 getDataMovimiento() {
        return dataMovimiento;
    }

    public void setDataMovimiento(DataMovimientoDJ621 dataMovimiento) {
        this.dataMovimiento = dataMovimiento;
    }
}
