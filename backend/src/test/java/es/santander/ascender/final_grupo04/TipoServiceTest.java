package es.santander.ascender.final_grupo04;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import es.santander.ascender.final_grupo04.DTO.TipoDTO;
import es.santander.ascender.final_grupo04.DTO.TipoFormatoDTO;
import es.santander.ascender.final_grupo04.model.Formato;
import es.santander.ascender.final_grupo04.model.Tipo;
import es.santander.ascender.final_grupo04.repository.FormatoRepository;
import es.santander.ascender.final_grupo04.repository.TipoRepository;
import es.santander.ascender.final_grupo04.service.TipoService;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@SpringBootTest
@Transactional
public class TipoServiceTest {

    @Autowired
    private TipoService tipoService;

    @Autowired
    private TipoRepository tipoRepository;

    @Autowired
    private FormatoRepository formatoRepository;

    private Tipo tipo;

    @BeforeEach
    void setUp() {
        // Inicializamos datos previos para cada test, si es necesario
        tipo = new Tipo();
        tipo.setNombre("Libro");
        tipoRepository.save(tipo);
    }

    @Test
    void testCrearTipo_Exitoso() {
    // Crear un DTO de tipo con un nombre único para evitar colisiones
        String nombreUnico = "Música_" + System.currentTimeMillis(); // Usamos un nombre único basado en el timestamp
        TipoDTO tipoDTO = new TipoDTO();
        tipoDTO.setNombre(nombreUnico);
        tipoDTO.setFormatoIds(List.of(1L, 2L));  // IDs de formatos existentes en la base de datos

        // Llamar al servicio para crear el tipo
        TipoDTO response = tipoService.crearTipo(tipoDTO);

        // Verificamos que la creación fue exitosa
        assertNotNull(response);
        assertEquals(nombreUnico, response.getNombre());
        assertEquals(2, response.getFormatoIds().size());
    }

    // Test crear tipo de tipo existente
   

      

    // Test de listarTipos
    
    


    // Test de actualizarTipo
    @Test
    void testActualizarTipo_Exitoso() {
        Tipo tipoExistente = tipoRepository.findById(tipo.getId()).orElseThrow();
        String nuevoNombre = "Nuevo Tipo";
        Tipo tipoActualizado = tipoService.actualizarTipo(tipoExistente.getId(), nuevoNombre);

        assertNotNull(tipoActualizado);
        assertEquals(nuevoNombre, tipoActualizado.getNombre());
    }

    @Test
    void testActualizarTipo_TipoNoExistente() {
        Long tipoNoExistenteId = 999L;

        Tipo tipoActualizado = tipoService.actualizarTipo(tipoNoExistenteId, "Nuevo Nombre");

        assertNull(tipoActualizado);
    }

    // Test de obtenerFormatosPorTipo
    

    @Test
    void testObtenerFormatosPorTipo_TipoNoExistente() {
        Long tipoNoExistenteId = 999L;

        Exception exception = assertThrows(RuntimeException.class, () -> tipoService.obtenerFormatosPorTipo(tipoNoExistenteId));
        assertEquals("Tipo no encontrado", exception.getMessage());
    }

    // Test de eliminarTipo
    @Test
    void testEliminarTipo_Exitoso() {
        Tipo tipoExistente = tipoRepository.findById(tipo.getId()).orElseThrow();

        tipoService.eliminarTipo(tipoExistente.getId());

        assertFalse(tipoRepository.existsById(tipoExistente.getId()));
    }

    // Test eliminar tipo existente
    
}

