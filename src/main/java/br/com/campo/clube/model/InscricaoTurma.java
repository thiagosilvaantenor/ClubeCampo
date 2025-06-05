package br.com.campo.clube.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class InscricaoTurma {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "nome_turma")
	private String nomeTurma;
	private LocalDateTime dtHorario;
	@Column(name = "vagas_disponiveis", nullable = false, length = 2)
	private Integer vagasDisponiveis; 
	
}
