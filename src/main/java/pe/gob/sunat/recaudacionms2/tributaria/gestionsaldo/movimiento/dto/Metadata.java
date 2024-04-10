package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonIgnoreProperties(
        ignoreUnknown = true
)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Metadata  {
    private String numIdEventoPadre;
    private String numIdEvento;
    private String nomTopico;
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS",
            timezone = "GMT-5:00"
    )
    private Date fecEvento;
    private String desTabla;
    private String indOperacion;
    private String numVersionJSON;

    public String getNumIdEvento() {
        return numIdEvento;
    }

    public void setNumIdEvento(String numIdEvento) {
        this.numIdEvento = numIdEvento;
    }

    public String getNomTopico() {
        return nomTopico;
    }

    public void setNomTopico(String nomTopico) {
        this.nomTopico = nomTopico;
    }

    public Date getFecEvento() {
        return fecEvento;
    }

    public void setFecEvento(Date fecEvento) {
        this.fecEvento = fecEvento;
    }

    public String getDesTabla() {
        return desTabla;
    }

    public void setDesTabla(String desTabla) {
        this.desTabla = desTabla;
    }

    public String getIndOperacion() {
        return indOperacion;
    }

    public void setIndOperacion(String indOperacion) {
        this.indOperacion = indOperacion;
    }

    public String getNumVersionJSON() {
        return numVersionJSON;
    }

    public void setNumVersionJSON(String numVersionJSON) {
        this.numVersionJSON = numVersionJSON;
    }

    public String getNumIdEventoPadre() {
        return numIdEventoPadre;
    }

    public void setNumIdEventoPadre(String numIdEventoPadre) {
        this.numIdEventoPadre = numIdEventoPadre;
    }
}
