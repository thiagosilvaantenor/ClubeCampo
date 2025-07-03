package br.com.campo.clube.service;

import br.com.campo.clube.dto.*;
import br.com.campo.clube.exceptions.AssociadoException;
import br.com.campo.clube.exceptions.ParametroInvalidoException;
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
    private AssociadoService associadoService;

    @Autowired
    private DependenteService dependenteRepository;
    @Autowired
    private ParticipanteTurmaAssociadoRepository participanteTurmaAssociadoRepository;
    @Autowired
    private ParticipanteTurmaDependenteRepository participanteTurmaDependenteRepository;

    //Insert Turma
    @Transactional
    public Turma criarTurma(TurmaCadastroDTO dados) {
        Turma turma = new Turma();
        try{
            turma.setNomeTurma(dados.nomeTurma());
            turma.setDtHorario(dados.dtHorario());
            turma.setVagasDisponiveis(dados.vagasDisponiveis());
            turma.setVagasEsgotadas(false);
        }catch (ParametroInvalidoException e){
            throw new ParametroInvalidoException("Não foi possivel cadastrar a turma" + e.getLocalizedMessage());
        }
        return turmaRepository.save(turma);
    }

    //Selects
    @Transactional(readOnly = true)
    public List<Turma> listarTodas() {
        return turmaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Turma buscarPeloId(Long id) {
        return turmaRepository.findById(id)
                .orElseThrow(() -> new ParametroInvalidoException("Nenhuma Turma foi encontrada com este id: " + id));
    }
    //Update
    @Transactional
    public Turma atualizarTurma(Long id, TurmaAtualizacaoDTO dados) {
        Turma turma = turmaRepository.findById(id)
                .orElseThrow(() -> new ParametroInvalidoException("Turma não encontrada!"));

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
                .orElseThrow(() -> new ParametroInvalidoException("Turma não encontrada!"));
        if (!turma.getAssociados().isEmpty() || !turma.getDependentes().isEmpty()) {
            throw new ParametroInvalidoException
                    ("Não é possível excluir turma com participantes inscritos. Cancele as inscrições primeiro.");
        }
        turmaRepository.delete(turma);
    }
// --- Métodos dos participantes
//Insert ParticipanteAssociado
    @Transactional
    public ParticipanteTurmaAssociado inscreverAssociado(InscricaoAssociadoDTO dados) {
        //Verifica se a turma existe e tem vagas
        Turma turma = getTurmaDisponivel(dados.turmaId());
        //verifica se o associado existe e busca ele
        Associado associado = associadoService.buscarPeloId(dados.associadoId());
        //Verifica se o associado tem inadimplências e pode participar da turma
        verificaAssociado(associado, turma.getNomeTurma());
        //Cria a inscrição deste associado na turma, ele se torna participanteAssociado dela
        ParticipanteTurmaAssociado inscricao = new ParticipanteTurmaAssociado(null, associado, turma);
        //Controla a quantidade de vagas
        decrementarVaga(turma);
        return participanteTurmaAssociadoRepository.save(inscricao);
    }

    private void verificaAssociado(Associado associado, String nomeTurma) {
        int qntInadimplencia = associadoService.verificaQntInadimplencia(associado);
        //Se for maior ou igual a 4 não pode participar de turmas
        if (qntInadimplencia >= 4){
            throw new AssociadoException
            ("Associado está com inadimplência superior a 3 meses, por isso ele e seus dependentes não podem se inscrever em uma Turma");
        }
        //Se for passeio de haras não pode ter inadimplência maior ou igual a 2 meses
        if (nomeTurma.equalsIgnoreCase("haras") && qntInadimplencia >= 2) {
            throw new AssociadoException
            ("Associado tem inadimplência de pelo menos 2 meses por isso ele e seus dependentes não podem participar de uma turma do haras");
        }
    }

    //Insert ParticipanteDependente

    @Transactional
    public ParticipanteTurmaDependente inscreverDependente(InscricaoDependenteDTO dados) {
        Turma turma = getTurmaDisponivel(dados.turmaId());
        Dependente dependente = dependenteRepository.buscarPeloId(dados.dependenteId());
        //Verifica se o associado, responsavel pelo dependente tem inadimplências
        verificaAssociado(dependente.getAssociado(), turma.getNomeTurma());
        ParticipanteTurmaDependente inscricao = new ParticipanteTurmaDependente(null, dependente, turma);
        decrementarVaga(turma);
        return participanteTurmaDependenteRepository.save(inscricao);
    }

    //Delete de Participantes
    @Transactional
    public void cancelarInscricaoAssociado(Long idInscricao) {
        //Procura a inscrição do id informado
        ParticipanteTurmaAssociado inscricao = participanteTurmaAssociadoRepository.findById(idInscricao)
                .orElseThrow(() -> new ParametroInvalidoException("Inscrição de associado não encontrada!"));
        //Controle de vagas da turma
        incrementarVaga(inscricao.getTurma());
        participanteTurmaAssociadoRepository.delete(inscricao);
    }

    @Transactional
    public void cancelarInscricaoDependente(Long idInscricao) {
        //Procura a inscrição do id informado
        ParticipanteTurmaDependente inscricao = participanteTurmaDependenteRepository.findById(idInscricao)
                .orElseThrow(() -> new ParametroInvalidoException("Inscrição de dependente não encontrada!"));
        //Controle de vagas da turma
        incrementarVaga(inscricao.getTurma());
        participanteTurmaDependenteRepository.delete(inscricao);
    }

    //Verifica se a turma existe e se tem vagas disponiveis
    private Turma getTurmaDisponivel(Long turmaId) {
        Turma turma = turmaRepository.findById(turmaId)
                .orElseThrow(() -> new ParametroInvalidoException("Turma não encontrada!"));
        if (Boolean.TRUE.equals(turma.getVagasEsgotadas())) {
            throw new ParametroInvalidoException("Vagas esgotadas para esta turma!");
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