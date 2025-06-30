package br.com.campo.clube.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;

public record CobrancaMensalDadosExibicao(
        Long id,
        AssociadoSimplesDTO associado,
        @JsonFormat(pattern="dd-MM-yyyy")
        LocalDate dtVencimento,
        BigDecimal valorPadrao,
        BigDecimal valorFinal,
        YearMonth mesAno,
        Boolean pago
) {
}
