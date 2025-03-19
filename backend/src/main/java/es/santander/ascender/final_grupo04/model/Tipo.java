package es.santander.ascender.final_grupo04.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;

@Entity
public class Tipo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String nombre;

    @OneToMany(mappedBy = "tipo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Formato> formatos;

    @JsonIgnore
    @OneToMany(mappedBy = "tipo", cascade = CascadeType.ALL)
    private List<Item> items;

    public Tipo() {
    }

    public Tipo(Long id, String nombre, List<Formato> formatos, List<Item> items) {
        this.id = id;
        this.nombre = nombre;
        this.formatos = formatos;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Formato> getFormatos() {
        return formatos;
    }

    public void setFormato(List<Formato> formatos) {
        this.formatos = formatos;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
