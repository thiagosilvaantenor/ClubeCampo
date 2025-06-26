package br.com.campo.clube.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "pagamento_realizado")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PagamentoRealizado {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_pagamento_realizado")
	private Long id;
//	FIXME: Verificar se é necessario ter o mapemaneto do acessociado, já que o mesmo está presente em CobrancaMensal
//	@ManyToOne
//	@JoinColumn(name="associado_id", nullable=false)
//	private Associado associado;

	@Column(name = "dt_pagamento")
	private LocalDateTime dtPagamento;
	@Column(name = "forma_pagamento")
	private String formaPagamento;

	@OneToOne
	@JoinColumn(name = "id_cobranca_mensal")
	private CobrancaMensal cobrancaMensal;
}
