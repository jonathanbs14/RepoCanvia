package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.producer.seguimiento;

import com.fasterxml.jackson.annotation.JsonInclude;
import pe.gob.sunat.tecnologiams2.arquitectura.framework.microservices.core.exception.ErrorEntity;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorSeguimiento {
    private String cod;
    private String msg;
    private String exec;

    public ErrorSeguimiento() {
    }

    public ErrorSeguimiento(String cod, String msg, String exec) {
        this.cod = cod;
        this.msg = msg;
        this.exec = exec;
    }

    public ErrorSeguimiento(String cod, String msg, String exec, List<ErrorEntity> errors) {
        this.cod = cod;
        this.msg = msg;
        this.exec = exec;
        this.errors = errors;
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ErrorEntity> errors;

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getExec() {
        return exec;
    }

    public void setExec(String exec) {
        this.exec = exec;
    }

    public List<ErrorEntity> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorEntity> errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "ErrorSeguimiento{" +
                "cod='" + cod + '\'' +
                ", msg='" + msg + '\'' +
                ", exec='" + exec + '\'' +
                ", errors=" + errors +
                '}';
    }
}
