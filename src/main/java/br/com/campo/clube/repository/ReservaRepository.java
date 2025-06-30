package br.com.campo.clube.repository;

import br.com.campo.clube.model.Area;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.campo.clube.model.Associado;
import br.com.campo.clube.model.Reserva;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservaRepository extends  JpaRepository<Reserva, Long>{

    @Query("""
        SELECT r FROM Reserva r
        WHERE r.area = :area
        AND (
            (:dtInicioNovaReserva < r.dtReservaFim AND :dtFimNovaReserva > r.dtReservaInicio)
        )
        AND (:idReservaAtual IS NULL OR r.id != :idReservaAtual)
    """)
    List<Reserva> findConflito(
            @Param("area") Area area,
            @Param("dtInicioNovaReserva") LocalDateTime dtInicioNovaReserva,
            @Param("dtFimNovaReserva") LocalDateTime dtFimNovaReserva,
            @Param("idReservaAtual") Long idReservaAtual
    );
}
