package br.com.campo.clube.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;

public record CobrancaMensalDadosAtualizacao(
        @NotBlank(message = "Id do associado não pode estar em branco")
        Long associadoId,
        @JsonFormat(pattern="dd-MM-yyyy")
        LocalDate dtVencimento,
        BigDecimal valorPadrao,
        BigDecimal valorFinal,
        @JsonFormat(pattern="MM-yyyy")
        YearMonth mesAno,
        Boolean pago
) {
}
