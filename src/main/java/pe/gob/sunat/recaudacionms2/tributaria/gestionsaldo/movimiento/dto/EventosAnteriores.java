package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.Map;

@Document("eventosAnteriores")
public class EventosAnteriores {

    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private Metadata metadata;
    private CabeceraDJ cabeceraDJ;
    private Map<String, Object> data;

    public EventosAnteriores() {
        super();
    }

    public EventosAnteriores(Metadata metadata, CabeceraDJ cabeceraDJ, Map<String, Object> data) {
        super();
        this.metadata = metadata;
        this.cabeceraDJ = cabeceraDJ;
        this.data = data;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

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

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

}
