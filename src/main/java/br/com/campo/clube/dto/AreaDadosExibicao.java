package br.com.campo.clube.dto;


import br.com.campo.clube.model.Area;

import java.math.BigDecimal;

public record AreaDadosExibicao(
        Long id,
        String nomeArea,
        Boolean reservavel,
        Integer quantidade
) {

}
