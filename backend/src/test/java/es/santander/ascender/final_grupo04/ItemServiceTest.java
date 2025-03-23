package es.santander.ascender.final_grupo04;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import es.santander.ascender.final_grupo04.DTO.ItemDTO;
import es.santander.ascender.final_grupo04.DTO.ItemResponseDTO;
import es.santander.ascender.final_grupo04.model.Item;
import es.santander.ascender.final_grupo04.model.Tipo;
import es.santander.ascender.final_grupo04.repository.ItemRepository;
import es.santander.ascender.final_grupo04.repository.TipoRepository;
import es.santander.ascender.final_grupo04.service.ItemService;

@SpringBootTest
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
        // Cargar un tipo existente de la base de datos, por ejemplo, el tipo con id 1
        // que podría ser "Música"
        tipo = tipoRepository.findById(1L).orElseThrow(() -> new RuntimeException("Tipo no encontrado"));

        // Cargar un ítem existente, por ejemplo, el ítem con id 1 (Álbum de Rock)
        item = itemRepository.findById(1L).orElseThrow(() -> new RuntimeException("Ítem no encontrado"));
    }

    @Test
    void testCrearItemConFormato_Exitoso() {
        // Crear un DTO de item con tipo y formato válidos
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setTitulo("Nuevo Álbum");
        itemDTO.setUbicacion("Estante D");
        itemDTO.setTipoId(tipo.getId());
        itemDTO.setFormato("1"); // Usando el formato "CD" para tipo "Música"

        // Llamada al servicio para crear el ítem
        ItemResponseDTO response = itemService.crearItemConFormato(itemDTO);

        // Verificamos que el ítem se haya creado correctamente
        assertNotNull(response);
        assertEquals(itemDTO.getTitulo(), response.getTitulo());
        assertEquals(itemDTO.getUbicacion(), response.getUbicacion());
        assertEquals(tipo.getNombre(), response.getTipo()); // "Música"
        assertEquals("CD", response.getFormato()); // Formato validado
    }

    @Test
    void testListarItemDisponibles_Exitoso() {
        // Crear un ítem adicional para probar el listado de ítems disponibles
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setTitulo("Nuevo Álbum");
        itemDTO.setUbicacion("Estante E");
        itemDTO.setTipoId(tipo.getId());
        itemDTO.setFormato("1");

        itemService.crearItemConFormato(itemDTO);

        // Llamamos al servicio para listar los ítems disponibles
        List<ItemResponseDTO> items = itemService.listarItemDisponibles();

        // Verificamos que los ítems disponibles sean mayores que 0
        assertFalse(items.isEmpty());
    }

    @Test
    void testBuscarItemsPorTituloYTipo() {
        // Buscar ítems con un título específico y tipo "Música"
        List<ItemResponseDTO> items = itemService.buscarItems("Álbum de Rock", "Música", null, "titulo");

        // Verificamos que la búsqueda haya devuelto el ítem correcto
        assertEquals(1, items.size());
        assertEquals("Álbum de Rock", items.get(0).getTitulo());
    }

    @Test
    void testActualizarItem_Exitoso() {
        // Crear un DTO de item con nuevos valores para actualizar
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setTitulo("Álbum de Jazz");
        itemDTO.setUbicacion("Estante F");
        itemDTO.setTipoId(tipo.getId());
        itemDTO.setFormato("CD"); // Usar el nombre del formato

        ItemResponseDTO response = itemService.actualizarItem(item.getId(), itemDTO);

        // Verificamos que la actualización haya sido exitosa
        assertNotNull(response);
        assertEquals(itemDTO.getTitulo(), response.getTitulo());
        assertEquals(itemDTO.getUbicacion(), response.getUbicacion());
        assertEquals("CD", response.getFormato()); // Verificar que el formato sea "CD"
    }

    @Test
    void testCrearItemConFormato_FormatoInvalido() {
        // Crear un DTO con un formato no asociado al tipo "Música"
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setTitulo("Ítem Inválido");
        itemDTO.setUbicacion("Estante Z");
        itemDTO.setTipoId(tipo.getId());
        itemDTO.setFormato("5"); // Formato "PDF", no válido para tipo "Música"

        // Verificar que se lanza una excepción
        Exception exception = assertThrows(RuntimeException.class, () -> itemService.crearItemConFormato(itemDTO));
        assertEquals("Formato no válido para el tipo seleccionado", exception.getMessage());
    }

    @Test
    void testEliminarItem_Exitoso() {
        // Eliminar el ítem que hemos cargado anteriormente
        Long id = item.getId();
        itemService.eliminarItem(id);

        // Verificamos que el ítem ya no existe
        assertFalse(itemRepository.existsById(id));
    }
}
