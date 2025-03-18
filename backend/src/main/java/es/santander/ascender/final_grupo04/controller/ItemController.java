package es.santander.ascender.final_grupo04.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.santander.ascender.final_grupo04.model.Item;
import es.santander.ascender.final_grupo04.service.ItemService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping
        public ResponseEntity<Item> crearItem(@Valid @RequestBody Item item) {
        Item createdItem = itemService.crearItem(item);
        return new ResponseEntity<>(createdItem, HttpStatus.CREATED);
    }

    @GetMapping
        public ResponseEntity<List<Item>> listarItemsDisponibles() {
        List<Item> items = itemService.listarItemDisponibles();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/{id}")
        public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        Optional<Item> item = itemService.getItemById(id);
        return item.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
        public ResponseEntity<Item> actualizarItem(@PathVariable Long id, @Valid @RequestBody Item itemDetails) {
        Item updatedItem = itemService.actualizarItem(id, itemDetails);
        if (updatedItem == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedItem, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
        public ResponseEntity<Void> eliminarItem(@PathVariable Long id) {
        itemService.eliminarItem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
