package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.Closeable;
import java.io.IOException;
import java.time.YearMonth;

public class LibUtil {
    private final static ObjectReader objectReader = getObjectMapper().reader();
    private final static ObjectWriter objectWriter = getObjectMapper().writer();

    public static <T> String serializeToJson(T entity) throws JsonProcessingException {
        ObjectMapper objectMapper = getObjectMapper();
        return objectMapper.writeValueAsString(entity);
    }

    public static <T> T deserializeFromJson(String json, Class<T> entity) throws IOException {
        ObjectMapper objectMapper = getObjectMapper();
        return objectMapper.readValue(json, entity);
    }

    public static ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    public static ObjectWriter getDefaultObjectWriter() {
        return LibUtil.objectWriter;
    }

    public static ObjectReader getDefaultObjectReader() {
        return LibUtil.objectReader;
    }

    public static void cerrarStream(Closeable closable) {
        if (null != closable) {
            try {
                closable.close();
            } catch (IOException e) {
                e.getMessage();
            }
        }
    }
    /*-------------------------------------------------------------------------------------
    UTIL PARA GESTION PAGO VALIDAR periodoInicioModeloSA-IGV
    -----------------------------------------------------------------------------------------*/

    public static Boolean validarPeriodoPago(String periodoPago, String periodoInicioModeloSA) {

        int yearPeriodoPago = Integer.parseInt(periodoPago.substring(0, 4));
        int monthPeriodoPago = Integer.parseInt(periodoPago.substring(4, 6));

        int yearPeriodoInicioModeloSA = Integer.parseInt(periodoInicioModeloSA.substring(0, 4));
        int monthPeriodoInicioModeloSA = Integer.parseInt(periodoInicioModeloSA.substring(4, 6));

        YearMonth datePeriodoPago = YearMonth.of(yearPeriodoPago, monthPeriodoPago);
        YearMonth datePeriodoInicioModeloSA = YearMonth.of(yearPeriodoInicioModeloSA, monthPeriodoInicioModeloSA);

        if (datePeriodoPago.isBefore(datePeriodoInicioModeloSA)) {
            return true;
        }
        return false;
        /*if (datePeriodoPago.isAfter(datePeriodoInicioModeloSA)) {
            System.out.println(periodoPago + " es mayor que " + periodoInicioModeloSA);
        } else if (datePeriodoPago.isBefore(datePeriodoInicioModeloSA)) {
            System.out.println(periodoPago + " es menor que " + periodoInicioModeloSA);
            return true;
        } else {
            System.out.println(periodoPago + " es igual a " + periodoInicioModeloSA);
        }*/



    }
}
