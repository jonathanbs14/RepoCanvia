package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.general;

public class DetalleReliquidacion {

    private String fecActualizacionDeuda;

    private String fecVencimientoDeuda;

    private String fecReliquidacion;

    private String fechaUltimoPago;

    private String fecUltimaCapitalizacion;

    private DetalleMontos detalleMontos;


    private String indFueraPlazo;

    private String indPagosIEAN;

    private String indVentasGravadas;

    private String indDocumentoValorado;

    private String indFormaReliquidar;

    private String mtoPagoExceso;

    private String indAjuste;

    private String indEstadoDeuda;


    public String getFecActualizacionDeuda() {
        return fecActualizacionDeuda;
    }

    public void setFecActualizacionDeuda(String fecActualizacionDeuda) {
        this.fecActualizacionDeuda = fecActualizacionDeuda;
    }

    public String getFecVencimientoDeuda() {
        return fecVencimientoDeuda;
    }

    public void setFecVencimientoDeuda(String fecVencimientoDeuda) {
        this.fecVencimientoDeuda = fecVencimientoDeuda;
    }

    public String getFecReliquidacion() {
        return fecReliquidacion;
    }

    public void setFecReliquidacion(String fecReliquidacion) {
        this.fecReliquidacion = fecReliquidacion;
    }

    public String getFechaUltimoPago() {
        return fechaUltimoPago;
    }

    public void setFechaUltimoPago(String fechaUltimoPago) {
        this.fechaUltimoPago = fechaUltimoPago;
    }

    public String getFecUltimaCapitalizacion() {
        return fecUltimaCapitalizacion;
    }

    public void setFecUltimaCapitalizacion(String fecUltimaCapitalizacion) {
        this.fecUltimaCapitalizacion = fecUltimaCapitalizacion;
    }

    public DetalleMontos getDetalleMontos() {
        return detalleMontos;
    }

    public void setDetalleMontos(DetalleMontos detalleMontos) {
        this.detalleMontos = detalleMontos;
    }

    public String getIndFueraPlazo() {
        return indFueraPlazo;
    }

    public void setIndFueraPlazo(String indFueraPlazo) {
        this.indFueraPlazo = indFueraPlazo;
    }

    public String getIndPagosIEAN() {
        return indPagosIEAN;
    }

    public void setIndPagosIEAN(String indPagosIEAN) {
        this.indPagosIEAN = indPagosIEAN;
    }

    public String getIndVentasGravadas() {
        return indVentasGravadas;
    }

    public void setIndVentasGravadas(String indVentasGravadas) {
        this.indVentasGravadas = indVentasGravadas;
    }

    public String getIndDocumentoValorado() {
        return indDocumentoValorado;
    }

    public void setIndDocumentoValorado(String indDocumentoValorado) {
        this.indDocumentoValorado = indDocumentoValorado;
    }

    public String getIndFormaReliquidar() {
        return indFormaReliquidar;
    }

    public void setIndFormaReliquidar(String indFormaReliquidar) {
        this.indFormaReliquidar = indFormaReliquidar;
    }

    public String getMtoPagoExceso() {
        return mtoPagoExceso;
    }

    public void setMtoPagoExceso(String mtoPagoExceso) {
        this.mtoPagoExceso = mtoPagoExceso;
    }

    public String getIndAjuste() {
        return indAjuste;
    }

    public void setIndAjuste(String indAjuste) {
        this.indAjuste = indAjuste;
    }

    public String getIndEstadoDeuda() {
        return indEstadoDeuda;
    }

    public void setIndEstadoDeuda(String indEstadoDeuda) {
        this.indEstadoDeuda = indEstadoDeuda;
    }
}
