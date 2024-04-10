package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.general;

import java.io.Serializable;

public class PercepcionProxy implements Serializable {

    private int totalComprobantes;

    private ComprobantesProxy comprobantes;
    private  double mtoTotal;

    public int getTotalComprobantes() {
        return totalComprobantes;
    }

    public void setTotalComprobantes(int totalComprobantes) {
        this.totalComprobantes = totalComprobantes;
    }

    public ComprobantesProxy getComprobantes() {
        return comprobantes;
    }

    public void setComprobantes(ComprobantesProxy comprobantes) {
        this.comprobantes = comprobantes;
    }

    public double getMtoTotal() {
        return mtoTotal;
    }

    public void setMtoTotal(double mtoTotal) {
        this.mtoTotal = mtoTotal;
    }
}
