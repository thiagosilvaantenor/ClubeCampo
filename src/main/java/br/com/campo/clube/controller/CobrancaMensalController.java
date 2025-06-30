package br.com.campo.clube.controller;

import br.com.campo.clube.dto.*;
import br.com.campo.clube.model.CobrancaMensal;
import br.com.campo.clube.service.CobrancaMensalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
        cobrancas.forEach(associado -> {
            dtos.add(toCobrancaMensalDadosExibicao(associado));
        });
        //Retorna 200 com os DTOS no body
        return ResponseEntity.ok(dtos);
    }
    private CobrancaMensalDadosExibicao toCobrancaMensalDadosExibicao(CobrancaMensal cobranca){
        return new CobrancaMensalDadosExibicao(
                cobranca.getId(),
                new AssociadoSimplesDTO(cobranca.getAssociado().getId(), cobranca.getAssociado().getNomeCompleto()),
                cobranca.getDtVencimento(),
                cobranca.getValorPadrao(),
                cobranca.getValorFinal(),
                cobranca.getMesAno(),
                cobranca.getEmAtraso());
    }
}
