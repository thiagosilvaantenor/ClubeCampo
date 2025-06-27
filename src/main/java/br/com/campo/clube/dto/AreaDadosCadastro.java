package br.com.campo.clube.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record AreaDadosCadastro(
        @NotBlank(message = "Nome da area não pode estar em branco")
        String nomeArea,
        @NotBlank(message = "Tipo da area não pode estar em branco")
        String tipoArea,
        @NotBlank(message = "valor da área não pode estar em branco")
        BigDecimal valorArea,
        @NotNull(message = "reservavel não pode ser nulo")
        Boolean reservavel,
        @NotNull(message = "quantidade não pode ser nulo")
        @Size(min = 1, max = 3)
        Integer quantidade
)
{}
