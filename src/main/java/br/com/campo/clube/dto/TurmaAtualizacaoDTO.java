package br.com.campo.clube.dto;

import jakarta.validation.constraints.Future;

import java.time.LocalDateTime;

public record TurmaAtualizacaoDTO(
        String nomeTurma,
        @Future(message = "A data e hora devem ser no futuro")
        LocalDateTime dtHorario
) {
}
