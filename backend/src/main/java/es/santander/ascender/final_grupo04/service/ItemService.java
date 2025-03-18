package es.santander.ascender.final_grupo04.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import es.santander.ascender.final_grupo04.model.Item;
import es.santander.ascender.final_grupo04.repository.ItemRepository;

public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> listarItemDisponibles() {

        return itemRepository.findByDisponibleTrue();
    }

    public Item crearItem(Item item) {
        item.setEstado(true); 
        return itemRepository.save(item);
    }

    public Item actualizarItem(Long id, Item datosActualizados) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item no encontrado"));

        item.setTitulo(datosActualizados.getTitulo());
        item.setUbicacion(datosActualizados.getUbicacion());
        
        item.setEstado(datosActualizados.isEstado());
        return itemRepository.save(item);
    }

    
    // Ordenar

    

    public boolean eliminarItem(Long id) {
        if (itemRepository.existsById(id)) {
            itemRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
