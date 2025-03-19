package es.santander.ascender.final_grupo04.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.santander.ascender.final_grupo04.DTO.ItemPrestamoDTO;
import es.santander.ascender.final_grupo04.DTO.PrestamoResponseDTO;
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

    /**
     * Crea un préstamo para un ítem disponible.
     */
    public PrestamoResponseDTO crearPrestamo(Long itemId, String persona, LocalDate fechaPrevistaDevolucion) {
        if (persona == null || persona.isBlank()) {
            throw new IllegalArgumentException("La persona no puede estar vacía");
        }
        if (fechaPrevistaDevolucion == null || fechaPrevistaDevolucion.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha prevista de devolución no puede ser nula o en el pasado");
        }

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

        item.setEstado(false);
        item.setPrestamo(prestamo);

        prestamoRepository.save(prestamo);
        itemRepository.save(item);

        return convertirADTO(prestamo);
    }

    /**
     * Devuelve un ítem prestado.
     */
    public PrestamoResponseDTO devolverItem(Long prestamoId) {
        Prestamo prestamo = prestamoRepository.findById(prestamoId)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado"));

        if (prestamo.getFechaDevolucion() != null) {
            throw new RuntimeException("El préstamo ya fue devuelto");
        }

        prestamo.setFechaDevolucion(LocalDate.now());
        prestamo.setActivo(false);

        // Cambiar estado de los ítems asociados a "Disponible"
        if (prestamo.getItems() != null) {
            for (Item item : prestamo.getItems()) {
                item.setEstado(true);
                item.setPrestamo(null);
                itemRepository.save(item);
            }
        }

        prestamoRepository.save(prestamo);
        return convertirADTO(prestamo);
    }

    /**
     * Lista los préstamos activos, filtrando por persona o fecha.
     */
    public List<PrestamoResponseDTO> listarPrestamosActivos(String persona, LocalDate fecha) {
        List<Prestamo> prestamos;

        if (persona != null && !persona.isBlank()) {
            prestamos = prestamoRepository.findByPersonaAndActivoTrue(persona);
        } else if (fecha != null) {
            prestamos = prestamoRepository.findByFechaPrestamoAndActivoTrue(fecha);
        } else {
            prestamos = prestamoRepository.findByActivoTrue();
        }

        return prestamos.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Convierte un `Prestamo` en `PrestamoResponseDTO`
     */
    private PrestamoResponseDTO convertirADTO(Prestamo prestamo) {
        List<ItemPrestamoDTO> itemsDTO = prestamo.getItems().stream()
                .map(i -> new ItemPrestamoDTO(i.getId(), i.getTitulo(), i.getUbicacion()))
                .collect(Collectors.toList());

        return new PrestamoResponseDTO(
                prestamo.getId(),
                prestamo.getPersona(),
                prestamo.getFechaPrestamo(),
                prestamo.getFechaPrevistaDevolucion(),
                prestamo.getFechaDevolucion(),
                prestamo.isActivo(),
                itemsDTO
        );
    }
}
