package br.com.campo.clube.controller;

import br.com.campo.clube.dto.AssociadoSimplesDTO;
import br.com.campo.clube.dto.DependenteCadastroDTO;
import br.com.campo.clube.dto.DependenteExibicaoDTO;
import br.com.campo.clube.model.Dependente;
import br.com.campo.clube.service.DependenteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dependente")
public class DependenteController {

    @Autowired
    private DependenteService service;

    @PostMapping
    public ResponseEntity<DependenteExibicaoDTO> criarDependente(@RequestBody @Valid DependenteCadastroDTO dados) {
        Dependente salvo = service.salvar(dados);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDependenteExibicaoDTO(salvo));
    }

    //Exibe todos os dependentes cadastrados
    @GetMapping()
    public ResponseEntity<List<DependenteExibicaoDTO>> exibirDependentes() {
        List<Dependente> dependentes = service.buscarTodos();
        List<DependenteExibicaoDTO> dtos = dependentes.stream()
                .map(this::toDependenteExibicaoDTO).toList();
        return ResponseEntity.ok(dtos);
    }

    //Exibe todos os dependentes do associado
    @GetMapping("/associado/{id}")
    public ResponseEntity<List<DependenteExibicaoDTO>> exibirDependentesPorAssociado(@PathVariable Long id) {
        List<Dependente> dependentes = service.buscarPorAssociado(id);
        List<DependenteExibicaoDTO> dtos = dependentes.stream()
                .map(this::toDependenteExibicaoDTO).toList();
        return ResponseEntity.ok(dtos);
    }
    //Exibe um dependente
    @GetMapping("/{id}")
    public ResponseEntity<DependenteExibicaoDTO> exibirDependente(@PathVariable Long id){
        Dependente dependente = service.buscarPeloId(id);
        return ResponseEntity.ok(toDependenteExibicaoDTO(dependente));

    }

    @PutMapping("/{id}")
    public ResponseEntity<DependenteExibicaoDTO> atualizarDependente(@PathVariable Long id,
                                                 @RequestBody @Valid DependenteCadastroDTO dados) {
        Dependente atualizado = service.atualizar(id, dados);
        return ResponseEntity.ok(toDependenteExibicaoDTO(atualizado));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

    private DependenteExibicaoDTO toDependenteExibicaoDTO(Dependente dependente) {
        AssociadoSimplesDTO associadoDTO = new AssociadoSimplesDTO(
                dependente.getAssociado().getId(),
                dependente.getAssociado().getNomeCompleto()
        );
        return new DependenteExibicaoDTO(
                dependente.getId(),
                dependente.getNome(),
                dependente.getRg(),
                associadoDTO
        );
    }
}