package es.santander.ascender.final_grupo04.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.santander.ascender.final_grupo04.DTO.ItemDTO;
import es.santander.ascender.final_grupo04.DTO.ItemResponseDTO;
import es.santander.ascender.final_grupo04.service.ItemService;
import jakarta.validation.Valid;

@CrossOrigin(origins = { "http://localhost:4200", "https://appequipo4storage.z16.web.core.windows.net" })
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
     * Busca ítems con filtros y paginación.
     */
    @GetMapping("/buscar")
    public ResponseEntity<Page<ItemResponseDTO>> buscarItemsPaginado(
            @RequestParam(defaultValue = "") String titulo,
            @RequestParam(defaultValue = "") String tipo,
            @RequestParam(defaultValue = "") String ubicacion,
            @PageableDefault(size = 10, sort = "titulo") Pageable pageable
    ) {
        Page<ItemResponseDTO> result = itemService.buscarItemsPaginado(titulo, tipo, ubicacion, pageable);
        return new ResponseEntity<>(result, HttpStatus.OK);
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
