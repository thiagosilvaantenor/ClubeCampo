package br.com.campo.clube.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record AreaDadosCadastro(
        @NotBlank(message = "Nome da area não pode estar em branco")
        String nomeArea,
        @NotNull(message = "reservavel não pode ser nulo")
        Boolean reservavel,
        Integer quantidade
)
{}
