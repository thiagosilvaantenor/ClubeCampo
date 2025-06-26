package br.com.campo.clube.dto;


public record AssociadoDadosCadastro(
		String nomeCompleto,
		String rg,
		String cpf,
		Long tipoId,
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
