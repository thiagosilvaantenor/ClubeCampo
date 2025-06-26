package br.com.campo.clube.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.campo.clube.model.CobrancaMensal;

public interface PagamentoReservaRepository extends  JpaRepository<CobrancaMensal, Long>{

}
