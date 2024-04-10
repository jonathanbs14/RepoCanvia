package pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.service.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectReader;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.domain.parametria.param0017.ParamItem0017;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.basegestionsaldo.repository.ParametriaRepository;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.dto.DatosVO;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.dj.ConsultarPercepcionesRetencionesPeriodos;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.mapper.kafka.consumer.dj.DataDJVO;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.service.OperacionMovimientoService;
import pe.gob.sunat.recaudacionms2.tributaria.gestionsaldo.movimiento.utils.LibUtil;
import static org.mockito.Mockito.mockStatic;

public class MovimientoDJ621IGVServiceImplTest {
    /*
  @InjectMocks
    MovimientoDJ621IGVServiceImpl service;

    @Mock
    private ParametriaRepository parametriaRepository;

    @Mock
    private ConsultarPercepcionesRetencionesPeriodos conusltaPer;

    @Mock
    private  OperacionMovimientoService operacionMovimientoService;

    @Mock
    ObjectReader objectReader;

    DatosVO<DataDJVO> dataJson = new DatosVO<>();
    ParamItem0017 param0017 = new ParamItem0017();


     static MockedStatic<LibUtil> classMock_LibUtil = mockStatic(LibUtil.class);


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        classMock_LibUtil.when(() -> LibUtil.getDefaultObjectReader())
                .thenReturn(objectReader);
    }

//    @AfterAll
//    public void close(){
//        classMock_LibUtil.close();
//    }

    @Test
    public void dataJson () throws JsonProcessingException {
        Mockito.when(objectReader.forType(Mockito.<TypeReference<DatosVO<DataDJVO>>>any()))
                .thenReturn(objectReader);
        Mockito.when(objectReader.readValue(Mockito.anyString()))
                .thenReturn(dataJson);

        Mockito.when(parametriaRepository.findDataParametria0017()).
                thenReturn(param0017);

        service.processMessage("0", 0);
    }
*/
}
