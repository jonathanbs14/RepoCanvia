package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.producer.error;

import java.util.List;

public class ParametriaEvento {
    private String codEvento;
    private String nemoEvento;
    private String codTipoDocumento;
    private String topicoCapturador;
    private List<String> lisFormulario;

    public String getCodEvento() {
        return codEvento;
    }

    public void setCodEvento(String codEvento) {
        this.codEvento = codEvento;
    }

    public String getNemoEvento() {
        return nemoEvento;
    }

    public void setNemoEvento(String nemoEvento) {
        this.nemoEvento = nemoEvento;
    }

    public String getCodTipoDocumento() {
        return codTipoDocumento;
    }

    public void setCodTipoDocumento(String codTipoDocumento) {
        this.codTipoDocumento = codTipoDocumento;
    }

    public String getTopicoCapturador() {
        return topicoCapturador;
    }

    public void setTopicoCapturador(String topicoCapturador) {
        this.topicoCapturador = topicoCapturador;
    }

    public List<String> getLisFormulario() {
        return lisFormulario;
    }

    public void setLisFormulario(List<String> lisFormulario) {
        this.lisFormulario = lisFormulario;
    }

    @Override
    public String toString() {
        return "ParametriaEvento{" +
                "codEvento='" + codEvento + '\'' +
                ", nemoEvento='" + nemoEvento + '\'' +
                ", codTipoDocumento='" + codTipoDocumento + '\'' +
                ", topicoCapturador='" + topicoCapturador + '\'' +
                ", lisFormulario=" + lisFormulario +
                '}';
    }
}
