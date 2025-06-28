package br.com.campo.clube.service;

import br.com.campo.clube.dto.*;
import br.com.campo.clube.model.*;
import br.com.campo.clube.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TurmaService {

    @Autowired
    private TurmaRepository turmaRepository;
    @Autowired
    private AssociadoRepository associadoRepository;
    @Autowired
    private ParticipanteTurmaAssociadoRepository turmaAssociadoRepository;

    @Autowired
    private DependenteRepository dependenteRepository;
    @Autowired
    private ParticipanteTurmaAssociadoRepository participanteTurmaAssociadoRepository;
    @Autowired
    private ParticipanteTurmaDependenteRepository participanteTurmaDependenteRepository;

    //Insert Turma
    @Transactional
    public Turma criarTurma(TurmaCadastroDTO dados) {
        Turma turma = new Turma();
        turma.setNomeTurma(dados.nomeTurma());
        turma.setDtHorario(dados.dtHorario());
        turma.setVagasDisponiveis(dados.vagasDisponiveis());
        turma.setVagasEsgotadas(false);
        return turmaRepository.save(turma);
    }

    //Selects
    @Transactional(readOnly = true)
    public List<Turma> listarTodas() {
        return turmaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Turma> buscarPeloId(Long id) {
        return turmaRepository.findById(id);
    }
    //Update
    @Transactional
    public Turma atualizarTurma(Long id, TurmaAtualizacaoDTO dados) {
        Turma turma = turmaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada!"));

        if (dados.nomeTurma() != null && !dados.nomeTurma().isBlank()) {
            turma.setNomeTurma(dados.nomeTurma());
        }
        if (dados.dtHorario() != null) {
            turma.setDtHorario(dados.dtHorario());
        }

        return turmaRepository.save(turma);
    }
    //Delete
    @Transactional
    public void excluirTurma(Long id) {
        Turma turma = turmaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada!"));
        if (!turma.getAssociados().isEmpty() || !turma.getDependentes().isEmpty()) {
            throw new RuntimeException("Não é possível excluir turma com participantes inscritos. Cancele as inscrições primeiro.");
        }
        turmaRepository.delete(turma);
    }
// --- Métodos dos participantes
//Insert ParticipanteAssociado
    @Transactional
    public ParticipanteTurmaAssociado inscreverAssociado(InscricaoAssociadoDTO dados) {
        Turma turma = getTurmaDisponivel(dados.turmaId());
        Associado associado = associadoRepository.findById(dados.associadoId())
                .orElseThrow(() -> new RuntimeException("Associado não encontrado!"));

        ParticipanteTurmaAssociado inscricao = new ParticipanteTurmaAssociado(null, associado, turma);
        decrementarVaga(turma);
        return participanteTurmaAssociadoRepository.save(inscricao);
    }

    //Insert ParticipanteDependente

    @Transactional
    public ParticipanteTurmaDependente inscreverDependente(InscricaoDependenteDTO dados) {
        Turma turma = getTurmaDisponivel(dados.turmaId());
        Dependente dependente = dependenteRepository.findById(dados.dependenteId())
                .orElseThrow(() -> new RuntimeException("Dependente não encontrado!"));

        ParticipanteTurmaDependente inscricao = new ParticipanteTurmaDependente(null, dependente, turma);
        decrementarVaga(turma);
        return participanteTurmaDependenteRepository.save(inscricao);
    }

    //Delete de Participantes
    @Transactional
    public void cancelarInscricaoAssociado(Long idInscricao) {
        ParticipanteTurmaAssociado inscricao = participanteTurmaAssociadoRepository.findById(idInscricao)
                .orElseThrow(() -> new RuntimeException("Inscrição de associado não encontrada!"));

        incrementarVaga(inscricao.getTurma());
        participanteTurmaAssociadoRepository.delete(inscricao);
    }

    @Transactional
    public void cancelarInscricaoDependente(Long idInscricao) {
        ParticipanteTurmaDependente inscricao = participanteTurmaDependenteRepository.findById(idInscricao)
                .orElseThrow(() -> new RuntimeException("Inscrição de dependente não encontrada!"));

        incrementarVaga(inscricao.getTurma());
        participanteTurmaDependenteRepository.delete(inscricao);
    }

    //Verifica se a turma existe e se tem vagas disponiveis
    private Turma getTurmaDisponivel(Long turmaId) {
        Turma turma = turmaRepository.findById(turmaId)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada!"));
        if (turma.getVagasEsgotadas()) {
            throw new RuntimeException("Vagas esgotadas para esta turma!");
        }
        return turma;
    }
    //Diminui a quantidade de vagas da turma
    private void decrementarVaga(Turma turma) {
        turma.setVagasDisponiveis(turma.getVagasDisponiveis() - 1);
        if (turma.getVagasDisponiveis() <= 0) {
            turma.setVagasEsgotadas(true);
        }
        turmaRepository.save(turma);
    }
    //Aumenta a quantidade de vagas da turma
    private void incrementarVaga(Turma turma) {
        turma.setVagasDisponiveis(turma.getVagasDisponiveis() + 1);
        if (turma.getVagasDisponiveis() > 0) {
            turma.setVagasEsgotadas(false);
        }
        turmaRepository.save(turma);
    }

}