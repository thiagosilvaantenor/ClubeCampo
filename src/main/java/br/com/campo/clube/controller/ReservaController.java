package br.com.campo.clube.controller;

import br.com.campo.clube.dto.*;
import br.com.campo.clube.model.Reserva;
import br.com.campo.clube.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/reserva")
public class ReservaController {
    @Autowired
    private ReservaService service;

    @PostMapping
    public ResponseEntity<Reserva> criarReserva(@RequestBody @Valid ReservaDadosCadastro dados) {
        //Se o service retornou um Reserva então deu tudo certo
          Reserva salvo = service.salvar(dados);
        //Envia os dados do DTO para a service criar o Reserva e salvar
        //Retorna 201, CREATED com o reserva no body
        return ResponseEntity.status(201).body(salvo);
    }

    @GetMapping
    public ResponseEntity<List<ReservaDadosExibicao>> exibirReservas(){
        //Busca os associados no banco de dados
        List<Reserva> reservas = service.buscarTodos();

        List<ReservaDadosExibicao> dtos = new ArrayList<>();

        //Itera sobre a lista de associados, para cada item cria um DTO com os dados desse associado
        reservas.forEach( reserva ->
                dtos.add(new ReservaDadosExibicao(reserva.getId(),
                        reserva.getArea(), reserva.getAssociado(), reserva.getDtReservaInicio(), reserva.getDtReservaFim(),
                        reserva.getStatusReserva())));
        //Retorna 200 com os DTOS no body
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaDadosExibicao> exibirReserva(@PathVariable Long id){
        //Busca o associado no banco de dados é retornado um Optional, a validação é feita no service
        Reserva encontrado = service.buscarReservaPeloId(id);

        return ResponseEntity.ok(new ReservaDadosExibicao(encontrado.getId(),
                encontrado.getArea(), encontrado.getAssociado(), encontrado.getDtReservaInicio(),
                encontrado.getDtReservaFim(), encontrado.getStatusReserva())
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarReserva(@PathVariable Long id, @RequestBody ReservaDadosCadastro dados) {
        //Busca o Reserva no banco de dados é retornado um Optional, validação feita no service
        Reserva encontrado = service.buscarReservaPeloId(id);
        ReservaDadosExibicao atualizado = service.atualizar(encontrado,dados);
        return ResponseEntity.ok(atualizado);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> excluir(@PathVariable Long id){
        //Busca o Reserva no banco de dados é retornado um Optional, validação feita no service
        Reserva encontrado = service.buscarReservaPeloId(id);
        service.excluir(encontrado);
        return ResponseEntity.ok().build();
    }
}
