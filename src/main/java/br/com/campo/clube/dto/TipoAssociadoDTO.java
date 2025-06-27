package br.com.campo.clube.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TipoAssociadoDTO(
        @NotBlank(message = "nome não pode estar em branco")
        String nome,
        @NotNull(message = "O campo 'valor' não pode ser nulo.")
        BigDecimal valor
) {
}
