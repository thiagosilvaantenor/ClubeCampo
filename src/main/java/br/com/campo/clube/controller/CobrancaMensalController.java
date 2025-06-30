package br.com.campo.clube.controller;

import br.com.campo.clube.dto.*;
import br.com.campo.clube.model.Associado;
import br.com.campo.clube.model.CobrancaMensal;
import br.com.campo.clube.service.CobrancaMensalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cobranca")
public class CobrancaMensalController {
    @Autowired
    private CobrancaMensalService service;

    @PostMapping
    public ResponseEntity<CobrancaMensalDadosExibicao> gerarCobranca(@RequestBody @Valid CobrancaMensalDadosCadastro dados){
        CobrancaMensal salvo = null;
        if (dados != null) {
            //Envia os dados do DTO para a service criar o Associado e salvar
            salvo = service.salvar(dados);
        }
        //Se o service retornou um Associado então deu tudo certo
        if (salvo != null) {
            //Retorna 201, CREATED com o associado no body

            return ResponseEntity.status(201).body(toCobrancaMensalDadosExibicao(salvo));
        }
        //Se não retorna badRequest/400
        return ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<List<CobrancaMensalDadosExibicao>> exibirCobrancas(){
        //Busca as cobrancas no banco de dados
        List<CobrancaMensal> cobrancas = service.buscarTodos();

        List<CobrancaMensalDadosExibicao> dtos = new ArrayList<>();

        //Itera sobre a lista de cobrancas, para cada item cria um DTO com os dados desse cobranca
        cobrancas.forEach(cobranca -> {
            dtos.add(toCobrancaMensalDadosExibicao(cobranca));
        });
        //Retorna 200 com os DTOS no body
        return ResponseEntity.ok(dtos);
    }


    @GetMapping("/{id}")
    public ResponseEntity<CobrancaMensalDadosExibicao> exibirCobranca(@PathVariable Long id){
        //Busca as cobrancas no banco de dados
       Optional<CobrancaMensal> cobranca = service.buscarPeloId(id);
       if (cobranca.isPresent()){
           //Retorna 200 com os DTOS no body
           return ResponseEntity.ok(toCobrancaMensalDadosExibicao(cobranca.get()));
       }
        //Se não retorna badRequest/400
        return ResponseEntity.badRequest().build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarCobranca(@PathVariable Long id, @RequestBody CobrancaMensalDadosAtualizacao dados) {
        //Busca o cobranca no banco de dados é retornado um Optional
        Optional<CobrancaMensal> cobranca = service.buscarPeloId(id);
        //Caso não encontre o cobranca retorna
        if (cobranca.isEmpty()){
            return ResponseEntity.badRequest().body("Erro, id informado não pertence a nenhum cobranca");
        }
        CobrancaMensal encontrado = cobranca.get();
        CobrancaMensalDadosExibicao atualizado = service.atualizar(encontrado, dados);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> excluir(@PathVariable Long id){
        //Busca o cobranca no banco de dados é retornado um Optional
        Optional<CobrancaMensal> cobranca = service.buscarPeloId(id);
        //Caso não encontre o cobranca retorna 404
        if (cobranca.isEmpty()){
            return ResponseEntity.badRequest().body("Erro, id informado não pertence a nenhum cobranca");
        }
        CobrancaMensal encontrado = cobranca.get();
        service.excluir(encontrado);

        return ResponseEntity.ok().build();
    }


    private CobrancaMensalDadosExibicao toCobrancaMensalDadosExibicao(CobrancaMensal cobranca){
        return new CobrancaMensalDadosExibicao(
                cobranca.getId(),
                new AssociadoSimplesDTO(cobranca.getAssociado().getId(), cobranca.getAssociado().getNomeCompleto()),
                cobranca.getDtVencimento(),
                cobranca.getValorPadrao(),
                cobranca.getValorFinal(),
                cobranca.getMesAno(),
                cobranca.getPago());
    }
}
