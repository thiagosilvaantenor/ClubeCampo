package br.com.campo.clube.controller;

import br.com.campo.clube.dto.*;
import br.com.campo.clube.model.ParticipanteTurmaAssociado;
import br.com.campo.clube.model.ParticipanteTurmaDependente;
import br.com.campo.clube.model.Turma;
import br.com.campo.clube.service.TurmaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/turma")
public class TurmaController {

    @Autowired
    private TurmaService service;

    // --- Endpoints CRUD de Turma ---
    @PostMapping
    public ResponseEntity<TurmaExibicaoDTO> criarTurma(@RequestBody @Valid TurmaCadastroDTO dados) {
        Turma turmaSalva = service.criarTurma(dados);
        //Converte a Turma em DTO
        TurmaExibicaoDTO dto = toTurmaExibicaoDTO(turmaSalva);
        //Retorna 201 e o DTO no body
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping
    public ResponseEntity<List<TurmaExibicaoDTO>> listarTurmas() {
        List<Turma> turmas = service.listarTodas();
        List<TurmaExibicaoDTO> dtos = turmas.stream()
                .map(this::toTurmaExibicaoDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurmaExibicaoDTO> exibirTurma(@PathVariable Long id) {
        return service.buscarPeloId(id)
                .map(turma -> ResponseEntity.ok(toTurmaExibicaoDTO(turma)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TurmaExibicaoDTO> atualizarTurma(@PathVariable Long id, @RequestBody @Valid TurmaAtualizacaoDTO dados) {
        Turma turmaAtualizada = service.atualizarTurma(id, dados);
        return ResponseEntity.ok(toTurmaExibicaoDTO(turmaAtualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirTurma(@PathVariable Long id) {
        service.excluirTurma(id);
        return ResponseEntity.noContent().build();
    }

    // --- Endpoints de Inscrição ---

    //INSERTS
    @PostMapping("/inscrever-associado")
    public ResponseEntity<ParticipanteAssociadoExibicaoDTO> inscreverAssociado(@RequestBody @Valid InscricaoAssociadoDTO dados) {
        ParticipanteTurmaAssociado inscricao = service.inscreverAssociado(dados);
        return ResponseEntity.status(HttpStatus.CREATED).body(toParticipanteAssociadoExibicaoDTO(inscricao));
    }

    @PostMapping("/inscrever-dependente")
    public ResponseEntity<ParticipanteDependenteExibicaoDTO> inscreverDependente(@RequestBody @Valid InscricaoDependenteDTO dados) {
        ParticipanteTurmaDependente inscricao = service.inscreverDependente(dados);
        return ResponseEntity.status(HttpStatus.CREATED).body(toParticipanteDependenteExibicaoDTO(inscricao));
    }

    // --- Endpoints para Cancelar Inscrição ---
    //DELETES
    @DeleteMapping("/inscricao-associado/{idInscricao}")
    public ResponseEntity<Void> cancelarInscricaoAssociado(@PathVariable Long idInscricao) {
        service.cancelarInscricaoAssociado(idInscricao);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/inscricao-dependente/{idInscricao}")
    public ResponseEntity<Void> cancelarInscricaoDependente(@PathVariable Long idInscricao) {
        service.cancelarInscricaoDependente(idInscricao);
        return ResponseEntity.noContent().build();
    }

    // --- Métodos de Conversão de Entidade para DTO ---
    private TurmaExibicaoDTO toTurmaExibicaoDTO(Turma turma) {
        return new TurmaExibicaoDTO(
                turma.getId(),
                turma.getNomeTurma(),
                turma.getDtHorario(),
                turma.getVagasDisponiveis(),
                turma.getVagasEsgotadas(),
                turma.getAssociados().stream().map(this::toParticipanteAssociadoExibicaoDTO).collect(Collectors.toList()),
                turma.getDependentes().stream().map(this::toParticipanteDependenteExibicaoDTO).collect(Collectors.toList())
        );
    }

    private ParticipanteAssociadoExibicaoDTO toParticipanteAssociadoExibicaoDTO(ParticipanteTurmaAssociado p) {
        return new ParticipanteAssociadoExibicaoDTO(p.getId(), new AssociadoSimplesDTO(p.getAssociado().getId(),
                p.getAssociado().getNomeCompleto()));
    }

    private ParticipanteDependenteExibicaoDTO toParticipanteDependenteExibicaoDTO(ParticipanteTurmaDependente p) {
        return new ParticipanteDependenteExibicaoDTO(p.getId(), new DependenteSimplesDTO(p.getDependente().getId(),
                p.getDependente().getNome()));
    }

}