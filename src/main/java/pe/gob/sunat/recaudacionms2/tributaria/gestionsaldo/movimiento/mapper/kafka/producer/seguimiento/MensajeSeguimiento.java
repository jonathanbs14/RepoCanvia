package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.producer.seguimiento;

public class MensajeSeguimiento {
    private MetadataSeguimiento metadata;
    private DataSeguimiento data;

    public MetadataSeguimiento getMetadata() {
        return metadata;
    }

    public void setMetadata(MetadataSeguimiento metadata) {
        this.metadata = metadata;
    }

    public DataSeguimiento getData() {
        return data;
    }

    public void setData(DataSeguimiento data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MensajeSeguimiento{" +
                "metadata=" + metadata +
                ", data=" + data +
                '}';
    }
}
