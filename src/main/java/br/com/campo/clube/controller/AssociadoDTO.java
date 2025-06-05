package br.com.campo.clube.controller;

public record AssociadoDTO(
		String nomeCompleto,
		String rg,
		String cpf,
		String tipo,
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
