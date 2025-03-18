package es.santander.ascender.final_grupo04.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.santander.ascender.final_grupo04.model.Prestamo;
import es.santander.ascender.final_grupo04.service.PrestamoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/prestamo")
public class PrestamoController {

    @Autowired
    private PrestamoService  prestamoService;

    @PostMapping
        public ResponseEntity<Prestamo> crearPrestamo(@Valid @RequestBody Long itemId) {
        Prestamo createdPrestamo = prestamoService.crearPrestamo(itemId);
        return new ResponseEntity<>(createdPrestamo, HttpStatus.CREATED);
    }

    @GetMapping("/historial/nombre")
        public ResponseEntity<List<Prestamo>> listaraHistorialDePrestamos(String nombre) {
        List<Prestamo> prestamos = prestamoService.listarHistorialDePrestamos(nombre);
        return new ResponseEntity<>(prestamos, HttpStatus.OK);
    }

    @GetMapping("/historial/fecha")
        public ResponseEntity<List<Prestamo>> listaraHistorialDePrestamos(LocalDate fechaPrestamo) {
        List<Prestamo> prestamos = prestamoService.listarHistorialDePrestamos(fechaPrestamo);
        return new ResponseEntity<>(prestamos, HttpStatus.OK);
    }

    @GetMapping("/item/disponible")
        public ResponseEntity<List<Prestamo>> listarItemDisponibles () {
        List<Prestamo> prestamos = prestamoService.listarItemDisponibles ();
        return new ResponseEntity<>(prestamos, HttpStatus.OK);
    }

    @PutMapping("/devolver")
    public ResponseEntity<Prestamo> devolverItem (@PathVariable Long id) {
        Prestamo devolucionPrestamo = prestamoService.devolverItem(id);
        if (devolucionPrestamo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(devolucionPrestamo, HttpStatus.OK);
    }




}
