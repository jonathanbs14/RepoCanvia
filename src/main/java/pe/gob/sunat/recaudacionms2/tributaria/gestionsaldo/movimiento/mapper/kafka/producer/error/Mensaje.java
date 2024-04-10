package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.producer.error;

public class Mensaje {
    private String codRetorno;
    private String desMensaje;

    public String getCodRetorno() {
        return codRetorno;
    }

    public void setCodRetorno(String codRetorno) {
        this.codRetorno = codRetorno;
    }

    public String getDesMensaje() {
        return desMensaje;
    }

    public void setDesMensaje(String desMensaje) {
        this.desMensaje = desMensaje;
    }

    @Override
    public String toString() {
        return "Mensaje{" +
                "codRetorno='" + codRetorno + '\'' +
                ", desMensaje='" + desMensaje + '\'' +
                '}';
    }
}
