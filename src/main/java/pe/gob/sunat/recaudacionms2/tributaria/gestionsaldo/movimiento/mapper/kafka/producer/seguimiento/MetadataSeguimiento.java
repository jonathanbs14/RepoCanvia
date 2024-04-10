package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.producer.seguimiento;

import com.fasterxml.jackson.annotation.JsonFormat;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.utils.FormatoFecha;

import java.util.Date;

public class MetadataSeguimiento {
    private String numIdEvento;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FormatoFecha.yyyyMMddTHHmmssSSSSSSSSS, timezone = FormatoFecha.ZONA_HORARIA)
    private Date fecEvento;
    private String nomTopicoOrigen;
    private String nomTopico;

    public String getNumIdEvento() {
        return numIdEvento;
    }

    public void setNumIdEvento(String numIdEvento) {
        this.numIdEvento = numIdEvento;
    }

    public Date getFecEvento() {
        return fecEvento;
    }

    public void setFecEvento(Date fecEvento) {
        this.fecEvento = fecEvento;
    }

    public String getNomTopicoOrigen() {
        return nomTopicoOrigen;
    }

    public void setNomTopicoOrigen(String nomTopicoOrigen) {
        this.nomTopicoOrigen = nomTopicoOrigen;
    }

    public String getNomTopico() {
        return nomTopico;
    }

    public void setNomTopico(String nomTopico) {
        this.nomTopico = nomTopico;
    }

    @Override
    public String toString() {
        return "MetadataSeguimiento{" +
                "numIdEvento='" + numIdEvento + '\'' +
                ", fecEvento=" + fecEvento +
                ", nomTopicoOrigen='" + nomTopicoOrigen + '\'' +
                ", nomTopico='" + nomTopico + '\'' +
                '}';
    }
}
