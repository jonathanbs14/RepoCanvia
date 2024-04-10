package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.producer.dj;

import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.dto.CabeceraDJ;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.dto.Metadata;

public class DataSaVO {

    private Metadata metadata;
    private CabeceraDJ cabecera;
    private Data data;

    public DataSaVO() {
        super();
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
