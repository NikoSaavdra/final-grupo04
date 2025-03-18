package es.santander.ascender.final_grupo04.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.santander.ascender.final_grupo04.model.Item;
import es.santander.ascender.final_grupo04.repository.ItemRepository;

@Service
@Transactional
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public Item crearItem(Item item) {
        item.setEstado(true);
        return itemRepository.save(item);
    }

    @Transactional(readOnly = true)
    public List<Item> listarItemDisponibles() {

        return itemRepository.findByEstadoTrue();
    }

    @Transactional(readOnly = true)
    public Optional<Item> buscarItem(Long id) {
        return itemRepository.findById(id);
    }

    @Transactional
    public Item actualizarItem(Long id, Item itemDetails) {
        Optional<Item> itemOptional = itemRepository.findById(id);
        if (itemOptional.isPresent()) {
            Item item = itemOptional.get();
            item.setTitulo(itemDetails.getTitulo());
            item.setUbicacion(itemDetails.getUbicacion());
            item.setTipo(itemDetails.getTipo());
            return itemRepository.save(item);
        }
        return null;
    }

    @Transactional
    public void eliminarItem(Long id) {
        itemRepository.deleteById(id);
    }

    public List<Item> buscarItems(String titulo, String tipo, String ubicacion) {
        // Implementar un método personalizado en el repositorio para manejar la búsqueda avanzada
        return itemRepository.buscarPorCriterios(titulo, tipo, ubicacion);
    }
}
