package br.com.campo.clube.dto;

import com.fasterxml.jackson.annotation.JsonFormat;


import java.time.LocalDate;

public record PagamentoRealizadoDadosAtualizacao(
       @JsonFormat(pattern = "dd-MM-yyyy")
       LocalDate dtPagamento,
       String formaPagamento,
       Long cobrancaId
) {
}
