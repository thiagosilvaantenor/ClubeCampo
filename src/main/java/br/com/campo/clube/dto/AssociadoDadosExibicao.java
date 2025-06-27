package br.com.campo.clube.dto;

import jakarta.validation.constraints.Size;

public record AssociadoDadosExibicao(
     Long id,
     String nomeCompleto,
     @Size(min = 7, max = 9, message = "rg deve ter no maximo 9 digitos")
     String rg,
     @Size(min = 11, max = 11, message = "Cpf deve ter exatos 11 caracteries")
     String cpf,
     TipoAssociadoDadosExibicao tipo,
     Boolean carteirinhaBloqueada,
     @Size(min = 10, max = 10, message = "telefone deve ter exatos 10 números")
     String telefoneResidencial,
     @Size(min = 10, max = 10, message = "telefone deve ter exatos 10 números")
     String telefoneComercial,
     @Size(min = 11, max = 11, message = "celular deve ter exatos 11 números")
     String telefoneCelular,
     @Size(min = 8, max = 8, message = "cep deve ter exatos 8 números")
     String cep,
     String logradouro,
     String bairro,
     String cidade,
     @Size(min = 2, max = 2, message = "a sigla do estado deve ter exatos 2 letras")
     String estado) {
}
