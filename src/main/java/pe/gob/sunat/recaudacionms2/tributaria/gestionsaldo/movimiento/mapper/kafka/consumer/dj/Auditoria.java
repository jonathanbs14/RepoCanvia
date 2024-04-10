package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.dj;

import java.util.Date;

public class Auditoria {

    private String codUsuRegis;
    private Date fecRegis;
    private String codUsuModif;
    private Date fecModif;

    public Date getFecRegis() {
        return fecRegis;
    }

    public void setFecRegis(Date fecRegis) {
        this.fecRegis = fecRegis;
    }

    public Date getFecModif() {
        return fecModif;
    }

    public void setFecModif(Date fecModif) {
        this.fecModif = fecModif;
    }

    public String getCodUsuRegis() {
        return codUsuRegis;
    }

    public void setCodUsuRegis(String codUsuRegis) {
        this.codUsuRegis = codUsuRegis;
    }

    public String getCodUsuModif() {
        return codUsuModif;
    }

    public void setCodUsuModif(String codUsuModif) {
        this.codUsuModif = codUsuModif;
    }

}
