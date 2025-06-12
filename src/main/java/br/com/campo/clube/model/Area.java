package br.com.campo.clube.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Area {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_area")
	private Long id;
	@Column(name="nome_area")
	private String nomeArea;
	@Column(name="tipo_area")
	private String tipoArea;
	@Column(name="valor_area")
	private Double valorArea;
	@Column(name="reservado")
	private Boolean reservado;
}
