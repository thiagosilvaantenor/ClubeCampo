package br.com.campo.clube.dto;

import br.com.campo.clube.model.Associado;

public record DependenteExibicaoDTO(
        Long id,
        String nome,
        String rg,
        AssociadoSimplesDTO associado
) {
}
