package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.producer.seguimiento;

import java.util.Date;

public class AuditoriaSeguimiento {
    private String codUsuRegis;
    private Date fecRegis;
    private String codUsuModif;
    private Date fecModif;

    public String getCodUsuRegis() {
        return codUsuRegis;
    }

    public void setCodUsuRegis(String codUsuRegis) {
        this.codUsuRegis = codUsuRegis;
    }

    public Date getFecRegis() {
        return fecRegis;
    }

    public void setFecRegis(Date fecRegis) {
        this.fecRegis = fecRegis;
    }

    public String getCodUsuModif() {
        return codUsuModif;
    }

    public void setCodUsuModif(String codUsuModif) {
        this.codUsuModif = codUsuModif;
    }

    public Date getFecModif() {
        return fecModif;
    }

    public void setFecModif(Date fecModif) {
        this.fecModif = fecModif;
    }

    @Override
    public String toString() {
        return "AuditoriaSeguimiento{" +
                "codUsuRegis='" + codUsuRegis + '\'' +
                ", fecRegis=" + fecRegis +
                ", codUsuModif='" + codUsuModif + '\'' +
                ", fecModif=" + fecModif +
                '}';
    }
}
