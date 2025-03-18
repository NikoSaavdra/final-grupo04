package es.santander.ascender.final_grupo04.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Tipo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nombre;
    private String formato;

    @OneToMany
    private Item item;

    public Tipo() {
    }

    
    public Tipo(Long id, String nombre, String formato, Item item) {
        this.id = id;
        this.nombre = nombre;
        this.formato = formato;
        this.item = item;
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


    public String getFormato() {
        return formato;
    }


    public void setFormato(String formato) {
        this.formato = formato;
    }


    public Item getItem() {
        return item;
    }


    public void setItem(Item item) {
        this.item = item;
    }

    



}
