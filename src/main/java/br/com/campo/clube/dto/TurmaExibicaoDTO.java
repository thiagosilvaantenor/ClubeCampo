package br.com.campo.clube.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public record TurmaExibicaoDTO(
        Long id,
        String nomeTurma,
        @JsonFormat(pattern="dd-MM-yyyy HH:mm")
        LocalDateTime dtHorario,
        Integer vagasDisponiveis,
        Boolean vagasEsgotadas,
        List<ParticipanteAssociadoExibicaoDTO> associadosInscritos,
        List<ParticipanteDependenteExibicaoDTO> dependentesInscritos
) {
}
