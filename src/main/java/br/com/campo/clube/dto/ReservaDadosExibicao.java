package br.com.campo.clube.dto;


import br.com.campo.clube.model.Area;
import br.com.campo.clube.model.Associado;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ReservaDadosExibicao(
        Long id,
        Area area,
        Associado associado,
        @JsonFormat(pattern="dd-MM-yyyy HH:mm")
        LocalDateTime dtReservaInicio,
        @JsonFormat(pattern="dd-MM-yyyy HH:mm")
        LocalDateTime dtReservaFim,
        String statusReserva
) {
}
