package br.com.campo.clube.dto;


import java.math.BigDecimal;

public record AreaDadosExibicao(
        Long id,
        String nomeArea,
        String tipoArea,
        BigDecimal valorArea,
        Boolean reservavel,
        Integer quantidade
) {}
