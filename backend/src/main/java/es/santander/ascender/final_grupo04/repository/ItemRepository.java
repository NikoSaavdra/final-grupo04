package es.santander.ascender.final_grupo04.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.santander.ascender.final_grupo04.model.Item;

public interface ItemRepository extends JpaRepository<Item,Long>{

    List<Item> findByDisponibleTrue(); // Busca todos los items disponibles para préstamo.

}
