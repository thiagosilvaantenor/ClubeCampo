package br.com.campo.clube.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.campo.clube.model.ParticipanteTurmaAssociado;

import java.util.List;

public interface ParticipanteTurmaAssociadoRepository extends  JpaRepository<ParticipanteTurmaAssociado, Long>{

    List<ParticipanteTurmaAssociado> findByAssociadoId(Long id);
}
