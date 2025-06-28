package br.com.campo.clube.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


import java.math.BigDecimal;

public record AreaDadosCadastro(
        @NotBlank(message = "Nome da area não pode estar em branco")
        String nomeArea,
        @NotBlank(message = "Tipo da area não pode estar em branco")
        String tipoArea,
        @NotNull(message = "reservavel não pode ser nulo")
        Boolean reservavel,
        Integer quantidade
)
{}
