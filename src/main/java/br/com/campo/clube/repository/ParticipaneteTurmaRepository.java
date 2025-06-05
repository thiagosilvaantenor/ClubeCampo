package br.com.campo.clube.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.campo.clube.model.Associado;
import br.com.campo.clube.model.ParticipanteTurma;

public interface ParticipaneteTurmaRepository extends  JpaRepository<ParticipanteTurma, Long>{

}
