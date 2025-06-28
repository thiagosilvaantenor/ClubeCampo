package br.com.campo.clube.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.com.campo.clube.dto.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.campo.clube.model.Associado;
import br.com.campo.clube.service.AssociadoService;

@RestController
@RequestMapping("/associado")
public class AssociadoController {
	
	@Autowired
	private AssociadoService service;

	@PostMapping
	public ResponseEntity<AssociadoDadosExibicao> criarAssociado(@RequestBody  @Valid AssociadoDadosCadastro dados) {

		Associado salvo = null;
		if (dados != null) {
			//Envia os dados do DTO para a service criar o Associado e salvar
			salvo = service.salvar(dados);
		}
		//Se o service retornou um Associado então deu tudo certo
		if (salvo != null) {
			//Retorna 201, CREATED com o associado no body

			return ResponseEntity.status(201).body(toAssociadoDadosExibicao(salvo));
		}
        //Se não retorna badRequest/400
        return ResponseEntity.badRequest().build();
	}
	
	@GetMapping
	public ResponseEntity<List<AssociadoDadosExibicao>> exibirAssociados(){
		//Busca os associados no banco de dados
		List<Associado> associados = service.buscarTodos();
		
		List<AssociadoDadosExibicao> dtos = new ArrayList<>();
			
		//Itera sobre a lista de associados, para cada item cria um DTO com os dados desse associado
		associados.forEach( associado -> {
			dtos.add(toAssociadoDadosExibicao(associado));
		});
		//Retorna 200 com os DTOS no body
		return ResponseEntity.ok(dtos);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<AssociadoDadosExibicao> exibirAssociado(@PathVariable Long id){
		//Busca o associado no banco de dados é retornado um Optional
		Optional<Associado> associado = service.buscarPeloId(id);
		
		 //Verifica se no Optional contém o associado
		if (associado.isPresent()) {
			 //Se sim, pega o associado e com os dados dele cria um DTO pra ser retornado com ok/200
			Associado encontrado = associado.get();
			return ResponseEntity.ok(toAssociadoDadosExibicao(encontrado));
		}
		 //Caso o Optional não contenha um associado, retorna not found/404
		return ResponseEntity.notFound().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> atualizarAssociado(@PathVariable Long id, @RequestBody AssociadoDadosAtualizacao dados) {
		//Busca o associado no banco de dados é retornado um Optional
		Optional<Associado> associado = service.buscarPeloId(id);
		//Caso não encontre o associado retorna
		if (associado.isEmpty()){
			return ResponseEntity.badRequest().body("Erro, id informado não pertence a nenhum associado");
		}
		Associado encontrado = associado.get();
		AssociadoDadosExibicao atualizado = service.atualizar(encontrado, dados);
		return ResponseEntity.ok(atualizado);
	}


	@DeleteMapping("/{id}")
	public ResponseEntity<Object> excluir(@PathVariable Long id){
		//Busca o associado no banco de dados é retornado um Optional
		Optional<Associado> associado = service.buscarPeloId(id);
		//Caso não encontre o associado retorna 404
		if (associado.isEmpty()){
			return ResponseEntity.badRequest().body("Erro, id informado não pertence a nenhum associado");
		}
		Associado encontrado = associado.get();
		service.excluir(encontrado);

		return ResponseEntity.ok().build();
	}

	//Conversor de Entidade para DTO
	private AssociadoDadosExibicao toAssociadoDadosExibicao(Associado associado){
		return new AssociadoDadosExibicao(associado.getId(),associado.getNomeCompleto(),associado.getRg(),
				associado.getCpf(),new TipoAssociadoDadosExibicao(associado.getTipo().getId(),
				associado.getTipo().getNome(),associado.getTipo().getValor()),associado.getCarteirinhaBloqueada(),
				associado.getTelefoneResidencial(), associado.getTelefoneComercial(), associado.getTelefoneCelular(),
				associado.getCep(), associado.getLogradouro(), associado.getBairro(), associado.getCidade(),
				associado.getEstado());
	}


}

