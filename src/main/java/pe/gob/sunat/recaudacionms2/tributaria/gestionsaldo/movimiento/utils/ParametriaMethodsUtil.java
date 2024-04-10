package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.utils;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
//import org.springframework.data.redis.RedisConnectionFailureException;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.domain.parametria.param0010.Param0010;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.domain.parametria.param0020.DataParametria0020;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.domain.parametria.param0020.ParamItem0020;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.repository.ParametriaRepository;

import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.producer.error.ParametriaEvento;
//import pe.gob.sunat.tecnologiams2.arquitectura.framework.microservices.redis.config.RedisRepository;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ParametriaMethodsUtil {

    //public static ParametriaEvento getDataParametria0010(String codEvento, ParametriaRepository parametriaRepository, RedisRepository redisRepository, Integer tiempoRedis, Logger logger) {
    public static ParametriaEvento getDataParametria0010(String codEvento, ParametriaRepository parametriaRepository, Integer tiempoRedis, Logger logger) {

        String key = Constantes.PARAMETRIA_BASE_0010 + "/" + codEvento;
        logger.info("buscando redis:{}", key);
        try {
           // ParametriaEvento parametriaEvento = redisRepository.find(key, ParametriaEvento.class);
//            if (Objects.nonNull(parametriaEvento)) {
//                logger.info("encontro redis:{}", key);
//                return parametriaEvento;
//            }
       // } catch (RedisConnectionFailureException e) {
        } catch (Exception e) {
            logger.info("REDIS: Error al leer: {}", key);
            logger.error(ExceptionUtils.getStackTrace(e));
        }

        Param0010 parametria = parametriaRepository.findParam0010ByCodEvento("Evt".concat(codEvento));

        if (Objects.isNull(parametria)) {
            return null;
        }

        ParametriaEvento param = new ParametriaEvento();
        param.setNemoEvento(parametria.getNemoEvento());
        param.setCodTipoDocumento(parametria.getTipoEvento());
        param.setCodEvento(parametria.getCodEvento());
        param.setTopicoCapturador(parametria.getTopicoCapturador());
        if (Objects.nonNull(parametria.getIndicadores())) {
            param.setLisFormulario(parametria.getIndicadores().getLisFormulario());
        }


        try {
         //   redisRepository.save(key, param, Duration.ofMinutes(Objects.isNull(tiempoRedis) ? Constantes.MINUTOS_DURACION_REDIS_DEFAULT : tiempoRedis));
        //} catch (RedisConnectionFailureException e) {
        } catch (Exception e) {
            logger.info("REDIS: Error al grabar: {}", key);
            logger.error(ExceptionUtils.getStackTrace(e));
        }

        return param;
    }

    //public static DataParametria0020 getDataParametria0020(String etapa, String desSeguimiento, ParametriaRepository parametriaRepository, RedisRepository redisRepository, Integer tiempoRedis, Logger logger) {
    public static DataParametria0020 getDataParametria0020(String etapa, String desSeguimiento, ParametriaRepository parametriaRepository, Integer tiempoRedis, Logger logger) {

        /*String key = Constantes.PARAMETRIA_BASE_0020 + "/" + etapa;
        logger.info("buscando redis:{}", key);
        try {
            DataParametria0020 dataParametria0020 = redisRepository.find(key, DataParametria0020.class);


            if (Objects.nonNull(dataParametria0020)) {
                logger.info("encontro redis:{}", key);
                return dataParametria0020;
            }
        } catch (RedisConnectionFailureException e) {
            logger.info("REDIS: Error al leer: {}", key);
            logger.error(ExceptionUtils.getStackTrace(e));
        }*/

        //DataParametria0020 parametria = parametriaRepository.findDataParametria0020ByCodEtapa(etapa);//SE CREARON DOS METODOS UNO QUE BUSCA CON COD_ETAPA Y OTRO QUE BUSCA CON COD_SEGUIMIENTO
        DataParametria0020 parametria = parametriaRepository.findDataParametria0020ByDesSeguimiento(desSeguimiento);
        if (Objects.isNull(parametria)) {
            return null;
        }

        /*
        try {
            redisRepository.save(key, parametria, Duration.ofMinutes(Objects.isNull(tiempoRedis) ? Constantes.MINUTOS_DURACION_REDIS_DEFAULT : tiempoRedis));
        } catch (RedisConnectionFailureException e) {
            logger.info("REDIS: Error al grabar: {}", key);
            logger.error(ExceptionUtils.getStackTrace(e));
        }

         */

        return parametria;
    }

    public static Optional<ParamItem0020> findParamItem0020(List<ParamItem0020> lisParametros, String codEtapa, String idComponente, String idServicio, String codResultado) {
        return lisParametros.parallelStream().filter(p -> codEtapa.equals(p.getCodEtapa()) && idComponente.equals(p.getIdComponente()) && idServicio.equals(p.getCodServicio()) && codResultado.equals(p.getCodResultado())).findFirst();
    }

    public static Optional<ParamItem0020> findParamItem0020(List<ParamItem0020> lisParametros, String codEtapa,String desSeguimiento, String idComponente, String idServicio, String codResultado) {
        return lisParametros.parallelStream().filter(p -> codEtapa.equals(p.getCodEtapa()) && desSeguimiento.equals(p.getDesSeguimiento()) && idComponente.equals(p.getIdComponente()) && idServicio.equals(p.getCodServicio()) && codResultado.equals(p.getCodResultado())).findFirst();
    }

}
