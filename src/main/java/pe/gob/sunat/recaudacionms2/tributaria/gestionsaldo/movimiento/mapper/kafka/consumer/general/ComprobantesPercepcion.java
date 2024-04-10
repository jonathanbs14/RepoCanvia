package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.general;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ComprobantesPercepcion {

    private FisicoPercepcion fisico;
    private ElectronicoPercepcion electronicoPercepcion;


    public FisicoPercepcion getFisico() {
        return fisico;
    }

    public void setFisico(FisicoPercepcion fisico) {
        this.fisico = fisico;
    }

    public ElectronicoPercepcion getElectronico() {
        return electronicoPercepcion;
    }

    public void setElectronico(ElectronicoPercepcion electronicoPercepcion) {
        this.electronicoPercepcion = electronicoPercepcion;
    }
}
