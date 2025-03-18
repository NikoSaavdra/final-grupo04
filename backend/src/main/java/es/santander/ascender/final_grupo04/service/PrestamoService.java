package es.santander.ascender.final_grupo04.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.santander.ascender.final_grupo04.model.Item;
import es.santander.ascender.final_grupo04.model.Prestamo;
import es.santander.ascender.final_grupo04.repository.ItemRepository;
import es.santander.ascender.final_grupo04.repository.PrestamoRepository;

@Service
@Transactional
public class PrestamoService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Transactional
    public Prestamo crearPrestamo(Long itemId, String persona, LocalDate fechaPrevistaDevolucion) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item no encontrado"));

        if (!item.isEstado()) {
            throw new RuntimeException("El item no está disponible");
        }

        Prestamo prestamo = new Prestamo();
        prestamo.setPersona(persona);
        prestamo.setFechaPrestamo(LocalDate.now());
        prestamo.setFechaPrevistaDevolucion(fechaPrevistaDevolucion);
        prestamo.setActivo(true);
        prestamo.setItems(List.of(item));

        // Cambiar estado del ítem a "No disponible"
        item.setEstado(false);
        item.setPrestamo(prestamo);

        // Guardar ítem y préstamo
        itemRepository.save(item);
        return prestamoRepository.save(prestamo);
    }

    @Transactional
    public Prestamo devolverItem(Long prestamoId) {
        Prestamo prestamo = prestamoRepository.findById(prestamoId)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado"));

        if (prestamo.getFechaDevolucion() != null) {
            throw new RuntimeException("El préstamo ya fue devuelto");
        }

        // Marcar como devuelto
        prestamo.setFechaDevolucion(LocalDate.now());
        prestamo.setActivo(false);

        // Cambiar estado de los ítems asociados a "Disponible"
        for (Item item : prestamo.getItems()) {
            item.setEstado(true);
            item.setPrestamo(null);
            itemRepository.save(item);
        }

        return prestamoRepository.save(prestamo);
    }

    public List<Prestamo> listarPrestamosActivos(String persona, LocalDate fecha) {
        if (persona != null) {
            return prestamoRepository.findByPersonaAndActivoTrue(persona);
        } else if (fecha != null) {
            return prestamoRepository.findByFechaPrestamoAndActivoTrue(fecha);
        } else {
            return prestamoRepository.findByActivoTrue();
        }
    }

}
