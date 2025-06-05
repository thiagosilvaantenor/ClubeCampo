package br.com.campo.clube.model;

import java.util.Currency;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Area {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String nomeArea;
	private String tipoArea;
	private Currency valorArea;
	private Boolean reservado;
}
