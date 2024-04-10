package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.producer.dj;

public class DatosCuentaAfectada {

    private String numCta;
    private Double mtoSaldoActual;
    private String indInconsistencia;

    //********************
    private Double mtoSaldoInicial;

    public Double getMtoSaldoInicial() {
        return mtoSaldoInicial;
    }

    public void setMtoSaldoInicial(Double mtoSaldoInicial) {
        this.mtoSaldoInicial = mtoSaldoInicial;
    }

//*******************

    public String getNumCta() {
        return numCta;
    }

    public void setNumCta(String numCta) {
        this.numCta = numCta;
    }



    public Double getMtoSaldoActual() {
        return mtoSaldoActual;
    }

    public void setMtoSaldoActual(Double mtoSaldoActual) {
        this.mtoSaldoActual = mtoSaldoActual;
    }

    public String getIndInconsistencia() {
        return indInconsistencia;
    }

    public void setIndInconsistencia(String indInconsistencia) {
        this.indInconsistencia = indInconsistencia;
    }

}
