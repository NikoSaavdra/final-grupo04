package es.santander.ascender.final_grupo04.service;

import java.time.LocalDate;
import java.util.List;

import org.apache.el.stream.Optional;
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
    public Prestamo crearPrestamo(Long itemId) {


        Item item = itemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("item no encontrado"));


        // Crear el préstamo
        Prestamo prestamo = new Prestamo();
        prestamo.setPersona(nombre);
        prestamo.setItem(item);
        prestamo.setFechaPrestamo(LocalDate.now());
        //prestamo.setFechaPrevistaDevolucion(); // ver
        prestamo.setActivo(true);

        // Actualizar el estado del item
        item.setEstado(false);

        // Guardar el préstamo y actualizar el item
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

        // Marcar el préstamo como devuelto
        prestamo.setFechaDevolucion(LocalDate.now());
        prestamo.setActivo(false);

        // Actualizar el estado del item
        Item item = prestamo.getItem();
        item.setEstado(true);

        itemRepository.save(item);
        return prestamoRepository.save(prestamo);
    }

    public List<Prestamo> listarItemDisponibles() {   // Lista de prestamos activos
        return prestamoRepository.findByActivoTrue();
    }

    public List<Prestamo> listarHistorialDePrestamos(String nombre) {    // Lista de prestamo por persona
        return prestamoRepository.findByNombre(nombre);
    }

    public List<Prestamo> listarHistorialDePrestamos(LocalDate fechaPrestamo) {    // Lista de prestamo por persona
        return prestamoRepository.findByFecha(fechaPrestamo);
    }

     // Método para obtener un préstamo por ID
     public Prestamo obtenerPrestamoPorId(Long id) {
        Optional<Prestamo> prestamo = prestamoRepository.findById(id);
        // Retorna el Prestamo si existe, o null si no se encuentra
        return prestamo.orElse(null);
    }



}