package br.com.campo.clube.dto;


import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AssociadoDadosCadastro(
		@NotBlank(message = "nome não pode estar em branco")
		String nomeCompleto,
		@NotBlank(message = "rg não pode estar em branco")
		@Size(min = 7, max = 9, message = "rg deve ter no maximo 9 digitos")
		String rg,
		@NotBlank(message = "cpf não pode estar em branco")
		@Size(min = 11, max = 11, message = "Cpf deve ter exatos 11 caracteries")
		String cpf,
		Long tipoId,
		@Nullable
		@Size(min = 10, max = 10, message = "telefone deve ter exatos 10 números")
		String telefoneResidencial,
		@Nullable
		@Size(min = 10, max = 10, message = "telefone deve ter exatos 10 números")
		String telefoneComercial,
		@NotBlank(message = "celular não pode estar em branco")
		@Size(min = 11, max = 11, message = "celular deve ter exatos 11 números")
		String telefoneCelular,
		@NotBlank(message = "Cep não pode estar em branco")
		@Size(min = 8, max = 8, message = "cep deve ter exatos 8 números")
		String cep,
		@NotBlank(message = "Logradouro não pode estar em branco")
		String logradouro,
		@NotBlank(message = "Bairro não pode estar em branco")
		String bairro,
		@NotBlank(message = "Cidade não pode estar em branco")
		String cidade,
		@Size(min = 2, max = 2, message = "a sigla do estado deve ter exatos 2 letras")
		@NotBlank(message = "A sigla do estado não pode estar em branco")
		String estado) {

}
