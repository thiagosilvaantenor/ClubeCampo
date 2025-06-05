package br.com.campo.clube.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.campo.clube.model.Associado;
import br.com.campo.clube.repository.AssociadoRepository;

@Service
public class AssociadoService {
	@Autowired
	private AssociadoRepository repository;
	
	
	public Associado salvar(Associado novo) {
		return repository.save(novo);
	}
	
	public List<Associado> buscarTodos(){
		return repository.findAll();
	}

	public Optional<Associado> buscarPeloId(Long id) {
		
		return repository.findById(id);
	}

}
