package br.com.campo.clube.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;

import br.com.campo.clube.dto.CobrancaMensalDadosCadastro;
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
	private LocalDate dtVencimento;
	@Column(name = "valor_padrao", precision = 10, scale = 2)
	// Atributo que representa o valor padrão da mensalidae, sem multas de atraso
	private BigDecimal valorPadrao;
	@Column(name = "valor_final", precision = 10, scale = 2)
	private BigDecimal valorFinal;
	@Column(name = "mes_ano", nullable = false)
	private YearMonth mesAno;
	@Column(name = "em_Atraso", nullable = true)
	// Atributo utilizado para representar mensalidade atrasada
	private Boolean emAtraso;

	public CobrancaMensal(CobrancaMensalDadosCadastro dados) {
		this.dtVencimento = dados.dtVencimento();
		//valorPadrão será de acordo com o valor do tipo do associado
		//valorFinal será ajustado no service
		this.mesAno = dados.mesAno();
		//Em atraso, vai ser verificada no service
	}
}
