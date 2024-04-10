package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.mongodb.MongoTimeoutException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.HttpStatus;
import org.apache.kafka.common.errors.TimeoutException;
import org.hibernate.exception.GenericJDBCException;
import org.slf4j.Logger;
import org.springframework.kafka.core.KafkaTemplate;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.domain.parametria.param0020.DataParametria0020;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.domain.parametria.param0020.ParamItem0020;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.producer.seguimiento.ErrorSeguimiento;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.producer.seguimiento.MensajeSeguimiento;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.producer.seguimiento.ProducerSeguimiento;
import pe.gob.sunat.tecnologiams2.arquitectura.framework.microservices.core.exception.GenericException;
import pe.gob.sunat.tecnologiams2.arquitectura.framework.microservices.core.exception.UnprocessableEntityException;
import pe.gob.sunat.tecnologiams2.arquitectura.framework.microservices.core.util.JsonUtilParser;
import pe.gob.sunat.tecnologiams2.arquitectura.framework.microservices.kafka.exception.ErrorException;
import pe.gob.sunat.tecnologiams2.arquitectura.framework.microservices.kafka.exception.LoopException;
import pe.gob.sunat.tecnologiams2.arquitectura.framework.microservices.kafka.exception.RetriesException;

import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.utils.Constantes.*;

public class UtilSeguimiento {


