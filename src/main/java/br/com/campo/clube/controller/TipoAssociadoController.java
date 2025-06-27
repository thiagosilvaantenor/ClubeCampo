package br.com.campo.clube.controller;

import br.com.campo.clube.dto.AreaDadosExibicao;
import br.com.campo.clube.dto.TipoAssociadoDTO;
import br.com.campo.clube.dto.TipoAssociadoDadosExibicao;
import br.com.campo.clube.model.Area;
import br.com.campo.clube.model.TipoAssociado;
import br.com.campo.clube.service.TipoAssociadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tipoAssociado")
public class TipoAssociadoController {

    @Autowired
    private TipoAssociadoService service;

    //Insert
    @PostMapping
    public ResponseEntity<Object> cadastrarTipo(@RequestBody @Valid TipoAssociadoDTO dados){
        if (dados != null){
            TipoAssociado salvo = service.salvar(dados);
            if (salvo != null)
                return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
        }
        //Se dados nulo ou problema ao salvar retorna 400
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    //SELECTS
    @GetMapping
    public ResponseEntity<List<TipoAssociadoDadosExibicao>> exibirTodos(){
        List<TipoAssociado> tipos = service.buscaTodos();
        List<TipoAssociadoDadosExibicao> dtos = new ArrayList<>();
        tipos.forEach((tipo) -> dtos.add(new TipoAssociadoDadosExibicao(tipo.getId(), tipo.getNome(), tipo.getValor())));
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> exibirTipoAssocido(@PathVariable Long id){
        Optional<TipoAssociado> tipo = service.buscarTipoAssociadoPorId(id);
        if(tipo != null){
            TipoAssociado encontrado = tipo.get();
            return ResponseEntity.status(HttpStatus.OK).body(new TipoAssociadoDadosExibicao(
                    encontrado.getId(),
                    encontrado.getNome(),
                    encontrado.getValor()));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarTipoAssociado(@PathVariable Long id, @RequestBody TipoAssociadoDTO dados) {
        //Busca o associado no banco de dados é retornado um Optional
        Optional<TipoAssociado> tipoAssociado = service.buscarTipoAssociadoPorId(id);

        //Caso não encontre o associado retorna
        if (tipoAssociado.isEmpty()){
            return ResponseEntity.badRequest().body("Erro, id informado não pertence a nenhum associado");
        }
        TipoAssociado encontrado = tipoAssociado.get();
        TipoAssociadoDadosExibicao atualizado = service.atualizar(encontrado, dados);
        return ResponseEntity.ok(atualizado);
    }

    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> excluir(@PathVariable Long id){
        //Busca o area no banco de dados é retornado um Optional
        Optional<TipoAssociado> area = service.buscarTipoAssociadoPorId(id);
        //Caso não encontre o area retorna 404
        if (area.isEmpty()){
            return ResponseEntity.badRequest().body("Erro, id informado não pertence a nenhum area");
        }
        TipoAssociado encontrado = area.get();
        service.excluir(encontrado);

        return ResponseEntity.ok().build();
    }

}
