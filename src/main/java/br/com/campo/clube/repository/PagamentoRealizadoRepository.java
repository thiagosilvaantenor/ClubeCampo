package br.com.campo.clube.repository;

import br.com.campo.clube.model.PagamentoRealizado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PagamentoRealizadoRepository extends JpaRepository<PagamentoRealizado, Long> {
    List<PagamentoRealizado> findByCobrancaMensalAssociadoId(Long id);
}
