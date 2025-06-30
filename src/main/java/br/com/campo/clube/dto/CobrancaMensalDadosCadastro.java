package br.com.campo.clube.dto;

import com.fasterxml.jackson.annotation.JsonFormat;


import java.time.LocalDate;

import java.time.YearMonth;

public record CobrancaMensalDadosCadastro(
        Long associadoId,
        @JsonFormat(pattern="dd-MM-yyyy")
        LocalDate dtVencimento,
        @JsonFormat(pattern="MM-yyyy")
        YearMonth mesAno
) {
}
