package br.com.campo.clube.service;

import br.com.campo.clube.dto.*;
import br.com.campo.clube.model.Associado;
import br.com.campo.clube.model.Dependente;
import br.com.campo.clube.repository.AssociadoRepository;
import br.com.campo.clube.repository.DependenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DependenteService {

    @Autowired
    private DependenteRepository dependenteRepository;

    @Autowired
    private AssociadoRepository associadoRepository;


    @Transactional
    public Dependente salvar(DependenteCadastroDTO dados) {
        Associado associado = associadoRepository.findById(dados.associadoId())
                .orElseThrow(() -> new RuntimeException("Associado titular não encontrado!"));

        Dependente dependente = new Dependente();
        dependente.setAssociado(associado);
        dependente.setNome(dados.nome());
        dependente.setRg(dados.rg());

        return dependenteRepository.save(dependente);
    }

    @Transactional
    public Dependente atualizar(Long id, DependenteCadastroDTO dados) {
        Dependente dependente = dependenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dependente não encontrado!"));

        dependente.setNome(dados.nome());
        dependente.setRg(dados.rg());

        return dependenteRepository.save(dependente);
    }

    @Transactional(readOnly = true)
    public Optional<Dependente> buscarPeloId(Long id) {
        return dependenteRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Dependente> buscarPorAssociado(Long associadoId) {
        return dependenteRepository.findByAssociadoId(associadoId);
    }

    @Transactional
    public void excluir(Long id) {
        // TODO: Adicionar regra de negócio, se necessário (ex: verificar se dependente está em alguma turma)
        if (!dependenteRepository.existsById(id)) {
            throw new RuntimeException("Dependente com ID " + id + " não encontrado para exclusão!");
        }
        dependenteRepository.deleteById(id);
    }

}
