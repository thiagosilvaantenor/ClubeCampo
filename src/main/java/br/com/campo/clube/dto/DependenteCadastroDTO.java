package br.com.campo.clube.dto;

public record DependenteCadastroDTO(
        Long associadoId,
        String nome,
        String rg
) {
}
