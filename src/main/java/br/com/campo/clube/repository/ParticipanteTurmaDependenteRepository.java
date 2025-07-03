package br.com.campo.clube.repository;

import br.com.campo.clube.model.ParticipanteTurmaDependente;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipanteTurmaDependenteRepository extends  JpaRepository<ParticipanteTurmaDependente, Long>{

    List<ParticipanteTurmaDependente> findByDependenteId(Long id);
}
