package br.com.campo.clube.controller;

import br.com.campo.clube.dto.*;
import br.com.campo.clube.model.Associado;
import br.com.campo.clube.model.Reserva;
import br.com.campo.clube.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reserva")
public class ReservaController {
    @Autowired
    private ReservaService service;

    @PostMapping
    public ResponseEntity<Reserva> criarReserva(@RequestBody @Valid ReservaDadosCadastro dados) {

        Reserva salvo = null;
        if (dados != null) {
            //Envia os dados do DTO para a service criar o Reserva e salvar
            salvo = service.salvar(dados);
        }
        //Se o service retornou um Reserva então deu tudo certo
        if (salvo != null) {
            //Retorna 201, CREATED com o reserva no body
            return ResponseEntity.status(201).body(salvo);
        }
        //Se não retorna badRequest/400
        return ResponseEntity.badRequest().build();
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
        //Busca o associado no banco de dados é retornado um Optional
        Optional<Reserva> reserva = service.buscarReservaPeloId(id);

        //Verifica se no Optional contém o associado
        if (reserva.isPresent()) {
            //Se sim, pega o associado e com os dados dele cria um DTO pra ser retornado com ok/200
            Reserva encontrado = reserva.get();
            return ResponseEntity.ok(new ReservaDadosExibicao(encontrado.getId(),
                    encontrado.getArea(), encontrado.getAssociado(), encontrado.getDtReservaInicio(),
                    encontrado.getDtReservaFim(), encontrado.getStatusReserva())
            );
        }
        //Caso o Optional não contenha um associado, retorna not found/404
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarReserva(@PathVariable Long id, @RequestBody ReservaDadosCadastro dados) {
        //Busca o Reserva no banco de dados é retornado um Optional
        Optional<Reserva> reserva = service.buscarReservaPeloId(id);
        //Caso não encontre o Reserva retorna
        if (reserva.isEmpty()){
            return ResponseEntity.badRequest().body("Erro, id informado não pertence a nenhum Reserva");
        }
        Reserva encontrado = reserva.get();
        ReservaDadosExibicao atualizado = service.atualizar(encontrado,dados);
        return ResponseEntity.ok(atualizado);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> excluir(@PathVariable Long id){
        //Busca o Reserva no banco de dados é retornado um Optional
        Optional<Reserva> reserva = service.buscarReservaPeloId(id);
        //Caso não encontre o Reserva retorna 404
        if (reserva.isEmpty()){
            return ResponseEntity.badRequest().body("Erro, id informado não pertence a nenhuma Reserva");
        }
        Reserva encontrado = reserva.get();
        service.excluir(encontrado);

        return ResponseEntity.ok().build();
    }
}
