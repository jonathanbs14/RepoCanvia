package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.general;

import java.util.Date;

public class DatosMovimientoAcumPercReT {

    private String codCuenta;
    private String codEvento;
    private Date fechMov;
    private double montoMov;
    private double impResult;
    private  double  persRetAcum;
    private double persRetUtil;

    private double persRetUtilAnt;

    private double saldAFavorAnt;

    private double SaldAcumLugMov;

    public String getCodCuenta() {
        return codCuenta;
    }

    public void setCodCuenta(String codCuenta) {
        this.codCuenta = codCuenta;
    }

    public String getCodEvento() {
        return codEvento;
    }

    public void setCodEvento(String codEvento) {
        this.codEvento = codEvento;
    }

    public Date getFechMov() {
        return fechMov;
    }

    public void setFechMov(Date fechMov) {
        this.fechMov = fechMov;
    }

    public double getMontoMov() {
        return montoMov;
    }

    public void setMontoMov(double montoMov) {
        this.montoMov = montoMov;
    }

    public double getImpResult() {
        return impResult;
    }

    public void setImpResult(double impResult) {
        this.impResult = impResult;
    }

    public double getPersRetAcum() {
        return persRetAcum;
    }

    public void setPersRetAcum(double persRetAcum) {
        this.persRetAcum = persRetAcum;
    }

    public double getPersRetUtil() {
        return persRetUtil;
    }

    public void setPersRetUtil(double persRetUtil) {
        this.persRetUtil = persRetUtil;
    }

    public double getPersRetUtilAnt() {
        return persRetUtilAnt;
    }

    public void setPersRetUtilAnt(double persRetUtilAnt) {
        this.persRetUtilAnt = persRetUtilAnt;
    }

    public double getSaldAFavorAnt() {
        return saldAFavorAnt;
    }

    public void setSaldAFavorAnt(double saldAFavorAnt) {
        this.saldAFavorAnt = saldAFavorAnt;
    }

    public double getSaldAcumLugMov() {
        return SaldAcumLugMov;
    }

    public void setSaldAcumLugMov(double saldAcumLugMov) {
        SaldAcumLugMov = saldAcumLugMov;
    }
}
