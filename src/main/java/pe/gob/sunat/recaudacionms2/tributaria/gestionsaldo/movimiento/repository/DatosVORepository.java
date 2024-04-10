package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.dto.DatosVO;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.dj.DataDJVO;

import java.util.List;

public interface DatosVORepository  extends MongoRepository<DatosVO, String> {

    @Query("{ 'cabecera.numRuc' : ?0, 'cabecera.getCodDocumento' : ?1, 'cabecera.getNumDocumento' : ?2 }")
    List<DatosVO> findByNumRucNumForCodOrd(String numRuc, String numOrd, String codFor);


}
