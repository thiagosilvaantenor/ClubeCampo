package br.com.campo.clube.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.campo.clube.model.Associado;
import br.com.campo.clube.model.Reserva;

public interface ReservaRepository extends  JpaRepository<Reserva, Long>{

}
