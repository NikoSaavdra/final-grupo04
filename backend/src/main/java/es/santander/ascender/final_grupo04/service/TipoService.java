package es.santander.ascender.final_grupo04.service;

import java.util.List;

import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.santander.ascender.final_grupo04.model.Tipo;
import es.santander.ascender.final_grupo04.repository.TipoRepository;
import jakarta.transaction.Transactional;

@Transactional
@Service
public class TipoService {

    @Autowired
    private TipoRepository tipoRepository;

    @Transactional(readOnly = true)
    public List<Tipo> listarTipos() {
        return tipoRepository.findAll();
    }

    @Transactional
    public Tipo crearTipo(Tipo tipo) {
        return tipoRepository.save(tipo);
    }

    @Transactional
    public Tipo actualizarTipo(Long tipoId, String nuevoNombre) {
        Optional<Tipo> tipoOptional = tipoRepository.findById(tipoId);
        if (tipoOptional.isPresent()) {
            Tipo tipo = tipoOptional.get();
            tipo.setNombre(nuevoNombre);
            return tipoRepository.save(tipo);
        }
        return null;

    }

    @Transactional
    public void eliminarTipo(Long tipoId) {
        Tipo tipo = tipoRepository.findById(tipoId)
                .orElseThrow(() -> new RuntimeException("Tipo no encontrado"));

        if (!tipo.getItem().isEmpty()) {
            throw new RuntimeException("No se puede eliminar un tipo que tiene items asociados");
        }

        tipoRepository.delete(tipo);
    }
}