        public static void getMensajeSeguimientoError(String numIdEvento, DataParametria0020 param0020, String topicoConsumerEjecucion, String topicoProducerSeguimiento,
                                                  String codEvento, String nomMicroservicio, Long cantidadMilisegundosEjecucion,
                                                  String codRespuesta, Exception e, Logger LOG, KafkaTemplate kafkaTemplate, int retryNumber, int retry) {
        ErrorSeguimiento errorSeguimiento = null;
        ParamItem0020 paramItem0020 = validarExistencia(ParametriaMethodsUtil.findParamItem0020(param0020.getLisParametros(),ID_COD_ETAPA_MOVIMIENTO,ID_COMPONENTE_IGV,ID_COD_SERVICIO_EVENTO,ID_CODRESULTADO_ERROR ),LOG);
        JsonUtilParser jsonUtilParser = new JsonUtilParser();

        if (e instanceof RetriesException) {//error tipo reintento

            LOG.error("Error tipo RetriesException");

            if ((retryNumber - 1) == retry) {
                if (e.getCause() instanceof UnprocessableEntityException) {
                    LOG.error("Error tipo RetriesException: con error de negocio, se enviara a topico R");

                    errorSeguimiento = new ErrorSeguimiento(String.valueOf(HttpStatus.SC_UNPROCESSABLE_ENTITY), "Error de Negocio", null, Arrays.asList(((UnprocessableEntityException) e.getCause()).getErrorEntity()));

                    MensajeSeguimiento mensajeSeguimiento = ProducerSeguimiento.getMensajeSeguimiento(numIdEvento, paramItem0020, topicoConsumerEjecucion,
                            ID_COMPONENTE_IGV, codEvento, nomMicroservicio, cantidadMilisegundosEjecucion,
                            codRespuesta, errorSeguimiento);

                    try {
                        kafkaTemplate.send(topicoProducerSeguimiento, jsonUtilParser.entityToJson(mensajeSeguimiento)).get();
                    } catch (InterruptedException ex) {
                        // Rethrow InterruptedException
                        Thread.currentThread().interrupt(); // Re-interrupt the thread
                        //throw ex;
                    } catch (Exception ex) {
                        LOG.error("no hay kafka:" + ExceptionUtils.getStackTrace(ex));
                        throw new LoopException(ex);
                    }

                    throw new ErrorException(e);//se va defrente al topico -R
                }
            }
            throw new RetriesException(e);
        }
        if (e.getCause() instanceof TimeoutException || e instanceof InterruptedException || e instanceof ExecutionException) { //kafka sin conexion
            LOG.error("Error: kafka sin conexion | ocurrio un error al enviar");
            throw new LoopException(e);
        }
        if (e.getCause() instanceof MongoTimeoutException) { //mongo sin conexion
            LOG.error("Error: Mongo sin conexion");

            MensajeSeguimiento mensajeSeguimiento = ProducerSeguimiento.getMensajeSeguimiento(numIdEvento, paramItem0020, topicoConsumerEjecucion,
                    ID_COMPONENTE_IGV, codEvento, nomMicroservicio, cantidadMilisegundosEjecucion,
                    codRespuesta, errorSeguimiento);

            try {
                kafkaTemplate.send(topicoProducerSeguimiento, jsonUtilParser.entityToJson(mensajeSeguimiento)).get();
            } catch (InterruptedException ex) {
                // Rethrow InterruptedException
                Thread.currentThread().interrupt(); // Re-interrupt the thread
                //throw ex;
            } catch (Exception ex) {
                LOG.error("no hay kafka:" + ExceptionUtils.getStackTrace(ex));
                throw new LoopException(ex);
            }

            throw new LoopException(e);
        }
        if (e.getCause() instanceof GenericJDBCException ||
                e.getCause() instanceof SQLException || e.getCause() instanceof SQLTimeoutException ||
                e instanceof SQLException || e instanceof SQLTimeoutException) {//informix sin conexion

            LOG.error("Error: Informix sin conexion");
           // paramItem0020 = validarExistencia(ParametriaMethodsUtil.findParamItem0020(param0020.getLisParametros(), Constantes.ID_COMPONENTE_ERROR, Constantes.ID_COD_SERVICIO_INFORMIX, Constantes.ID_CODRESULTADO_ERROR), logger);

            MensajeSeguimiento mensajeSeguimiento = ProducerSeguimiento.getMensajeSeguimiento(numIdEvento, paramItem0020, topicoConsumerEjecucion,
                    ID_COMPONENTE_IGV, codEvento, nomMicroservicio, cantidadMilisegundosEjecucion,
                    codRespuesta, errorSeguimiento);

            /*try {
                kafkaTemplate.send(topicoProducerSeguimiento, jsonUtilParser.entityToJson(mensajeSeguimiento)).get();
            } catch (Exception ex) {
                LOG.error("no hay kafka:" + ExceptionUtils.getStackTrace(ex));
                throw new LoopException(ex);
            }*/

            try {
                kafkaTemplate.send(topicoProducerSeguimiento, jsonUtilParser.entityToJson(mensajeSeguimiento)).get();
            } catch (InterruptedException ex) {
                // Rethrow InterruptedException
                Thread.currentThread().interrupt(); // Re-interrupt the thread
               // throw ex;
            } catch (Exception ex) {
                LOG.error("no hay kafka:" + ExceptionUtils.getStackTrace(ex));
                throw new LoopException(ex);
            }

            throw new LoopException(e);

        }
        if (e instanceof UnprocessableEntityException) {//error de negocio
            LOG.error("validacion de negocio");
            errorSeguimiento = new ErrorSeguimiento(String.valueOf(HttpStatus.SC_UNPROCESSABLE_ENTITY), "Error de Negocio", null, Arrays.asList(((UnprocessableEntityException) e).getErrorEntity()));

            MensajeSeguimiento mensajeSeguimiento = ProducerSeguimiento.getMensajeSeguimiento(numIdEvento, paramItem0020, topicoConsumerEjecucion,
                    ID_COMPONENTE_IGV, codEvento, nomMicroservicio, cantidadMilisegundosEjecucion,
                    codRespuesta, errorSeguimiento);

            try {
                kafkaTemplate.send(topicoProducerSeguimiento, jsonUtilParser.entityToJson(mensajeSeguimiento)).get();
            } catch (InterruptedException ex) {
                // Rethrow InterruptedException
                Thread.currentThread().interrupt(); // Re-interrupt the thread
                //throw ex;
            } catch (Exception ex) {
                LOG.error("no hay kafka:" + ExceptionUtils.getStackTrace(ex));
                throw new LoopException(ex);
            }
            return;
        }
        if (e instanceof JsonParseException || e instanceof GenericException) {//error parseo
            LOG.error("Error: parseo json");

            errorSeguimiento = new ErrorSeguimiento(String.valueOf(HttpStatus.SC_INTERNAL_SERVER_ERROR), "Error de Parseo", ExceptionUtils.getStackTrace(e));

            MensajeSeguimiento mensajeSeguimiento = ProducerSeguimiento.getMensajeSeguimiento(numIdEvento, paramItem0020, topicoConsumerEjecucion,
                    ID_COMPONENTE_IGV, codEvento, nomMicroservicio, cantidadMilisegundosEjecucion,
                    codRespuesta, errorSeguimiento);

            try {
                kafkaTemplate.send(topicoProducerSeguimiento, jsonUtilParser.entityToJson(mensajeSeguimiento)).get();
            } catch (InterruptedException ex) {
                // Rethrow InterruptedException
                Thread.currentThread().interrupt(); // Re-interrupt the thread
                //throw ex;
            } catch (Exception ex) {
                LOG.error("no hay kafka:" + ExceptionUtils.getStackTrace(ex));
                throw new LoopException(ex);
            }

            throw new ErrorException(e);//se va defrente al topico -R
        }
        if (e instanceof NullPointerException) {//error nullpointer
            LOG.error("Error: codigo NullpointerException");

            errorSeguimiento = new ErrorSeguimiento(String.valueOf(HttpStatus.SC_INTERNAL_SERVER_ERROR), "Error de codigo", ExceptionUtils.getStackTrace(e));

            MensajeSeguimiento mensajeSeguimiento = ProducerSeguimiento.getMensajeSeguimiento(numIdEvento, paramItem0020, topicoConsumerEjecucion,
                    ID_COMPONENTE_IGV, codEvento, nomMicroservicio, cantidadMilisegundosEjecucion,
                    codRespuesta, errorSeguimiento);

            try {
                kafkaTemplate.send(topicoProducerSeguimiento, jsonUtilParser.entityToJson(mensajeSeguimiento)).get();
            } catch (InterruptedException ex) {
                // Rethrow InterruptedException
                Thread.currentThread().interrupt(); // Re-interrupt the thread
                //throw ex;
            } catch (Exception ex) {
                LOG.error("no hay kafka:" + ExceptionUtils.getStackTrace(ex));
                throw new LoopException(ex);
            }

            throw new ErrorException(e);//se va defrente al topico -R
        } else {

            LOG.error("Error no controlado se enviara a topico R:{}", ExceptionUtils.getStackTrace(e));
            errorSeguimiento = new ErrorSeguimiento(String.valueOf(HttpStatus.SC_INTERNAL_SERVER_ERROR), "Error de codigo", ExceptionUtils.getStackTrace(e));

            MensajeSeguimiento mensajeSeguimiento = ProducerSeguimiento.getMensajeSeguimiento(numIdEvento, paramItem0020, topicoConsumerEjecucion,
                    ID_COMPONENTE_IGV, codEvento, nomMicroservicio, cantidadMilisegundosEjecucion,
                    codRespuesta, errorSeguimiento);

            try {
                kafkaTemplate.send(topicoProducerSeguimiento, jsonUtilParser.entityToJson(mensajeSeguimiento)).get();
            } catch (InterruptedException ex) {
                // Rethrow InterruptedException
                Thread.currentThread().interrupt(); // Re-interrupt the thread
                //throw ex;
            } catch (Exception ex) {
                LOG.error("no hay kafka:" + ExceptionUtils.getStackTrace(ex));
                throw new LoopException(ex);
            }

            throw new ErrorException(e);//se va defrente al topico -R
        }
    }


