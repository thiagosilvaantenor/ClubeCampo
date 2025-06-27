package br.com.campo.clube.controller;

import br.com.campo.clube.dto.*;
import br.com.campo.clube.model.Area;
import br.com.campo.clube.model.Associado;
import br.com.campo.clube.service.AreaService;
import br.com.campo.clube.service.AssociadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        areas.forEach( area -> {
            dtos.add(new AreaDadosExibicao(
                    area.getId(), area.getNomeArea(), area.getTipoArea(),
                    area.getValorArea(),area.getReservavel(), area.getQuantidade())
            );
        });
        //Retorna 200 com os DTOS no body
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AreaDadosExibicao> exibirArea(@PathVariable Long id){
        //Busca o area no banco de dados é retornado um Optional
        Optional<Area> area = service.buscarAreaPeloId(id);

        //Verifica se no Optional contém o area
        if (area.isPresent()) {
            //Se sim, pega o area e com os dados dele cria um DTO pra ser retornado com ok/200
            Area encontrado = area.get();
            return ResponseEntity.ok(new AreaDadosExibicao(
                            encontrado.getId(), encontrado.getNomeArea(), encontrado.getTipoArea(),
                            encontrado.getValorArea(),encontrado.getReservavel(), encontrado.getQuantidade())
            );
        }
        //Caso o Optional não contenha um area, retorna not found/404
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarAssociado(@PathVariable Long id, @RequestBody AreaDadosCadastro dados) {
        //Busca o associado no banco de dados é retornado um Optional
        Optional<Area> area = service.buscarAreaPeloId(id);
        //Caso não encontre o associado retorna
        if (area.isEmpty()){
            return ResponseEntity.badRequest().body("Erro, id informado não pertence a nenhum associado");
        }
        Area encontrado = area.get();
        AreaDadosExibicao atualizado = service.atualizar(encontrado, dados);
        return ResponseEntity.ok(atualizado);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> excluir(@PathVariable Long id){
        //Busca o area no banco de dados é retornado um Optional
        Optional<Area> area = service.buscarAreaPeloId(id);
        //Caso não encontre o area retorna 404
        if (area.isEmpty()){
            return ResponseEntity.badRequest().body("Erro, id informado não pertence a nenhum area");
        }
        Area encontrado = area.get();
        service.excluir(encontrado);

        return ResponseEntity.ok().build();
    }
}
