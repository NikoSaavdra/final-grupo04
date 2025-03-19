package es.santander.ascender.final_grupo04.DTO;

public class ItemDTO {

    private String titulo;
    private String ubicacion;
    private Long tipoId; // ID del tipo asociado
    private String formato; // Formato seleccionado (por ejemplo, "CD" o "Vinilo")

    // Getters y Setters
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

    public Long getTipoId() {
        return tipoId;
    }

    public void setTipoId(Long tipoId) {
        this.tipoId = tipoId;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }
}
