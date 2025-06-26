package br.com.campo.clube.dto;


import br.com.campo.clube.model.TipoAssociado;

public record AssociadoDTO(
		String nomeCompleto,
		String rg,
		String cpf,
		TipoAssociado tipo,
		Boolean carteirinhaBloqueada,
		String telefoneResidencial,
		String telefoneComercial,
		String telefoneCelular,
		String cep,
		String logradouro,
		String bairro,
		String cidade,
		String estado) {

}
