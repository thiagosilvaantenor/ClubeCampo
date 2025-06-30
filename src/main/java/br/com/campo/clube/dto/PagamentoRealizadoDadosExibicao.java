package br.com.campo.clube.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;


public record PagamentoRealizadoDadosExibicao(
       Long id,
       @JsonFormat(pattern = "dd/MM/YYYY")
       LocalDate dtPagamento,
       String formaPagamento,
       CobrancaMensalDadosExibicao cobranca
) {
}
