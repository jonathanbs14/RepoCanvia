package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.general;

public class DatosAnexo {

    private String codTipoDocumento;
    private String numDocumento;
    private String mtoRegistrado;

    public String getCodTipoDocumento() {
        return codTipoDocumento;
    }

    public void setCodTipoDocumento(String codTipoDocumento) {
        this.codTipoDocumento = codTipoDocumento;
    }

    public String getNumDocumento() {
        return numDocumento;
    }

    public void setNumDocumento(String numDocumento) {
        this.numDocumento = numDocumento;
    }

    public String getMtoRegistrado() {
        return mtoRegistrado;
    }

    public void setMtoRegistrado(String mtoRegistrado) {
        this.mtoRegistrado = mtoRegistrado;
    }
}
