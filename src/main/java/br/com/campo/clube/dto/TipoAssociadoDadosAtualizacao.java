package br.com.campo.clube.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TipoAssociadoDadosAtualizacao(
        String nome,
        BigDecimal valor
) {
}
