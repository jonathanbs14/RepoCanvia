package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.producer.seguimiento;

import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.domain.parametria.param0020.ParamItem0020;

import java.util.Date;

public class ProducerSeguimiento {

    public static MensajeSeguimiento getMensajeSeguimiento(String numIdEvento, ParamItem0020 param0020, String topicoConsumerEjecucion,
                                                           String idComponente, String codEvento, String nomMicroservicio, Long cantidadMilisegundosEjecucion,
                                                           String codRespuesta, ErrorSeguimiento errorSeguimiento){
        //codRespuesta=> 0 -> exitoso, 1->error
        MetadataSeguimiento metadataSeguimiento = new MetadataSeguimiento();
        metadataSeguimiento.setNumIdEvento(numIdEvento);
        metadataSeguimiento.setFecEvento(new Date());
        metadataSeguimiento.setNomTopicoOrigen(topicoConsumerEjecucion);

        DataSeguimiento dataSeguimiento = new DataSeguimiento();
        dataSeguimiento.setNomTopico(topicoConsumerEjecucion);
        dataSeguimiento.setCodSeguimiento(param0020.getCodSeguimiento());
        dataSeguimiento.setDesSeguimiento(param0020.getDesSeguimiento());
        dataSeguimiento.setCodEtapa(param0020.getCodEtapa());
        dataSeguimiento.setDesEtapa(param0020.getDesEtapa());
        dataSeguimiento.setIdComponente(idComponente);
        dataSeguimiento.setFecSeguimiento(new Date());
        dataSeguimiento.setCodEvento(codEvento);
        dataSeguimiento.setNomMicroservicio(nomMicroservicio);
        dataSeguimiento.setCntMiliProcesamiento(cantidadMilisegundosEjecucion);
        dataSeguimiento.setCodRespuesta(codRespuesta);
        dataSeguimiento.setError(errorSeguimiento);

        MensajeSeguimiento mensajeSeguimiento = new MensajeSeguimiento();
        mensajeSeguimiento.setMetadata(metadataSeguimiento);
        mensajeSeguimiento.setData(dataSeguimiento);

        return mensajeSeguimiento;
    }


}
