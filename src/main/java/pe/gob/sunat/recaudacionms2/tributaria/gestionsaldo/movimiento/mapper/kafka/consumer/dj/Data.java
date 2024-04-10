package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.dj;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.general.Contribuyente;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.general.DocumentoSustento;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Data {

    private Contribuyente contribuyente;
    private DocumentoSustento documentoSustento;
    private String indEstado;

    public Contribuyente getContribuyente() {
        return contribuyente;
    }

    public void setContribuyente(Contribuyente contribuyente) {
        this.contribuyente = contribuyente;
    }

    public DocumentoSustento getDocumentoSustento() {
        return documentoSustento;
    }

    public void setDocumentoSustento(DocumentoSustento documentoSustento) {
        this.documentoSustento = documentoSustento;
    }

    public String getIndEstado() {
        return indEstado;
    }

    public void setIndEstado(String indEstado) {
        this.indEstado = indEstado;
    }





}
