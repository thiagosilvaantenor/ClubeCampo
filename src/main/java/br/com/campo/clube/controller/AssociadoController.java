package br.com.campo.clube.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.campo.clube.model.Associado;
import br.com.campo.clube.service.AssociadoService;

@RestController
@RequestMapping("/associado")
public class AssociadoController {
	
	@Autowired
	private AssociadoService service;

	@PostMapping
	public ResponseEntity<Associado> criarAssociado(@RequestBody AssociadoDTO associado) {
		Associado salvo = null;
		if (associado != null) {
			//Cria o Associado com os dados do DTO 
			Associado novo = new Associado(associado);
			salvo = service.salvar(novo);
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
	public ResponseEntity<List<AssociadoDTO>> exibirAssociados(){
		//Busca os associados no banco de dados
		List<Associado> associados = service.buscarTodos();
		
		List<AssociadoDTO> dtos = new ArrayList<>();
			
		//Itera sobre a lista de associados, para cada item cria um DTO com os dados desse associado
		associados.forEach( associado -> {
			dtos.add(new AssociadoDTO(associado.getNomeCompleto(), 
					associado.getRg(), associado.getCpf(), associado.getTipo(),
					associado.getCarteirinhaBloqueada(),
					associado.getTelefoneResidencial(),
					associado.getTelefoneComercial(),
					associado.getTelefoneCelular(),
					associado.getCep(),
					associado.getLogradouro(),
					associado.getBairro(),
					associado.getCidade(),
					associado.getEstado()
					)
				);
		});
		//Retorna 200 com os DTOS no body
		return ResponseEntity.ok(dtos);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<AssociadoDTO> exibirAssociado(@PathVariable Long id){
		//Busca o associado no banco de dados é retornado um Optional
		Optional<Associado> associado = service.buscarPeloId(id);
		
		 //Verifica se no Optional contém o associado
		if (associado.isPresent()) {
			 //Se sim, pega o associado e com os dados dele cria um DTO pra ser retornado com ok/200
			Associado encontrado = associado.get();
			return ResponseEntity.ok(new AssociadoDTO(
					encontrado.getNomeCompleto(), 
					encontrado.getRg(), 
					encontrado.getCpf(), 
					encontrado.getTipo(),
					encontrado.getCarteirinhaBloqueada(),
					encontrado.getTelefoneResidencial(),
					encontrado.getTelefoneComercial(),
					encontrado.getTelefoneCelular(),
					encontrado.getCep(),
					encontrado.getLogradouro(),
					encontrado.getBairro(),
					encontrado.getCidade(),
					encontrado.getEstado()
					)
				);
		}
		 //Caso o Optional não contenha um associado, retorna not found/404
		return ResponseEntity.notFound().build();
	}
}
