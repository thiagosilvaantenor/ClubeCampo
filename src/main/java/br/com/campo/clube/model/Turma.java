package br.com.campo.clube.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Turma {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_turma")
	private Long id;
	@Column(name = "nome_turma")
	private String nomeTurma;
	@Column(name="dt_horario")
	private LocalDateTime dtHorario;
	@Column(name = "vagas_disponiveis", nullable = false, length = 2)
	private Integer vagasDisponiveis;
	@OneToMany(mappedBy = "turma")
	private List<ParticipanteTurmaAssociado> associados = new ArrayList<>();
	@OneToMany(mappedBy = "turma")
	private List<ParticipanteTurmaDependente> dependentes = new ArrayList<>();
	@Column(name = "vagas_esgotadas")
	private Boolean vagasEsgotadas;
	
	//participantes precisa de um Setter especifico
	public void setParticipantesTurma(List<ParticipanteTurmaAssociado> participantes) {
		
		//Verifica se a quantidade de participantes é menor ou igual a quantidade de vagasDisponiveis
		if (participantes.size() <= this.vagasDisponiveis)
			this.associados = participantes;
		if(this.associados.size() == this.vagasDisponiveis)
			System.out.println("Não é possivel adicionar mais participantes");
	}
	
	
	
	
	
	
}
