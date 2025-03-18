package es.santander.ascender.final_grupo04.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.santander.ascender.final_grupo04.model.Tipo;
import es.santander.ascender.final_grupo04.repository.TipoRepository;

@Transactional
@Service
public class TipoService {

    @Autowired
    private TipoRepository tipoRepository;

    @Transactional
    public Tipo crearTipo(Tipo tipo) {
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

    @Transactional
    public void eliminarTipo(Long id) {
        tipoRepository.deleteById(id);
    }
}
