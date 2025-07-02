package br.com.campo.clube.controller;

import br.com.campo.clube.dto.*;
import br.com.campo.clube.model.CobrancaMensal;
import br.com.campo.clube.model.PagamentoRealizado;
import br.com.campo.clube.service.CobrancaMensalService;
import br.com.campo.clube.service.PagamentoRealizadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pagamento")
public class PagamentoRealizadoController {
    @Autowired
    private PagamentoRealizadoService service;

    @PostMapping
    public ResponseEntity<PagamentoRealizadoDadosExibicao> gerarPagamento(@RequestBody @Valid PagamentoRealizadoDadosCadastro dados){
        PagamentoRealizado salvo = null;
        if (dados != null) {
            //Envia os dados do DTO para a service criar o Pagamento e salvar
            salvo = service.salvar(dados);
        }
        //Se o service retornou um Pagamento então deu tudo certo
        if (salvo != null) {
            //Retorna 201, CREATED com o pagamento no body
            return ResponseEntity.status(201).body(toPagamentoRealizadoDadosExibicao(salvo));
        }
        //Se não retorna badRequest/400
        return ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<List<PagamentoRealizadoDadosExibicao>> exibirPagamentos(){
        //Busca as pagamento no banco de dados
        List<PagamentoRealizado> pagamento = service.buscarTodos();

        List<PagamentoRealizadoDadosExibicao> dtos = new ArrayList<>();

        //Itera sobre a lista de pagamento, para cada item cria um DTO com os dados desse cobranca
        pagamento.forEach(cobranca -> dtos.add(toPagamentoRealizadoDadosExibicao(cobranca)));
        //Retorna 200 com os DTOS no body
        return ResponseEntity.ok(dtos);
    }


    @GetMapping("/{id}")
    public ResponseEntity<PagamentoRealizadoDadosExibicao> exibirPagamento(@PathVariable Long id){
        //Busca as cobrancas no banco de dados
       Optional<PagamentoRealizado> pagamento = service.buscarPeloId(id);
       if (pagamento.isPresent()){
           //Retorna 200 com os DTOS no body
           return ResponseEntity.ok(toPagamentoRealizadoDadosExibicao(pagamento.get()));
       }
        //Se não retorna badRequest/400
        return ResponseEntity.badRequest().build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarPagamento(@PathVariable Long id, @RequestBody PagamentoRealizadoDadosCadastro dados) {
        //Busca o pagamento no banco de dados é retornado um Optional
        Optional<PagamentoRealizado> pagamento = service.buscarPeloId(id);
        //Caso não encontre o pagamento retorna
        if (pagamento.isEmpty()){
            return ResponseEntity.badRequest().body("Erro, id informado não pertence a nenhum pagamento");
        }
        PagamentoRealizado encontrado = pagamento.get();
        PagamentoRealizadoDadosExibicao atualizado = service.atualizar(encontrado, dados);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> excluir(@PathVariable Long id){
        //Busca o pagamento no banco de dados é retornado um Optional
        Optional<PagamentoRealizado> pagamento = service.buscarPeloId(id);
        //Caso não encontre o pagamento retorna 404
        if (pagamento.isEmpty()){
            return ResponseEntity.badRequest().body("Erro, id informado não pertence a nenhum pagamento");
        }
        PagamentoRealizado encontrado = pagamento.get();
        service.excluir(encontrado);

        return ResponseEntity.ok().build();
    }

    private PagamentoRealizadoDadosExibicao toPagamentoRealizadoDadosExibicao(PagamentoRealizado pagamento){
        return new PagamentoRealizadoDadosExibicao(
                pagamento.getId(),
                pagamento.getDtPagamento(),
                pagamento.getFormaPagamento(),
                new CobrancaMensalDadosExibicao(
                        pagamento.getCobrancaMensal().getId(),
                        new AssociadoSimplesDTO(pagamento.getCobrancaMensal().getAssociado().getId(),
                                pagamento.getCobrancaMensal().getAssociado().getNomeCompleto()),
                        pagamento.getCobrancaMensal().getDtVencimento(), pagamento.getCobrancaMensal().getValorPadrao(),
                        pagamento.getCobrancaMensal().getValorFinal(), pagamento.getCobrancaMensal().getMesAno(),
                        pagamento.getCobrancaMensal().getPago())
        );
    }
}
