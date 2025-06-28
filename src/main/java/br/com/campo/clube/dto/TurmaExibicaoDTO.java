package br.com.campo.clube.dto;

import java.time.LocalDateTime;
import java.util.List;

public record TurmaExibicaoDTO(
        Long id,
        String nomeTurma,
        LocalDateTime dtHorario,
        Integer vagasDisponiveis,
        Boolean vagasEsgotadas,
        List<ParticipanteAssociadoExibicaoDTO> associadosInscritos,
        List<ParticipanteDependenteExibicaoDTO> dependentesInscritos
) {
}
