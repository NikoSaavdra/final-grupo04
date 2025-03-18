package es.santander.ascender.final_grupo04.controller;

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

import es.santander.ascender.final_grupo04.model.Tipo;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tipo")
public class TipoController {


    @PostMapping
        public ResponseEntity<Tipo> createTipo(@Valid @RequestBody Tipo tipo) {
        Tipo createdTipo = tipoService.createTipo(tipo);
        return new ResponseEntity<>(createdTipo, HttpStatus.CREATED);
    }

    @GetMapping
        public ResponseEntity<List<Tipo>> getAllTipos() {
        List<Tipo> tipos = tipoService.getAllTipos();
        return new ResponseEntity<>(tipos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
        public ResponseEntity<Tipo> getTipoById(@PathVariable Long id) {
        Optional<Tipo> tipo = tipoService.getTipoById(id);
        return tipo.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
        public ResponseEntity<Tipo> updateTipo(@PathVariable Long id, @Valid @RequestBody Tipo tipoDetails) {
        Tipo updatedTipo = tipoService.updateTipo(id, tipoDetails);
        if (updatedTipo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedTipo, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteTipo(@PathVariable Long id) {
        tipoService.deleteTipo(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
