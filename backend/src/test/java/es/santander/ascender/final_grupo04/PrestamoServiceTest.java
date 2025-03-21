package es.santander.ascender.final_grupo04;


import es.santander.ascender.final_grupo04.DTO.PrestamoResponseDTO;
import es.santander.ascender.final_grupo04.model.Item;
import es.santander.ascender.final_grupo04.model.Prestamo;
import es.santander.ascender.final_grupo04.repository.ItemRepository;
import es.santander.ascender.final_grupo04.repository.PrestamoRepository;
import es.santander.ascender.final_grupo04.service.PrestamoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PrestamoServiceTest {

    @Autowired
    private PrestamoService prestamoService;

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private ItemRepository itemRepository;

    private Item item;
    private Prestamo prestamo;

    @BeforeEach
    void setUp() {
    
        item = itemRepository.findById(1L).orElseThrow(() -> new RuntimeException("Item no encontrado"));

        prestamo = prestamoRepository.findById(1L).orElseThrow(() -> new RuntimeException("Préstamo no encontrado"));
    }

    @Test
    void testCrearPrestamo_Exitoso() {
        // Verificamos que el préstamo inicial existe y está activo
        assertNotNull(prestamo);
        assertTrue(prestamo.isActivo());

        // Creamos el préstamo con la persona "Juan Pérez" y la fecha prevista de devolución
        PrestamoResponseDTO response = prestamoService.crearPrestamo(1L, "Juan Pérez", LocalDate.now().plusDays(7));

    
        assertNotNull(response);
        assertEquals("Juan Pérez", response.getPersona());

        assertTrue(response.getItems().stream().anyMatch(itemDTO -> itemDTO.getId().equals(item.getId())));
    }

    @Test
    void testDevolverItem_Exitoso() {
        // Realizamos la devolución del préstamo existente
        PrestamoResponseDTO response = prestamoService.devolverItem(prestamo.getId());

        // Verificamos que la fecha de devolución se haya asignado correctamente
        assertNotNull(response.getFechaDevolucion());
        assertTrue(response.getFechaDevolucion().isBefore(LocalDate.now().plusDays(1))); // La devolución debe ser en la fecha actual o anterior

        // Verificamos que el estado del ítem se haya actualizado a "Disponible"
        item = itemRepository.findById(1L).orElseThrow(() -> new RuntimeException("Item no encontrado"));
        assertTrue(item.isEstado());  // El ítem debe estar disponible después de la devolución
    }
}
