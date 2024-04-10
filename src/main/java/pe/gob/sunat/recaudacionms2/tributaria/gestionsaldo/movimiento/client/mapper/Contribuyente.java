package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.client.mapper;

import java.util.List;

public class Contribuyente {

    private String numRuc;

    private String razonSocial;

    private String ubigeo;

    private String estado;

    private List<String> ciius;

    public String getNumRuc() {
        return numRuc;
    }

    public void setNumRuc(String numRuc) {
        this.numRuc = numRuc;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getUbigeo() {
        return ubigeo;
    }

    public void setUbigeo(String ubigeo) {
        this.ubigeo = ubigeo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<String> getCiius() {
        return ciius;
    }

    public void setCiius(List<String> ciius) {
        this.ciius = ciius;
    }
}
