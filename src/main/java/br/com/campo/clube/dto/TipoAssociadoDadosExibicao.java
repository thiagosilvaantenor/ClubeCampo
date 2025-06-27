package br.com.campo.clube.dto;

import java.math.BigDecimal;
// DTO para exibir um Tipo de Associado na resposta
public record TipoAssociadoDadosExibicao(Long id, String nome, BigDecimal valor) {
}
