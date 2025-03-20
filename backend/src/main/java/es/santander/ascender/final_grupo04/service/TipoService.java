package es.santander.ascender.final_grupo04.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.santander.ascender.final_grupo04.DTO.TipoDTO;
import es.santander.ascender.final_grupo04.model.Formato;
import es.santander.ascender.final_grupo04.model.Tipo;
import es.santander.ascender.final_grupo04.repository.FormatoRepository;
import es.santander.ascender.final_grupo04.repository.TipoRepository;

@Transactional
@Service
public class TipoService {

    @Autowired
    private TipoRepository tipoRepository;

    @Autowired
    private FormatoRepository formatoRepository;

    @Transactional
    public Tipo crearTipo(TipoDTO tipoDTO) {
    // Recuperamos los formatos por sus IDs
    List<Formato> formatosExistentes = tipoDTO.getFormatoIds().stream()
        .map(id -> formatoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Formato con ID " + id + " no encontrado")))
        .collect(Collectors.toList());

    // Creamos el nuevo tipo
    Tipo tipo = new Tipo();
    tipo.setNombre(tipoDTO.getNombre());  // Asignamos el nombre desde el DTO
    tipo.setFormato(formatosExistentes);  // Asociamos los formatos al tipo

    // Guardamos el nuevo tipo con los formatos asociados
    return tipoRepository.save(tipo);
}


    @Transactional(readOnly = true)
    public List<Tipo> listarTipos() {
        return tipoRepository.findAll();
    }

    @Transactional
    public Tipo actualizarTipo(Long id, String nombre) {
        Optional<Tipo> tipoOptional = tipoRepository.findById(id);
        if (tipoOptional.isPresent()) {
            Tipo tipo = tipoOptional.get();
            tipo.setNombre(nombre);
            return tipoRepository.save(tipo);
        }
        return null;
    }

    public List<String> obtenerFormatosPorTipo(Long tipoId) {
        Tipo tipo = tipoRepository.findById(tipoId)
                .orElseThrow(() -> new RuntimeException("Tipo no encontrado"));

        // Obtener los nombres de los formatos asociados al tipo
        return tipo.getFormato().stream()
                .map(Formato::getNombre)
                .collect(Collectors.toList());
    }

    @Transactional
    public void eliminarTipo(Long id) {
        tipoRepository.deleteById(id);
    }
}
