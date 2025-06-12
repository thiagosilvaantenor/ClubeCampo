package br.com.campo.clube.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParticipanteTurma {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_participante_turma")
	private Long id;
	@OneToOne
	@JoinColumn(name="id_associado", nullable = true)
	private Associado associado;
	@OneToOne
	@JoinColumn(name="id_dependente", nullable=true)
	private Dependente dependente;
	@ManyToMany
	@JoinTable(
      name = "turma_participante",
	  joinColumns = @JoinColumn(name = "id_participante"), 
	  inverseJoinColumns = @JoinColumn(name = "id_turma"))
	private List<Turma> turmas;
}
