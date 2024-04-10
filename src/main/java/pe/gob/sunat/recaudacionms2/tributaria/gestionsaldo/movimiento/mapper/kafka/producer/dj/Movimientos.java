package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.producer.dj;

import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.domain.movimientossa.Cabecera;

public class Movimientos {

    private Cabecera cabecera;

    private DataMovimientos data;

    public Cabecera getCabecera() {
        return cabecera;
    }

    public void setCabecera(Cabecera cabecera) {
        this.cabecera = cabecera;
    }

    public DataMovimientos getData() {
        return data;
    }

    public void setData(DataMovimientos data) {
        this.data = data;
    }
}
