package es.santander.ascender.final_grupo04.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.santander.ascender.final_grupo04.model.Tipo;
import es.santander.ascender.final_grupo04.repository.ItemRepository;
import es.santander.ascender.final_grupo04.repository.TipoRepository;


@Transactional
@Service
public class TipoService {

    @Autowired
    private TipoRepository tipoRepository;

    @Autowired
    private ItemRepository itemRepository;

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
    public void eliminarTipo(Long id) {
        long count = itemRepository.countByTipoId(id);
        if (count > 0) {
            throw new IllegalArgumentException("No puedes eliminar el tipo porque hay items asociados");
        }
        tipoRepository.deleteById(id);
    }
}


