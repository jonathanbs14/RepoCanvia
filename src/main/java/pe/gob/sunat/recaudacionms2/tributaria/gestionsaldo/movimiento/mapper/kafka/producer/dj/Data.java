package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.producer.dj;

import java.util.List;

public class Data {

    private int numMovimientos;

    private List<Movimientos> lisMovimientos;

    public int getNumMovimientos() {
        return numMovimientos;
    }

    public void setNumMovimientos(int numMovimientos) {
        this.numMovimientos = numMovimientos;
    }

    public List<Movimientos> getLisMovimientos() {
        return lisMovimientos;
    }

    public void setLisMovimientos(List<Movimientos> lisMovimientos) {
        this.lisMovimientos = lisMovimientos;
    }
}
