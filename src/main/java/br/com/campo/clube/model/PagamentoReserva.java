package br.com.campo.clube.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Currency;

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
	private Long id;
	@ManyToOne
	@JoinColumn(name="associado_id", nullable=false)
	private Associado associado;
	@ManyToOne
	//Se for a vista é um registro, se for a prazo e um para cada parcela (muitos)
	@JoinColumn(name="reserva_id", nullable=false)
	private Reserva reserva;
	private LocalDateTime dtPagamento;
	private Currency valorPagamento;
	private String formaPagamento;
	private LocalDate mesAno;
	private Boolean emAtraso;
}
