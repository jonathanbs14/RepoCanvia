package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.dto.DatosVO;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.dj.MovimientoDJ621;

import java.util.List;

public interface MovimientoDJ621Repository extends MongoRepository<MovimientoDJ621, String> {

 //   @Query("{ 'cabecera.numRuc' : ?0, 'cabecera.getCodDocumento' : ?1, 'cabecera.getNumDocumento' : ?2 }")
  //  List<MovimientoDJ621> findByNumRucNumForCodOrd(String numRuc, String numOrd, String codFor);

    public void saveMovimientoAcumPercReT(MovimientoDJ621 movimientoAcumPercReT, String collectionName);
}
