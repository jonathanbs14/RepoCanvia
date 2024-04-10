package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.dto;

import java.io.Serializable;

public class DatosVO<T>  implements Serializable {
    private Metadata metadata;
    private CabeceraDJ cabeceraDJ;
    private T data;

    public DatosVO() {
        super();
    }

    public DatosVO(Metadata metadata, CabeceraDJ cabeceraDJ, T data) {
        super();
        this.metadata = metadata;
        this.cabeceraDJ = cabeceraDJ;
        this.data = data;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
