package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.general;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Contribuyente {
    private String codDocIde;
    private String numRuc;
    private String numDocIde;
    private String codDependencia;

    private String codRegimenAfecto;

    // Getters y setters

    // Getter para codDocIde
    public String getCodDocIde() {
        return codDocIde;
    }

    // Setter para codDocIde
    public void setCodDocIde(String codDocIde) {
        this.codDocIde = codDocIde;
    }

    // Getter para numRuc
    public String getNumRuc() {
        return numRuc;
    }

    // Setter para numRuc
    public void setNumRuc(String numRuc) {
        this.numRuc = numRuc;
    }

    // Getter para numDocIde
    public String getNumDocIde() {
        return numDocIde;
    }

    // Setter para numDocIde
    public void setNumDocIde(String numDocIde) {
        this.numDocIde = numDocIde;
    }

    // Getter para codDependencia
    public String getCodDependencia() {
        return codDependencia;
    }

    // Setter para codDependencia
    public void setCodDependencia(String codDependencia) {
        this.codDependencia = codDependencia;
    }


    public String getCodRegimenAfecto() {
        return codRegimenAfecto;
    }

    public void setCodRegimenAfecto(String codRegimenAfecto) {
        this.codRegimenAfecto = codRegimenAfecto;
    }

    @Override
    public String toString() {
        return "Contribuyente{" +
                "codDocIde='" + codDocIde + '\'' +
                ", numRuc='" + numRuc + '\'' +
                ", numDocIde='" + numDocIde + '\'' +
                ", codDependencia='" + codDependencia + '\'' +
                ", codRegimenAfecto='" + codRegimenAfecto + '\'' +
                '}';
    }

}
