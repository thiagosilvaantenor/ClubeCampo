package br.com.campo.clube.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ParticipanteTurma {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	//De onde vem essas fk?
	// private Turma turma;
	// private Participante participante;
	private LocalDateTime dtHorario;
	@Column(name = "vagas_disponiveis", nullable = false, length = 2)
	private Integer vagasDisponiveis;
	private Boolean dependente;
}
