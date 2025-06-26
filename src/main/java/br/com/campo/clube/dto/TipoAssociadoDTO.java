package br.com.campo.clube.dto;

import java.math.BigDecimal;

public record TipoAssociadoDTO(
        String nome,
        BigDecimal valor
) {
}
