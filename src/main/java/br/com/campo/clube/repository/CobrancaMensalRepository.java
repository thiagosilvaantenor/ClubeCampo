package br.com.campo.clube.repository;

import br.com.campo.clube.model.Associado;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.campo.clube.model.CobrancaMensal;

import java.util.List;
import java.util.Optional;

public interface CobrancaMensalRepository extends  JpaRepository<CobrancaMensal, Long>{

    List<CobrancaMensal> findByAssociadoAndPago(Associado associado, boolean pago);

    List<CobrancaMensal> findByAssociadoId(Long id);
}
