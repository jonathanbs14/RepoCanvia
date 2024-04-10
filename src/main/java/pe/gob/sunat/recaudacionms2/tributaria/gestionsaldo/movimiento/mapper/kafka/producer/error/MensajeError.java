package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.producer.error;

//import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.domain.eventossa.Metadata;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.domain.general.MetadataGral;

public class MensajeError {
    private MetadataGral metadata;
    private Mensaje mensaje;

    public MetadataGral getMetadata() {
        return metadata;
    }

    public void setMetadata(MetadataGral metadata) {
        this.metadata = metadata;
    }

    public Mensaje getMensaje() {
        return mensaje;
    }

    public void setMensaje(Mensaje mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public String toString() {
        return "MensajeError{" +
                "metadata=" + metadata +
                ", mensaje=" + mensaje +
                '}';
    }
}
