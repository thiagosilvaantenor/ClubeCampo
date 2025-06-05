package br.com.campo.clube.model;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.campo.clube.controller.AssociadoDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Associado {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String nomeCompleto;
	@Column(length = 9)
	private String rg;
	@Column(length = 11)
	private String cpf;
	private String tipo;
	private Boolean carteirinhaBloqueada;
	
	//BRAINSTROM: Talvez faria mais sentido separar em uma entidade Telefones?
	@Column(length = 10)
	private String telefoneResidencial;
	@Column(length = 10)
	private String telefoneComercial;
	@Column(length = 11)
	private String telefoneCelular;
	
	//BRAINSTROM: Talvez faria mais sentido separar em uma entidade Endereco?
	@Column(length = 8)
	private String cep;
	private String logradouro;
	private String bairro;
	private String cidade;
	@Column(length = 2)
	private String estado;
	
	//@DateTimeFormat(pattern = "DD/MM/YYYY HH:MM:SS")
	private LocalDateTime dt_cadastro;
	


	public Associado(AssociadoDTO dados) {
		this.nomeCompleto = dados.nomeCompleto();
		this.rg = dados.rg();
		this.cpf = dados.cpf();
		this.tipo = dados.tipo();
		this.carteirinhaBloqueada = dados.carteirinhaBloqueada();
		this.telefoneResidencial = dados.telefoneResidencial();
		this.telefoneComercial = dados.telefoneComercial();
		this.telefoneCelular = dados.telefoneCelular();
		this.cep = dados.cep();
		this.logradouro = dados.logradouro();
		this.bairro = dados.bairro();
		this.cidade = dados.cidade();
		this.estado = dados.estado();
		this.dt_cadastro = LocalDateTime.now();
	}

	
	
}
