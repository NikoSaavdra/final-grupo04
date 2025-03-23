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

    private Tipo tipo;
    private Formato formato1, formato2;

    @BeforeEach
    void setUp() {
        // 🔹 1. Asegurar que los tipos no tengan referencias a formatos antes de eliminarlos
        tipoRepository.findAll().forEach(tipo -> tipo.getFormato().clear());
        tipoRepository.saveAll(tipoRepository.findAll());

        // 🔹 2. Borrar solo `tipo`, NO `formato`, ya que `data.sql` los carga
        tipoRepository.deleteAll();

        // Asegurarse de que 'Libro' esté asociado correctamente con 'Papel' y 'PDF'
        formato1 = formatoRepository.findById(4L)
                .orElseThrow(() -> new RuntimeException("Formato 'Papel' no encontrado en la BD"));

        formato2 = formatoRepository.findById(5L) // Usar formato 'PDF' (ID 5)
                .orElseThrow(() -> new RuntimeException("Formato 'PDF' no encontrado en la BD"));
        // 🔹 4. Crear un tipo si no existe y asociarlo a los formatos existentes
        tipo = tipoRepository.findByNombre("Libro")
                .orElseGet(() -> {
                    Tipo nuevoTipo = new Tipo();
                    nuevoTipo.setNombre("Libro");
                    nuevoTipo.setFormato(List.of(formato1, formato2));
                    return tipoRepository.save(nuevoTipo);
                });
    }

    /**
     * ✅ Test de creación de un tipo exitosamente
     */
    @Test
    void testCrearTipo_Exitoso() {
        String nombreUnico = "Música_" + System.currentTimeMillis();
        TipoDTO tipoDTO = new TipoDTO();
        tipoDTO.setNombre(nombreUnico);
        tipoDTO.setFormatoIds(List.of(formato1.getId(), formato2.getId()));

        TipoDTO response = tipoService.crearTipo(tipoDTO);

        assertNotNull(response);
        assertEquals(nombreUnico, response.getNombre());
        assertEquals(2, response.getFormatoIds().size());
    }

    /**
     * ✅ Test de creación de un tipo ya existente (debe lanzar una excepción)
     */
    @Test
    void testCrearTipo_TipoExistente() {
        TipoDTO tipoDTO = new TipoDTO();
        tipoDTO.setNombre(tipo.getNombre());

        Exception exception = assertThrows(RuntimeException.class, () -> tipoService.crearTipo(tipoDTO));
        assertEquals("El tipo con el nombre '" + tipo.getNombre() + "' ya existe.", exception.getMessage());
    }

    /**
     * ✅ Test de listar todos los tipos correctamente
     */
    @Test
    void testListarTipos() {
        List<TipoFormatoDTO> tipos = tipoService.listarTipos();

        assertNotNull(tipos);
        assertFalse(tipos.isEmpty());
        assertTrue(tipos.stream().anyMatch(t -> t.getNombre().equals("Libro")));

        List<TipoFormatoDTO> tipoDTOs = tipos.stream()
                .filter(t -> t.getNombre().equals("Libro"))
                .collect(Collectors.toList());

        assertEquals(1, tipoDTOs.size(), "Se esperaba un solo tipo con nombre 'Libro'");

        TipoFormatoDTO tipoDTO = tipoDTOs.get(0);  // Obtener el primer (y único) tipo llamado 'Libro'
        // Depuración: Imprimir los formatos obtenidos

        // Verificamos que tenga 2 formatos
        assertEquals(2, tipoDTO.getFormatos().size());

    }

    /**
     * ✅ Test de actualización de un tipo existente
     */
    @Test
    void testActualizarTipo_Exitoso() {
        String nuevoNombre = "Nuevo Tipo";
        Tipo tipoActualizado = tipoService.actualizarTipo(tipo.getId(), nuevoNombre);

        assertNotNull(tipoActualizado);
        assertEquals(nuevoNombre, tipoActualizado.getNombre());
    }

    /**
     * ✅ Test de actualización de un tipo que no existe (debe devolver null)
     */
    @Test
    void testActualizarTipo_TipoNoExistente() {
        Long tipoNoExistenteId = 999L;  // ID que no existe en la base de datos

        // Esperamos que se lance una RuntimeException cuando el tipo no exista
        Exception exception = assertThrows(RuntimeException.class, () -> tipoService.actualizarTipo(tipoNoExistenteId, "Nuevo Nombre"));

        // Verificar el mensaje de la excepción para asegurarse de que es el esperado
        assertEquals("Tipo no encontrado", exception.getMessage());
    }

    /**
     * ✅ Test de obtener formatos por tipo exitosamente
     */
    @Test
    void testObtenerFormatosPorTipo_Exitoso() {
        List<String> formatos = tipoService.obtenerFormatosPorTipo(tipo.getId());

        assertNotNull(formatos);
        assertEquals(2, formatos.size());
        assertTrue(formatos.contains("PDF"));
        assertTrue(formatos.contains("Papel"));
    }

    /**
     * ✅ Test de obtener formatos de un tipo que no existe (debe lanzar una
     * excepción)
     */
    @Test
    void testObtenerFormatosPorTipo_TipoNoExistente() {
        Long tipoNoExistenteId = 999L;

        Exception exception = assertThrows(RuntimeException.class, () -> tipoService.obtenerFormatosPorTipo(tipoNoExistenteId));
        assertEquals("Tipo no encontrado", exception.getMessage());
    }

    /**
     * ✅ Test de eliminación exitosa de un tipo
     */
    @Test
    void testEliminarTipo_Exitoso() {
        tipoService.eliminarTipo(tipo.getId());

        assertFalse(tipoRepository.existsById(tipo.getId()));
    }

    /**
     * ✅ Test de eliminación de un tipo que no existe (debe lanzar una
     * excepción)
     */
    @Test
    void testEliminarTipo_TipoNoExistente() {
        Long tipoNoExistenteId = 999L;

        Exception exception = assertThrows(RuntimeException.class, () -> tipoService.eliminarTipo(tipoNoExistenteId));
        assertEquals("Tipo no encontrado", exception.getMessage());
    }
}
