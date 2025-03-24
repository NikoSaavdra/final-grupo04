package es.santander.ascender.final_grupo04.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.santander.ascender.final_grupo04.DTO.ItemDTO;
import es.santander.ascender.final_grupo04.DTO.ItemResponseDTO;
import es.santander.ascender.final_grupo04.model.Formato;
import es.santander.ascender.final_grupo04.model.Item;
import es.santander.ascender.final_grupo04.model.Tipo;
import es.santander.ascender.final_grupo04.repository.ItemRepository;
import es.santander.ascender.final_grupo04.repository.TipoRepository;

@Service
@Transactional
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private TipoRepository tipoRepository;

    /**
     * Crea un nuevo ítem con un formato válido.
     */
    public ItemResponseDTO crearItemConFormato(ItemDTO itemDTO) {
        Tipo tipo = tipoRepository.findById(itemDTO.getTipoId())
                .orElseThrow(() -> new RuntimeException("Tipo no encontrado"));

        // Asegurarse de que la lista de formatos esté cargada
        List<Formato> formatos = tipo.getFormato();
        if (formatos == null || formatos.isEmpty()) {
            throw new RuntimeException("El tipo no tiene formatos asociados");
        }

        System.out.println(tipo.getNombre());
        System.out.println(tipo.getId());
        //es una lista
        System.out.println("el formato es :" + itemDTO.getFormato());
        System.out.println(tipo.getFormato());
        System.out.println("*********");
        Formato formato = tipo.getFormato().stream()
                .peek(System.out::println)
                .filter(f -> f.getId().equals(Long.parseLong(itemDTO.getFormato())))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Formato no válido para el tipo seleccionado"));

        Item item = new Item();
        item.setTitulo(itemDTO.getTitulo());
        item.setUbicacion(itemDTO.getUbicacion());
        item.setTipo(tipo);
        item.setFormato(formato);
        item.setEstado(true);
        item.setFechaAdquisicion(LocalDate.now());

        itemRepository.save(item);
        return convertirADTO(item);
    }

    /**
     * Lista todos los ítems disponibles.
     */
    @Transactional(readOnly = true)
    public List<ItemResponseDTO> listarItemDisponibles() {
        return itemRepository.findByEstadoTrue().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca ítems por título, tipo y ubicación, con opción de ordenación.
     */
    @Transactional(readOnly = true)
    public List<ItemResponseDTO> buscarItems(String titulo, String tipo, String ubicacion, String ordenarPor) {
        // Validar parámetro de ordenación
        Sort sort = Sort.unsorted();
        if (ordenarPor != null && !ordenarPor.isBlank()) {
            sort = Sort.by(Sort.Direction.ASC, ordenarPor);
        }

        return itemRepository.buscarPorCriterios(titulo, tipo, ubicacion, sort).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Actualiza un ítem existente.
     */
    @Transactional
    public ItemResponseDTO actualizarItem(Long id, ItemDTO itemDTO) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ítem no encontrado"));

        Tipo tipo = tipoRepository.findById(itemDTO.getTipoId())
                .orElseThrow(() -> new RuntimeException("Tipo no encontrado"));

        Formato formato = tipo.getFormato().stream()
                .filter(f -> f.getNombre().equalsIgnoreCase(itemDTO.getFormato()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Formato no válido para el tipo seleccionado"));

        item.setTitulo(itemDTO.getTitulo());
        item.setUbicacion(itemDTO.getUbicacion());
        item.setTipo(tipo);
        item.setFormato(formato);

        itemRepository.save(item);
        return convertirADTO(item);
    }

    /**
     * Convierte un `Item` en `ItemResponseDTO`
     */
    private ItemResponseDTO convertirADTO(Item item) {
        return new ItemResponseDTO(
                item.getId(),
                item.getTitulo(),
                item.getUbicacion(),
                item.isEstado(),
                item.getTipo().getNombre(),
                item.getFormato().getNombre(),
                item.getFechaADquisicion()
        );
    }

    @Transactional
    public void eliminarItem(Long id) {
        if (!itemRepository.existsById(id)) {
            throw new RuntimeException("El ítem con ID " + id + " no existe.");
        }
        itemRepository.deleteById(id);
    }

}
