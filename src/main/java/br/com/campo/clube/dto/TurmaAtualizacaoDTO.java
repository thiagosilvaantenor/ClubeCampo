package br.com.campo.clube.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;

import java.time.LocalDateTime;

public record TurmaAtualizacaoDTO(
        String nomeTurma,
        @Future(message = "A data e hora devem ser no futuro")
        @JsonFormat(pattern="dd-MM-yyyy HH:mm")
        LocalDateTime dtHorario
) {
}
