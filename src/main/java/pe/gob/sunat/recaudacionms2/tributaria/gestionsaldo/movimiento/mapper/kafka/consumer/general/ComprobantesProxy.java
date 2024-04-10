package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.general;

public class ComprobantesProxy {


    private FisicoProxy fisico;
    private ElectronicoProxy electronico;

    public FisicoProxy getFisico() {
        return fisico;
    }

    public void setFisico(FisicoProxy fisico) {
        this.fisico = fisico;
    }

    public ElectronicoProxy getElectronico() {
        return electronico;
    }

    public void setElectronico(ElectronicoProxy electronico) {
        this.electronico = electronico;
    }
}
