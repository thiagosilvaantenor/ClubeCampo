package br.com.campo.clube.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//Representa os participantes associado que estão em uma turma
public class ParticipanteTurmaAssociado {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_participante_turma_dependente")
	private Long id;
	@OneToOne
	@JoinColumn(name="id_associado")
	private Associado associado;
	@ManyToOne
	@JoinColumn(name = "id_turma")
	private Turma turma;
}
