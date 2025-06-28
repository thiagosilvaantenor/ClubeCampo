package br.com.campo.clube.dto;

import com.fasterxml.jackson.annotation.JsonFormat;


import java.time.LocalDateTime;

public record ReservaDadosCadastro(
        Long areaId,
        Long associadoId,
        @JsonFormat(pattern="dd-MM-yyyy HH:mm")
        LocalDateTime dtReservaInicio,
        @JsonFormat(pattern="dd-MM-yyyy HH:mm")
        LocalDateTime dtReservaFim,
        String statusReserva
) {
}
