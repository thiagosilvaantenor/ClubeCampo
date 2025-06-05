package br.com.campo.clube.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.campo.clube.model.Associado;
import br.com.campo.clube.model.PagamentoReserva;

public interface PagamentoReservaRepository extends  JpaRepository<PagamentoReserva, Long>{

}
