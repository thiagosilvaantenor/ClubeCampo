package br.com.campo.clube.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParticipanteTurmaDependente {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_participante_turma_dependente")
	private Long id;
	@OneToOne
	@JoinColumn(name="id_dependente")
	private Dependente dependente;
	@ManyToOne
	@JoinColumn(name = "id_turma")
	private Turma turma;
}
