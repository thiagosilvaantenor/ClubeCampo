package br.com.campo.clube.model;

import java.time.LocalDateTime;

import br.com.campo.clube.dto.AssociadoDadosCadastro;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "associado")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Associado {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_associado")
	private Long id;
	@Column(name = "telefone_residencial")
	private String nomeCompleto;
	@Column(length = 9)
	private String rg;
	@Column(length = 11)
	private String cpf;

	@ManyToOne
	@JoinColumn(name = "id_tipo_associado")
	private TipoAssociado tipo;
	@Column(name = "carteirinha_bloqueada")
	private Boolean carteirinhaBloqueada;
	
	@Column(name = "telefone_residencial", length = 10)
	private String telefoneResidencial;
	@Column(name = "telefone_comercial",length = 10)
	private String telefoneComercial;
	@Column(name = "telefone_celular",length = 11)
	private String telefoneCelular;
	
	@Column(length = 8)
	private String cep;
	private String logradouro;
	private String bairro;
	private String cidade;
	@Column(length = 2)
	private String estado;

	@Column(name = "dt_cadastro")
	private LocalDateTime dtCadastro;



	public Associado(AssociadoDadosCadastro dados) {
		this.nomeCompleto = dados.nomeCompleto();
		this.rg = dados.rg();
		this.cpf = dados.cpf();
		//Tipo vai ser adicionado no controller
		this.carteirinhaBloqueada = dados.carteirinhaBloqueada();
		this.telefoneResidencial = dados.telefoneResidencial();
		this.telefoneComercial = dados.telefoneComercial();
		this.telefoneCelular = dados.telefoneCelular();
		this.cep = dados.cep();
		this.logradouro = dados.logradouro();
		this.bairro = dados.bairro();
		this.cidade = dados.cidade();
		this.estado = dados.estado();
		this.dtCadastro = LocalDateTime.now();
	}

	
	
}
