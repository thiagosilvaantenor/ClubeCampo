package br.com.campo.clube.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Currency;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class PagamentoReserva {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_pagamento_reserva")
	private Long id;
	@ManyToOne
	@JoinColumn(name="associado_id", nullable=false)
	private Associado associado;
	@ManyToOne
	//Se for a vista é um registro, se for a prazo e um para cada parcela (muitos)
	@JoinColumn(name="reserva_id", nullable=false)
	private Reserva reserva;
	@Column(name = "dt_pagamento")
	private LocalDateTime dtPagamento;
	@Column(name = "valor_pagamento")
	private Currency valorPagamento;
	@Column(name = "forma_pagamento")
	private String formaPagamento;
	@Column(name = "mes_ano")
	private LocalDate mesAno;
	@Column(name = "em_Atraso")
	private Boolean emAtraso;
}
