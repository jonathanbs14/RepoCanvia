package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.general;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DatosDocumentoAcumPercReT {

    private String codDocumento;
    private String numDoc;

    private String perTributario;

    private String  codTributo;

    private Date fechaDocumento;

    private String tipoDJ;

    private String tipoRetificatoria;

    private String tipoRetificatoriaUsuarioInterno;

    public String getCodDocumento() {
        return codDocumento;
    }

    public void setCodDocumento(String codDocumento) {
        this.codDocumento = codDocumento;
    }

    public String getNumDoc() {
        return numDoc;
    }

    public void setNumDoc(String numDoc) {
        this.numDoc = numDoc;
    }

    public String getPerTributario() {
        return perTributario;
    }

    public void setPerTributario(String perTributario) {
        this.perTributario = perTributario;
    }

    public String getCodTributo() {
        return codTributo;
    }

    public void setCodTributo(String codTributo) {
        this.codTributo = codTributo;
    }

    public String getTipoDJ() {
        return tipoDJ;
    }

    public void setTipoDJ(String tipoDJ) {
        this.tipoDJ = tipoDJ;
    }

    public String getTipoRetificatoria() {
        return tipoRetificatoria;
    }

    public void setTipoRetificatoria(String tipoRetificatoria) {
        this.tipoRetificatoria = tipoRetificatoria;
    }

    public String getTipoRetificatoriaUsuarioInterno() {
        return tipoRetificatoriaUsuarioInterno;
    }

    public void setTipoRetificatoriaUsuarioInterno(String tipoRetificatoriaUsuarioInterno) {
        this.tipoRetificatoriaUsuarioInterno = tipoRetificatoriaUsuarioInterno;
    }

    public Date getFechaDocumento() {
        return fechaDocumento;
    }

    public void setFechaDocumento(Date fechaDocumento) {
        this.fechaDocumento = fechaDocumento;
    }
}
