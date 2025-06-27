package br.com.campo.clube.controller;

import br.com.campo.clube.dto.TipoAssociadoDTO;
import br.com.campo.clube.model.TipoAssociado;
import br.com.campo.clube.service.TipoAssociadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tipoAssociado")
public class TipoAssociadoController {

    @Autowired
    private TipoAssociadoService service;


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
    @GetMapping
    public ResponseEntity<List<TipoAssociadoDTO>> exibirTodos(){
        List<TipoAssociado> tipos = service.buscaTodos();
        List<TipoAssociadoDTO> dtos = new ArrayList<>();
        tipos.forEach((tipo) -> {
            dtos.add(new TipoAssociadoDTO(tipo.getNome(), tipo.getValor()));
        });
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> exibirTipoAssocido(@PathVariable Long id){
        TipoAssociado tipo = service.buscaTipoAssociado(id);
        if(tipo != null)
            return ResponseEntity.status(HttpStatus.OK).body(new TipoAssociadoDTO(tipo.getNome(), tipo.getValor()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
