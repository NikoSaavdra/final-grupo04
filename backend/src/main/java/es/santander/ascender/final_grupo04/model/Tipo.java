package es.santander.ascender.final_grupo04.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Tipo {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String nombre;



@OneToMany(mappedBy = "tipo")
List <Tipo> tipos;

public Tipo() {
}

public Tipo(Long id, String nombre, List<Tipo> tipos) {
    this.id = id;
    this.nombre = nombre;
    this.tipos = tipos;
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

public List<Tipo> getTipos() {
    return tipos;
}

public void setTipos(List<Tipo> tipos) {
    this.tipos = tipos;
}



}
