package es.santander.ascender.final_grupo04.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.santander.ascender.final_grupo04.model.Prestamo;

public interface PrestamoRepository extends JpaRepository<Prestamo,Long> {

    boolean existsByNombreUsuarioAndActivoTrue(String nombre); // Verifica si la persona tiene un préstamo activo.

    List<Prestamo> findByNombre(String nombre); // Busca todos los préstamos de un usuario.

}
