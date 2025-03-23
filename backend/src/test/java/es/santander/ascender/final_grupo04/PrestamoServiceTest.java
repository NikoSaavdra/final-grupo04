package es.santander.ascender.final_grupo04;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import es.santander.ascender.final_grupo04.DTO.PrestamoResponseDTO;
import es.santander.ascender.final_grupo04.model.Formato;
import es.santander.ascender.final_grupo04.model.Item;
import es.santander.ascender.final_grupo04.model.Prestamo;
import es.santander.ascender.final_grupo04.model.Tipo;
import es.santander.ascender.final_grupo04.repository.FormatoRepository;
import es.santander.ascender.final_grupo04.repository.ItemRepository;
import es.santander.ascender.final_grupo04.repository.PrestamoRepository;
import es.santander.ascender.final_grupo04.repository.TipoRepository;
import es.santander.ascender.final_grupo04.service.PrestamoService;

@SpringBootTest
public class PrestamoServiceTest {

    @Autowired
    private PrestamoService prestamoService;

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private FormatoRepository formatoRepository;

    @Autowired
    private TipoRepository tipoRepository;

    private Item item;
    private Prestamo prestamo;

    @BeforeEach
    void setUp() {

        prestamoRepository.deleteAll();
        itemRepository.deleteAll();

        // 🔹 Buscar un Tipo y Formato existentes en la BD (deben estar precargados en `data.sql`)
        Tipo tipo = tipoRepository.findByNombre("Libro")
                .orElseThrow(() -> new RuntimeException("No se encontró el tipo 'Libro' en la BD"));

        Formato formato = formatoRepository.findById(4L)
                .orElseThrow(() -> new RuntimeException("No se encontró el formato 'Papel' en la BD"));

        item = new Item();
        item.setTitulo("Libro de prueba");
        item.setUbicacion("Estante A");
        item.setEstado(true); // Disponible
        item.setTipo(tipo);
        item.setFormato(formato);

        item = itemRepository.save(item);  // Guardar el `Item` antes de asociarlo a `Prestamo`

        // ✅ Crear y guardar un nuevo `Prestamo`
        prestamo = new Prestamo();
        prestamo.setPersona("Juan Pérez");
        prestamo.setFechaPrestamo(LocalDate.now());
        prestamo.setFechaPrevistaDevolucion(LocalDate.now().plusDays(7));
        prestamo.setActivo(true);
        prestamo = prestamoRepository.save(prestamo);
    }

    @Test
    void testCrearPrestamo_Exitoso() {
        // Crear un préstamo para un ítem disponible
        PrestamoResponseDTO response = prestamoService.crearPrestamo(item.getId(), "María López", LocalDate.now().plusDays(7));

        // Validar que el préstamo se creó correctamente
        assertNotNull(response, "El préstamo no se creó correctamente.");
        assertEquals("María López", response.getPersona(), "La persona asociada al préstamo no es correcta.");
        assertTrue(response.isActivo(), "El préstamo debe estar activo.");

        // Validar que el ítem está asociado al préstamo
        assertTrue(response.getItems().stream()
                .anyMatch(itemDTO -> itemDTO.getId().equals(item.getId())),
                "El ítem no está asociado al préstamo.");

        // Validar que el estado del ítem cambió a "no disponible"
        Item updatedItem = itemRepository.findById(item.getId())
                .orElseThrow(() -> new RuntimeException("El ítem no fue encontrado después del préstamo."));
        assertFalse(updatedItem.isEstado(), "El ítem debe estar marcado como no disponible.");
    }

    @Test
    void testCrearPrestamo_ItemNoDisponible() {
        // Marcar el ítem como no disponible
        item.setEstado(false);
        itemRepository.save(item);

        // Intentar crear un préstamo con un ítem no disponible
        Exception exception = assertThrows(RuntimeException.class,
                () -> prestamoService.crearPrestamo(item.getId(), "Juan Pérez", LocalDate.now().plusDays(7)));
        assertEquals("El item no está disponible", exception.getMessage());
    }

    @Test
    void testCrearPrestamo_FechaDevolucionInvalida() {
        // Intentar crear un préstamo con una fecha de devolución inválida
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> prestamoService.crearPrestamo(item.getId(), "Juan Pérez", LocalDate.now().minusDays(1)));
        assertEquals("La fecha prevista de devolución no puede ser nula o en el pasado", exception.getMessage());
    }

    @Test
    void testDevolverItem_Exitoso() {
        // Realizamos la devolución del préstamo inicial
        PrestamoResponseDTO response = prestamoService.devolverItem(prestamo.getId());

        // Verificamos que la fecha de devolución se haya asignado
        assertNotNull(response.getFechaDevolucion());
        assertFalse(response.isActivo());

        // Verificamos que el estado del ítem se haya actualizado a "Disponible"
        Item updatedItem = itemRepository.findById(item.getId()).orElseThrow();
        assertTrue(updatedItem.isEstado()); // El ítem debe estar disponible
    }
}
