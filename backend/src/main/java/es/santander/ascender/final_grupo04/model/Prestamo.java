package es.santander.ascender.final_grupo04.model;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String persona;
    private Date fecha_prestamo;
    private Date fecha_devolucion;

    @OneToMany(mappedBy = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "prestamo_id")
    private Prestamo prestamo;

    public Prestamo() {
    }

    public Prestamo(Long id, String persona, Date fecha_prestamo, Date fecha_devolucion, Item item, Prestamo prestamo) {
        this.id = id;
        this.persona = persona;
        this.fecha_prestamo = fecha_prestamo;
        this.fecha_devolucion = fecha_devolucion;
        this.item = item;
        this.prestamo = prestamo;
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

    public Date getFecha_prestamo() {
        return fecha_prestamo;
    }

    public void setFecha_prestamo(Date fecha_prestamo) {
        this.fecha_prestamo = fecha_prestamo;
    }

    public Date getFecha_devolucion() {
        return fecha_devolucion;
    }

    public void setFecha_devolucion(Date fecha_devolucion) {
        this.fecha_devolucion = fecha_devolucion;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Prestamo getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(Prestamo prestamo) {
        this.prestamo = prestamo;
    }
    
 
}
