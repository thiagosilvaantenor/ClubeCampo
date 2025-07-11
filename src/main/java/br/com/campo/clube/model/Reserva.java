package br.com.campo.clube.model;

import java.time.LocalDateTime;

import br.com.campo.clube.dto.ReservaDadosCadastro;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Reserva {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_reserva")
	private Long id;
	@ManyToOne
	@JoinColumn(name="area_id", nullable=false)
	private Area area;
	@ManyToOne
	@JoinColumn(name="associado_id", nullable=false)
	private Associado associado;
	@Column(name = "dt_reserva_inicio")
	private LocalDateTime dtReservaInicio;
	@Column(name = "dt_reserva_fim")
	private LocalDateTime dtReservaFim;
	@Column(name = "status_reserva")
	private String statusReserva;


	public Reserva(ReservaDadosCadastro dados) {
		this.dtReservaInicio = dados.dtReservaInicio();
		this.dtReservaFim = dados.dtReservaFim();
		this.statusReserva = dados.statusReserva();
	}
}