    public static void enviarMensajeSeguimiento(ParamItem0020 paramItem0020, String numIdEvento,
                                                String idComponente, KafkaTemplate<String, String> kafkaTemplate,
                                                String topic0, String codigoEvento, String nombreMicro,
                                                String topicSeguimiento, JsonUtilParser jsonUtilParser)
            throws InterruptedException, ExecutionException {
        long inicioEjecucion = System.currentTimeMillis();
        if (!Objects.isNull(paramItem0020)) {
            MensajeSeguimiento mensajeSeguimientoExitoso = ProducerSeguimiento.getMensajeSeguimiento(numIdEvento, paramItem0020, topic0, idComponente, codigoEvento, nombreMicro,
                    (System.currentTimeMillis() - inicioEjecucion), Constantes.CODIGO_RESPUESTA_EXITOSO, null);
            kafkaTemplate.send(topicSeguimiento, jsonUtilParser.entityToJson(mensajeSeguimientoExitoso)).get();
        }

    }

    public static ParamItem0020 validarExistencia(Optional<ParamItem0020> paramItem0020, Logger LOG) {

        return paramItem0020.orElseGet(() -> {
            LOG.warn("No hay informacion en mongo!!");
            ParamItem0020 paramItem00202 = new ParamItem0020();//paramItem00202 -> codParametro:0020 ,codEtapa:2
            paramItem00202.setCodEtapa("02");
            return paramItem00202;
        });
    }
}
