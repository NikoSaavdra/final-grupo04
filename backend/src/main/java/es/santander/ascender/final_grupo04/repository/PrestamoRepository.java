package es.santander.ascender.final_grupo04.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.santander.ascender.final_grupo04.model.Prestamo;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

    // Lista de préstamos activos
    List<Prestamo> findByActivoTrue();

    // Lista de préstamos activos por persona
    List<Prestamo> findByPersonaAndActivoTrue(String persona);

    // Lista de préstamos activos por fecha
    List<Prestamo> findByFechaPrestamoAndActivoTrue(LocalDate fechaPrestamo);

    // Buscar préstamos por nombre de persona
    List<Prestamo> findByPersona(String persona);

    // Buscar préstamos por fecha de préstamo
    List<Prestamo> findByFechaPrestamo(LocalDate fechaPrestamo);
}
