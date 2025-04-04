package es.santander.ascender.final_grupo04;

import java.util.List;

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

import es.santander.ascender.final_grupo04.DTO.ItemDTO;
import es.santander.ascender.final_grupo04.DTO.ItemResponseDTO;
import es.santander.ascender.final_grupo04.model.Item;
import es.santander.ascender.final_grupo04.model.Tipo;
import es.santander.ascender.final_grupo04.repository.ItemRepository;
import es.santander.ascender.final_grupo04.repository.TipoRepository;
import es.santander.ascender.final_grupo04.service.ItemService;

@SpringBootTest
@Transactional
public class ItemServiceTest {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private TipoRepository tipoRepository;

    private Tipo tipo;
    private Item item;

    @BeforeEach
    void setUp() {
        tipo = tipoRepository.findByNombre("Música")
                .orElseThrow(() -> new RuntimeException("Tipo 'Música' no encontrado"));

        item = itemRepository.findAll().stream()
                .filter(i -> i.getTipo().getNombre().equals("Música"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No hay ítems tipo Música para test"));
    }

    @Test
    void testCrearItemConFormato_Exitoso() {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setTitulo("Nuevo Álbum");
        itemDTO.setUbicacion("Estante D");
        itemDTO.setTipoId(tipo.getId());
        itemDTO.setFormato("CD");

        ItemResponseDTO response = itemService.crearItemConFormato(itemDTO);

        assertNotNull(response);
        assertEquals(itemDTO.getTitulo(), response.getTitulo());
        assertEquals(itemDTO.getUbicacion(), response.getUbicacion());
        assertEquals(tipo.getNombre(), response.getTipo());
        assertEquals("CD", response.getFormato());
    }

    @Test
    void testListarItemDisponibles_Exitoso() {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setTitulo("Álbum Libre");
        itemDTO.setUbicacion("Estante X");
        itemDTO.setTipoId(tipo.getId());
        itemDTO.setFormato("CD");

        itemService.crearItemConFormato(itemDTO);

        List<ItemResponseDTO> disponibles = itemService.listarItemDisponibles();
        assertFalse(disponibles.isEmpty());
        assertTrue(disponibles.stream().anyMatch(i -> i.getTitulo().equals("Álbum Libre")));
    }

    @Test
    void testBuscarItemsPorTituloYTipo() {
        List<ItemResponseDTO> items = itemService.buscarItems("Thriller", "Música", null, "titulo");
        assertFalse(items.isEmpty());
        assertTrue(items.get(0).getTitulo().contains("Thriller"));
    }

    @Test
    void testActualizarItem_Exitoso() {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setTitulo("Jazz Vibes");
        itemDTO.setUbicacion("Estante J");
        itemDTO.setTipoId(tipo.getId());
        itemDTO.setFormato("CD");

        ItemResponseDTO updated = itemService.actualizarItem(item.getId(), itemDTO);

        assertNotNull(updated);
        assertEquals("Jazz Vibes", updated.getTitulo());
        assertEquals("Estante J", updated.getUbicacion());
        assertEquals("CD", updated.getFormato());
    }

    @Test
    void testCrearItemConFormato_FormatoInvalido() {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setTitulo("Error Item");
        itemDTO.setUbicacion("Estante Z");
        itemDTO.setTipoId(tipo.getId());
        itemDTO.setFormato("PDF"); // PDF no es válido para tipo Música

        Exception ex = assertThrows(RuntimeException.class, () -> itemService.crearItemConFormato(itemDTO));
        assertEquals("Formato no válido para el tipo seleccionado", ex.getMessage());
    }

}
