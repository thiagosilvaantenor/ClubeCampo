package br.com.campo.clube.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.campo.clube.model.ParticipanteTurmaAssociado;

public interface ParticipaneteTurmaRepository extends  JpaRepository<ParticipanteTurmaAssociado, Long>{

}
