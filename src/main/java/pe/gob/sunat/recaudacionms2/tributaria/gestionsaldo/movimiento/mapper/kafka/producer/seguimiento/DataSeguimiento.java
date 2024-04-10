package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.producer.seguimiento;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.utils.FormatoFecha;

import java.util.Date;

public class DataSeguimiento {
    private String nomTopico;
    private String codSeguimiento;
    private String desSeguimiento;
    private String codEtapa;
    private String desEtapa;
    private String idComponente;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FormatoFecha.yyyyMMddTHHmmssSSSSSSSSS, timezone = FormatoFecha.ZONA_HORARIA)
    private Date fecSeguimiento;

    private String codEvento;

    private String nomMicroservicio;

    private Long cntMiliProcesamiento;

    private String codRespuesta;
    //private String codResultado;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ErrorSeguimiento error;

    private String codServicio;

    public String getNomTopico() {
        return nomTopico;
    }

    public void setNomTopico(String nomTopico) {
        this.nomTopico = nomTopico;
    }

    public String getCodSeguimiento() {
        return codSeguimiento;
    }

    public void setCodSeguimiento(String codSeguimiento) {
        this.codSeguimiento = codSeguimiento;
    }

    public String getDesSeguimiento() {
        return desSeguimiento;
    }

    public void setDesSeguimiento(String desSeguimiento) {
        this.desSeguimiento = desSeguimiento;
    }

    public String getCodEtapa() {
        return codEtapa;
    }

    public void setCodEtapa(String codEtapa) {
        this.codEtapa = codEtapa;
    }

    public String getIdComponente() {
        return idComponente;
    }

    public void setIdComponente(String idComponente) {
        this.idComponente = idComponente;
    }

    public Date getFecSeguimiento() {
        return fecSeguimiento;
    }

    public void setFecSeguimiento(Date fecSeguimiento) {
        this.fecSeguimiento = fecSeguimiento;
    }

    public String getCodEvento() {
        return codEvento;
    }

    public void setCodEvento(String codEvento) {
        this.codEvento = codEvento;
    }

    public String getNomMicroservicio() {
        return nomMicroservicio;
    }

    public void setNomMicroservicio(String nomMicroservicio) {
        this.nomMicroservicio = nomMicroservicio;
    }

    public Long getCntMiliProcesamiento() {
        return cntMiliProcesamiento;
    }

    public void setCntMiliProcesamiento(Long cntMiliProcesamiento) {
        this.cntMiliProcesamiento = cntMiliProcesamiento;
    }

    public String getCodRespuesta() {
        return codRespuesta;
    }

    public void setCodRespuesta(String codRespuesta) {
        this.codRespuesta = codRespuesta;
    }

    public ErrorSeguimiento getError() {
        return error;
    }

    public void setError(ErrorSeguimiento error) {
        this.error = error;
    }

    public String getDesEtapa() {
        return desEtapa;
    }

    public void setDesEtapa(String desEtapa) {
        this.desEtapa = desEtapa;
    }

    public String getCodServicio() {
        return codServicio;
    }

    public void setCodServicio(String codServicio) {
        this.codServicio = codServicio;
    }

    @Override
    public String toString() {
        return "DataSeguimiento{" +
                "nomTopico='" + nomTopico + '\'' +
                ", codSeguimiento='" + codSeguimiento + '\'' +
                ", desSeguimiento='" + desSeguimiento + '\'' +
                ", codEtapa='" + codEtapa + '\'' +
                ", desEtapa='" + desEtapa + '\'' +
                ", idComponente='" + idComponente + '\'' +
                ", fecSeguimiento=" + fecSeguimiento +
                ", codEvento='" + codEvento + '\'' +
                ", nomMicroservicio='" + nomMicroservicio + '\'' +
                ", cntMiliProcesamiento=" + cntMiliProcesamiento +
                ", codRespuesta='" + codRespuesta + '\'' +
                ", error=" + error + '\'' +
                ",  codServicio=" + codServicio +
                '}';
    }
}
