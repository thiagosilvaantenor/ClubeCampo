package br.com.campo.clube.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cobranca_mensal")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Setter
@Getter
//Entidade para representar a cobrança mensal para participar do campo clube
public class CobrancaMensal {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_cobranca_mensal")
	private Long id;
	@ManyToOne
	//Varias cobranças para cada associado
	@JoinColumn(name="associado_id", nullable=false)
	private Associado associado;
	@Column(name = "dt_vencimento")
	private LocalDateTime dtVencimento;
	@Column(name = "valor_padrao", precision = 10, scale = 2)
	// Atributo que representa o valor padrão da mensalidae, sem multas de atraso
	private BigDecimal valorPadrao;
	@Column(name = "valor_final", precision = 10, scale = 2)
	private BigDecimal valorFinal;
	@Column(name = "mes_ano", nullable = false)
	private YearMonth mesAno;
	@Column(name = "em_Atraso", nullable = true)
	// Atributro utilizado para representar mensalidade atrasada
	private Boolean emAtraso;
}
