package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.dj;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.mvel2.DataConversion;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.domain.movimientossa.Control;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.domain.movimientossa.DatosCalculoIGV;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.domain.movimientossa.DatosCuentaAfectada;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.general.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class    DataMovimientoAcumPercReT {



private DatosDocumentoAcumPercReT documentoAcumPercReT;

private DatosMovimientoAcumPercReT movimientoAcumPercReT;

private DatosCuentaAfectada datosCuentaAfectada;

private DatosCalculoIGV datosCalculoIGV;

private Control control ;

    public DatosCalculoIGV getDatosCalculoIGV() {
        return datosCalculoIGV;
    }

    public void setDatosCalculoIGV(DatosCalculoIGV datosCalculoIGV) {
        this.datosCalculoIGV = datosCalculoIGV;
    }

    public Control getControl() {
        return control;
    }

    public void setControl(Control control) {
        this.control = control;
    }



    public DatosDocumentoAcumPercReT getDocumentoAcumPercReT() {
        return documentoAcumPercReT;
    }

    public void setDocumentoAcumPercReT(DatosDocumentoAcumPercReT documentoAcumPercReT) {
        this.documentoAcumPercReT = documentoAcumPercReT;
    }



    public DatosMovimientoAcumPercReT getMovimientoAcumPercReT() {
        return movimientoAcumPercReT;
    }

    public void setMovimientoAcumPercReT(DatosMovimientoAcumPercReT movimientoAcumPercReT) {
        this.movimientoAcumPercReT = movimientoAcumPercReT;
    }


}
