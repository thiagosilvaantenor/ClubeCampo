package br.com.campo.clube.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record PagamentoRealizadoDadosCadastro(
       @JsonFormat(pattern = "dd-MM-yyyy")
       LocalDate dtPagamento,
       @NotBlank(message = "Forma de pagamento não pode estar em branco")
       String formaPagamento,
       Long cobrancaId
) {
}
