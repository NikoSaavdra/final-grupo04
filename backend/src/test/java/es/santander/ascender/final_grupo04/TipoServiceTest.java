package es.santander.ascender.final_grupo04;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import es.santander.ascender.final_grupo04.repository.ItemRepository;
import es.santander.ascender.final_grupo04.repository.TipoRepository;
import es.santander.ascender.final_grupo04.service.TipoService;

@SpringBootTest
@Transactional
public class TipoServiceTest {

    @Autowired
    private TipoService tipoService;

    @Autowired
    private TipoRepository tipoRepository;

    @Autowired
    private FormatoRepository formatoRepository;

    @Autowired
    private ItemRepository itemRepository;

    private Formato formato1, formato2;

    @BeforeEach
    void setUp() {
        // Eliminar ítems relacionados con el tipo para evitar constraint violations
        itemRepository.deleteAll();

        // Recuperar formatos conocidos de la BD
        formato1 = formatoRepository.findById(4L)
                .orElseThrow(() -> new RuntimeException("Formato 'Papel' no encontrado"));
        formato2 = formatoRepository.findById(5L)
                .orElseThrow(() -> new RuntimeException("Formato 'PDF' no encontrado"));
    }

    @Test
    void testCrearTipo_Exitoso() {
        String nombreUnico = "TestTipo_" + System.currentTimeMillis();
        TipoDTO tipoDTO = new TipoDTO();
        tipoDTO.setNombre(nombreUnico);
        tipoDTO.setFormatoIds(List.of(formato1.getId(), formato2.getId()));

        TipoDTO creado = tipoService.crearTipo(tipoDTO);

        assertNotNull(creado);
        assertEquals(nombreUnico, creado.getNombre());
        assertEquals(2, creado.getFormatoIds().size());
    }

    @Test
    void testCrearTipo_TipoExistente() {
        TipoDTO tipoDTO = new TipoDTO();
        tipoDTO.setNombre("Libro"); // ya existe en data.sql
        tipoDTO.setFormatoIds(List.of(formato1.getId(), formato2.getId()));

        Exception ex = assertThrows(RuntimeException.class, () -> tipoService.crearTipo(tipoDTO));
        assertEquals("El tipo con el nombre 'Libro' ya existe.", ex.getMessage());
    }

    @Test
    void testListarTipos() {
        List<TipoFormatoDTO> tipos = tipoService.listarTipos();

        assertNotNull(tipos);
        assertFalse(tipos.isEmpty());

        List<TipoFormatoDTO> libros = tipos.stream()
                .filter(t -> t.getNombre().equals("Libro"))
                .collect(Collectors.toList());

        assertEquals(1, libros.size());
        assertEquals(3, libros.get(0).getFormatos().size()); // Libro tiene 3 formatos
    }

    @Test
    void testActualizarTipo_Exitoso() {
        Tipo tipo = tipoRepository.findByNombre("Libro")
                .orElseThrow(() -> new RuntimeException("Tipo 'Libro' no encontrado"));

        String nuevoNombre = "NuevoTipo_" + System.currentTimeMillis();
        Tipo actualizado = tipoService.actualizarTipo(tipo.getId(), nuevoNombre);

        assertNotNull(actualizado);
        assertEquals(nuevoNombre, actualizado.getNombre());
    }

    @Test
    void testActualizarTipo_TipoNoExistente() {
        Long idInexistente = 999L;

        Exception ex = assertThrows(RuntimeException.class,
                () -> tipoService.actualizarTipo(idInexistente, "OtroNombre"));

        assertEquals("Tipo no encontrado", ex.getMessage());
    }

    @Test
    void testObtenerFormatosPorTipo_Exitoso() {
        Tipo tipo = tipoRepository.findByNombre("Libro")
                .orElseThrow(() -> new RuntimeException("Tipo 'Libro' no encontrado"));

        List<String> formatos = tipoService.obtenerFormatosPorTipo(tipo.getId());

        assertNotNull(formatos);
        assertEquals(3, formatos.size());
        assertTrue(formatos.contains("PDF"));
        assertTrue(formatos.contains("Papel"));
        assertTrue(formatos.contains("eBook"));
    }

    @Test
    void testObtenerFormatosPorTipo_TipoNoExistente() {
        Long idInexistente = 999L;

        Exception ex = assertThrows(RuntimeException.class,
                () -> tipoService.obtenerFormatosPorTipo(idInexistente));

        assertEquals("Tipo no encontrado", ex.getMessage());
    }

    @Test
    void testEliminarTipo_TipoNoExistente() {
        Long idInexistente = 999L;

        Exception ex = assertThrows(RuntimeException.class,
                () -> tipoService.eliminarTipo(idInexistente));

        assertEquals("Tipo no encontrado", ex.getMessage());
    }
}
