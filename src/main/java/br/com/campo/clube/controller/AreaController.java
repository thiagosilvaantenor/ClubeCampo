package br.com.campo.clube.controller;

import br.com.campo.clube.dto.*;
import br.com.campo.clube.model.Area;

import br.com.campo.clube.service.AreaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/area")
public class AreaController {

    @Autowired
    private AreaService service;

    @PostMapping
    public ResponseEntity<Area> criarArea(@RequestBody @Valid AreaDadosCadastro dados) {

        Area salvo = null;
        if (dados != null) {
            //Envia os dados do DTO para a service criar o Associado e salvar
            salvo = service.salvar(dados);
        }
        //Se o service retornou um Associado então deu tudo certo
        if (salvo != null) {
            //Retorna 201, CREATED com o associado no body
            return ResponseEntity.status(201).body(salvo);
        }
        //Se não retorna badRequest/400
        return ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<List<AreaDadosExibicao>> exibirAreas(){
        //Busca os areas no banco de dados
        List<Area> areas = service.buscarTodos();

        List<AreaDadosExibicao> dtos = new ArrayList<>();

        //Itera sobre a lista de areas, para cada item cria um DTO com os dados desse associado
        areas.forEach( area -> dtos.add(new AreaDadosExibicao(
                area.getId(), area.getNomeArea(),
                area.getReservavel(), area.getQuantidade())
        ));
        //Retorna 200 com os DTOS no body
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AreaDadosExibicao> exibirArea(@PathVariable Long id){
        //Busca o area no banco de dados é retornado um Optional, validação feita no service
        Area encontrado = service.buscarAreaPeloId(id);

        //Pega o area e com os dados dele cria um DTO pra ser retornado com ok/200
        return ResponseEntity.ok(new AreaDadosExibicao(
                        encontrado.getId(), encontrado.getNomeArea(),
                        encontrado.getReservavel(), encontrado.getQuantidade())
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarArea(@PathVariable Long id, @RequestBody AreaDadosCadastro dados) {
        //Busca o associado no banco de dados é retornado um Optional
        Area encontrado = service.buscarAreaPeloId(id);
        AreaDadosExibicao atualizado = service.atualizar(encontrado, dados);
        return ResponseEntity.ok(atualizado);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> excluir(@PathVariable Long id){
        //Busca o area no banco de dados é retornado um Optional, validação feita no service
        Area encontrado = service.buscarAreaPeloId(id);
        service.excluir(encontrado);
        return ResponseEntity.ok().build();
    }

}
