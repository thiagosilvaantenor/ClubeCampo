package br.com.campo.clube.model;

import br.com.campo.clube.dto.AreaDadosCadastro;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode(of = "id")
public class Area {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_area")
	private Long id;
	@Column(name="nome_area")
	private String nomeArea;
	@Column(name="reservavel")
	private Boolean reservavel;
	@Column(name = "quantidade")
	private Integer quantidade;

	public Area(AreaDadosCadastro dados) {
		this.nomeArea = dados.nomeArea();
		this.reservavel = dados.reservavel();
		this.quantidade = dados.quantidade();
	}
}
