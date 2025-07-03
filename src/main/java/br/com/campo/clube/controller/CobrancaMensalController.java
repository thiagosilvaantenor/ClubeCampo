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
            //Envia os dados do DTO para a service criar a Cobrança e salvar
            salvo = service.salvar(dados);
        }
        //Se o service retornou uma Cobrança então deu tudo certo
        if (salvo != null) {
            //Retorna 201, CREATED com o cobrança no body

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
        //Busca a cobranca no banco de dados, validação feita na service
       CobrancaMensal cobranca = service.buscarPeloId(id);
       //Retorna 200 com o DTO no body
       return ResponseEntity.ok(toCobrancaMensalDadosExibicao(cobranca));
    }

    @GetMapping("/associado/{id}")
    public ResponseEntity<List<CobrancaMensalDadosExibicao>> exibirCobrancaDoAssociado(@PathVariable Long id){
        //Busca as cobrancas do associado no banco de dados, validação feita na service
        List<CobrancaMensal> cobrancas = service.buscarPeloAssociado(id);
        List<CobrancaMensalDadosExibicao> dtos = new ArrayList<>();
        cobrancas.forEach(cobranca -> dtos.add(toCobrancaMensalDadosExibicao(cobranca)));
        //Retorna 200 com os DTOS no body
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarCobranca(@PathVariable Long id, @RequestBody CobrancaMensalDadosAtualizacao dados) {
        //Busca o cobranca no banco de dados é retornado um Optional, validação feita na service
        CobrancaMensal encontrado = service.buscarPeloId(id);
        CobrancaMensalDadosExibicao atualizado = service.atualizar(encontrado, dados);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> excluir(@PathVariable Long id){
        //Busca o cobranca no banco de dados é retornado um Optional, validação feita na service
        CobrancaMensal encontrado = service.buscarPeloId(id);
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
