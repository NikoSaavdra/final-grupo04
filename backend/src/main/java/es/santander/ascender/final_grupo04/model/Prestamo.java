package es.santander.ascender.final_grupo04.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String persona;
    private LocalDate fechaPrestamo;
    private LocalDate fechaPrevistaDevolucion;
    private LocalDate fechaDevolucion;
    private boolean activo;

    @OneToMany
    private Item item;

    public Prestamo() {
    }

    public Prestamo(Long id, String persona, LocalDate fechaPrestamo, LocalDate fechaPrevistaDevolucion, LocalDate fechaDevolucion, boolean activo, Item item) {
        this.id = id;
        this.persona = persona;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaPrevistaDevolucion= fechaPrevistaDevolucion;
        this.fechaDevolucion = fechaDevolucion;
        this.activo=activo;
        this.item = item;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }
    
    public LocalDate getFechaPrevistaDevolucion() {
        return fechaPrevistaDevolucion;
    }

    public void setFechaPrevistaDevolucion(LocalDate fechaPrevistaDevolucion) {
        this.fechaPrevistaDevolucion = fechaPrevistaDevolucion;
    }
    

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
 
}
