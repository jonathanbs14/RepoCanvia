package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.dj;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.domain.eventosanteriores.DocumentoAsociadoAfectado;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.general.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataMovimientoDJ621 {

    private Contribuyente contribuyente;

    private DocumentoSustento sustento;

    private DocumentoAsociadoAfectado documentoAsociadoAfectado;

    private DatosMovimiento datosMovimiento;

    private DatosSaldoAcumulado datosSaldoAcumulado;
    private GestionYControl gestionYControl;


    public Contribuyente getContribuyente() {
        return contribuyente;
    }

    public void setContribuyente(Contribuyente contribuyente) {
        this.contribuyente = contribuyente;
    }

    public DocumentoSustento getSustento() {
        return sustento;
    }

    public void setSustento(DocumentoSustento sustento) {
        this.sustento = sustento;
    }

    public DocumentoAsociadoAfectado getDocumentoAsociadoAfectado() {
        return documentoAsociadoAfectado;
    }

    public void setDocumentoAsociadoAfectado(DocumentoAsociadoAfectado documentoAsociadoAfectado) {
        this.documentoAsociadoAfectado = documentoAsociadoAfectado;
    }

    public DatosMovimiento getDatosMovimiento() {
        return datosMovimiento;
    }

    public void setDatosMovimiento(DatosMovimiento datosMovimiento) {
        this.datosMovimiento = datosMovimiento;
    }

    public GestionYControl getGestionYControl() {
        return gestionYControl;
    }

    public void setGestionYControl(GestionYControl gestionYControl) {
        this.gestionYControl = gestionYControl;
    }

    public DatosSaldoAcumulado getDatosSaldoAcumulado() {
        return datosSaldoAcumulado;
    }

    public void setDatosSaldoAcumulado(DatosSaldoAcumulado datosSaldoAcumulado) {
        this.datosSaldoAcumulado = datosSaldoAcumulado;
    }
}
