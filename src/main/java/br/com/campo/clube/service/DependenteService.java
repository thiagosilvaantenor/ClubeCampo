package br.com.campo.clube.service;

import br.com.campo.clube.dto.*;
import br.com.campo.clube.exceptions.AssociadoException;
import br.com.campo.clube.exceptions.ParametroInvalidoException;
import br.com.campo.clube.model.Associado;
import br.com.campo.clube.model.Dependente;
import br.com.campo.clube.repository.AssociadoRepository;
import br.com.campo.clube.repository.DependenteRepository;
import br.com.campo.clube.repository.ParticipanteTurmaDependenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class DependenteService {

    @Autowired
    private DependenteRepository dependenteRepository;

    @Autowired
    private ParticipanteTurmaDependenteRepository participanteTurmaDependenteRepository;

    @Autowired
    private AssociadoRepository associadoRepository;


    @Transactional
    public Dependente salvar(DependenteCadastroDTO dados) {
        Associado associado = associadoRepository.findById(dados.associadoId())
                .orElseThrow(() -> new AssociadoException("Associado titular não encontrado!"));
        Dependente dependente = new Dependente();
        dependente.setAssociado(associado);
        dependente.setNome(dados.nome());
        dependente.setRg(dados.rg());

        return dependenteRepository.save(dependente);
    }

    @Transactional
    public Dependente atualizar(Long id, DependenteCadastroDTO dados) {
        Dependente dependente = dependenteRepository.findById(id)
                .orElseThrow(() -> new AssociadoException("Dependente não encontrado!"));
        dependente.setNome(dados.nome());
        dependente.setRg(dados.rg());

        return dependenteRepository.save(dependente);
    }

    @Transactional(readOnly = true)
    public Dependente buscarPeloId(Long id) {
        return dependenteRepository.findById(id)
                .orElseThrow(() -> new ParametroInvalidoException("Nenhum Dependente encontrado com este id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Dependente> buscarPorAssociado(Long associadoId) {
        return dependenteRepository.findByAssociadoId(associadoId);
    }

    @Transactional(readOnly = true)
    public List<Dependente> buscarTodos() {
        return dependenteRepository.findAll();
    }

    @Transactional
    public void excluir(Long id) {
        if (!dependenteRepository.existsById(id)) {
            throw new AssociadoException("Dependente com ID " + id + " não encontrado para exclusão!");
        }
        //Verifica se existe inscrições em turmas, se sim pede para excluir elas primeiro
        if (!participanteTurmaDependenteRepository.findByDependenteId(id).isEmpty()){
            throw new ParametroInvalidoException
                    ("Dependente tem inscrições em turmas, exclua elas primeira para poder excluir o dependente");
        }
        dependenteRepository.deleteById(id);
    }

}
