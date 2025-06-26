package br.com.campo.clube.repository;

import br.com.campo.clube.model.TipoAssociado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TipoAssociadoRepository extends JpaRepository<TipoAssociado, Long> {
    List<TipoAssociado> findByNome(String nome);
}
