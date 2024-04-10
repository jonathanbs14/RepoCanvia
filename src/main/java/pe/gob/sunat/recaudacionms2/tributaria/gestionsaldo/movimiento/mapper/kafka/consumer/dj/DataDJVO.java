package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.dj;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.general.*;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataDJVO {
    private Contribuyente contribuyente;
    private Documento documento;
    private DetallePago detallePago;
    private DetalleMontosPagados detalleMontosPagados;
    private List<Casilla> lisCasillas;

    // Getters y setters

    // Getters y setters para TuClase
    public Contribuyente getContribuyente() {
        return contribuyente;
    }

    public void setContribuyente(Contribuyente contribuyente) {
        this.contribuyente = contribuyente;
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    public DetallePago getDetallePago() {
        return detallePago;
    }

    public void setDetallePago(DetallePago detallePago) {
        this.detallePago = detallePago;
    }

    public DetalleMontosPagados getDetalleMontosPagados() {
        return detalleMontosPagados;
    }

    public void setDetalleMontosPagados(DetalleMontosPagados detalleMontosPagados) {
        this.detalleMontosPagados = detalleMontosPagados;
    }

    public List<Casilla> getLisCasillas() {
        return lisCasillas;
    }

    public void setLisCasillas(List<Casilla> lisCasillas) {
        this.lisCasillas = lisCasillas;
    }

    @Override
    public String toString() {
        return "DataDJVO{" +
                "contribuyente=" + contribuyente +
                ", documento=" + documento +
                ", detallePago=" + detallePago +
                ", detalleMontosPagados=" + detalleMontosPagados +
                ", lisCasillas=" + lisCasillas +
                '}';
    }
}
