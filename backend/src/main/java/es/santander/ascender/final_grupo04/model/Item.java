package es.santander.ascender.final_grupo04.model;


import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Length(max = 100)
    private String titulo;  
    private String ubicacion;
    private boolean estado;
    
    @ManyToOne
    private Tipo tipo;

    @ManyToOne
    private Prestamo prestamo;
    
    public Item() {
    }
    public Item(Long id, @Length(max = 100) String titulo, String ubicacion, boolean estado, Tipo tipo,
            Prestamo prestamo) {
        this.id = id;
        this.titulo = titulo;
        this.ubicacion = ubicacion;
        this.estado = estado;
        this.tipo = tipo;
        this.prestamo = prestamo;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getUbicacion() {
        return ubicacion;
    }
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
    public boolean isEstado() {
        return estado;
    }
    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    public Tipo getTipo() {
        return tipo;
    }
    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }
    public Prestamo getPrestamo() {
        return prestamo;
    }
    public void setPrestamo(Prestamo prestamo) {
        this.prestamo = prestamo;
    }
    
   

}
