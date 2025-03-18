package es.santander.ascender.final_grupo04.service;

import org.springframework.beans.factory.annotation.Autowired;

import es.santander.ascender.final_grupo04.repository.ItemRepository;

public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public List<item> listarItemDisponibles() {

        return itemRepository.findByDisponibleTrue();
    }

    public Item crearItem(Item item) {
        item.setDisponible(true); 
        return itemRepository.save(item);
    }

    public void actualizarItem(Long id, item datosActualizados) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item no encontrado"));

        item.setTitulo(datosActualizados.getTitulo());
        item.setUbicacion(datosActualizados.getAutor());
        
        item.setDisponible(datosActualizados.isDisponible());
        itemRepository.save(item);
    }
    
    // Ordenar

    

    public boolean deleteItem(Long id) {
        if (itemRepository.existsById(id)) {
            itemRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
