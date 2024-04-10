package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.general;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Casilla {

    private String numCas;
    private String valCas;
    private String codTipoCas;

    public Casilla() {
    }

    public Casilla(String numCas, String valCas, String nomTipoDatosCas) {
        this.numCas = numCas;
        this.valCas = valCas;
        this.codTipoCas = nomTipoDatosCas;
    }

    public String getNumCas() {
        return numCas;
    }

    public void setNumCas(String numCas) {
        this.numCas = numCas;
    }

    public String getValCas() { return valCas;
    }

    public void setValCas(String valCas) {
        this.valCas = valCas;
    }

    public String getCodTipoCas() {
        return codTipoCas;
    }

    public void setCodTipoCas(String codTipoCas) {
        this.codTipoCas = codTipoCas;
    }

    @Override
    public String toString() {
        return "Casilla{" +
                "numCas='" + numCas + '\'' +
                ", valCas='" + valCas + '\'' +
                ", codTipoCas='" + codTipoCas + '\'' +
                '}';
    }
}
