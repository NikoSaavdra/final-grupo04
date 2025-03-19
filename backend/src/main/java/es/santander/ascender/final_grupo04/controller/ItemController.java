package es.santander.ascender.final_grupo04.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import es.santander.ascender.final_grupo04.DTO.ItemDTO;
import es.santander.ascender.final_grupo04.DTO.ItemResponseDTO;
import es.santander.ascender.final_grupo04.service.ItemService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * Crea un nuevo ítem con formato y tipo validados.
     */
    @PostMapping
    public ResponseEntity<ItemResponseDTO> crearItem(@Valid @RequestBody ItemDTO itemDTO) {
        ItemResponseDTO createdItem = itemService.crearItemConFormato(itemDTO);
        return new ResponseEntity<>(createdItem, HttpStatus.CREATED);
    }

    /**
     * Lista todos los ítems disponibles.
     */
    @GetMapping
    public ResponseEntity<List<ItemResponseDTO>> listarItemsDisponibles() {
        List<ItemResponseDTO> items = itemService.listarItemDisponibles();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    /**
     * Busca ítems por título, tipo y ubicación, con opción de ordenación.
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<ItemResponseDTO>> buscarItems(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) String ubicacion,
            @RequestParam(required = false, defaultValue = "titulo") String ordenarPor) {

        List<ItemResponseDTO> items = itemService.buscarItems(titulo, tipo, ubicacion, ordenarPor);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    /**
     * Actualiza un ítem existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ItemResponseDTO> actualizarItem(@PathVariable Long id, @Valid @RequestBody ItemDTO itemDTO) {
        ItemResponseDTO updatedItem = itemService.actualizarItem(id, itemDTO);
        return new ResponseEntity<>(updatedItem, HttpStatus.OK);
    }

    /**
     * Elimina un ítem por ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarItem(@PathVariable Long id) {
        itemService.eliminarItem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
