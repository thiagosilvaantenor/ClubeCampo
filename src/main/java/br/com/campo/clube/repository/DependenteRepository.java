package br.com.campo.clube.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.campo.clube.model.Associado;
import br.com.campo.clube.model.Dependente;

import java.util.List;

public interface DependenteRepository extends  JpaRepository<Dependente, Long>{
    List<Dependente> findByAssociadoId(Long associadoId);
}
