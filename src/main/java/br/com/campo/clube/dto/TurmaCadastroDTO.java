package br.com.campo.clube.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record TurmaCadastroDTO(
        @NotBlank(message = "O nome da turma é obrigatório")
        String nomeTurma,
        @NotNull(message = "O horário da turma é obrigatório")
        @Future(message = "A data e hora devem ser no futuro")
        @JsonFormat(pattern="dd-MM-yyyy HH:mm")
        LocalDateTime dtHorario,
        @NotNull(message = "O número de vagas é obrigatório")
        @Min(value = 1, message = "A turma deve ter no mínimo 1 vaga")
        Integer vagasDisponiveis

)
{
}
