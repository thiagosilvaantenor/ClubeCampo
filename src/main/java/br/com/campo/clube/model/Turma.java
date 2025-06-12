package br.com.campo.clube.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
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
	@ManyToMany(mappedBy = "turmas")
	ParticipanteTurma[] participantes = new ParticipanteTurma[this.vagasDisponiveis];
	
	//Setters
	public void setId(Long id) {
		this.id = id;
	}
	public void setNomeTurma(String nomeTurma) {
		this.nomeTurma = nomeTurma;
	}
	public void setDtHorario(LocalDateTime dtHorario) {
		this.dtHorario = dtHorario;
	}
	public void setVagasDisponiveis(Integer vagasDisponiveis) {
		this.vagasDisponiveis = vagasDisponiveis;
	}
	public void setParticipantes(ParticipanteTurma[] participantes) {
		
		//Verifica se a quantidade de participantes é menor ou igual a quantidade de vagasDisponiveis
		if (participantes.length <= this.vagasDisponiveis)
			this.participantes = participantes;
		if(this.participantes.length == this.vagasDisponiveis)
			System.out.println("Não é possivel adicionar mais participantes");
	}
	
	
	
	
	
	
}
