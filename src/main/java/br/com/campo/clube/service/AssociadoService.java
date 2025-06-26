package br.com.campo.clube.service;

import java.util.List;
import java.util.Optional;

import br.com.campo.clube.dto.AssociadoDTO;
import br.com.campo.clube.dto.AssociadoDadosAtualizacao;
import br.com.campo.clube.dto.AssociadoDadosCadastro;
import br.com.campo.clube.model.TipoAssociado;
import br.com.campo.clube.repository.TipoAssociadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.campo.clube.model.Associado;
import br.com.campo.clube.repository.AssociadoRepository;

@Service
public class AssociadoService {
	@Autowired
	private AssociadoRepository repository;

	private TipoAssociadoRepository tipoRepository;

	public Associado salvar(AssociadoDadosCadastro novo) {
		Associado associado = new Associado(novo);
		//Busca o tipo pelo id
		TipoAssociado tipo = tipoRepository.getReferenceById(novo.tipoId());
        associado.setTipo(tipo);
        return repository.save(associado);
	}
	
	public List<Associado> buscarTodos(){
		return repository.findAll();
	}

	public Optional<Associado> buscarPeloId(Long id) {
		
		return repository.findById(id);
	}

    public void excluir(Associado encontrado) {
		repository.delete(encontrado);
    }

	public AssociadoDTO atualizar(Associado associado, AssociadoDadosAtualizacao dados) {

		if (dados.nomeCompleto() != null && !dados.nomeCompleto().isBlank()){
			associado.setNomeCompleto(dados.nomeCompleto());
		}
		if (dados.cpf() != null && !dados.cpf().isBlank()) {
			associado.setCpf(dados.cpf());
		}
		if (dados.rg() != null && !dados.rg().isBlank()){
			associado.setRg(dados.rg());
		}
		if (dados.tipoId() != null ) {
			TipoAssociado tipo = tipoRepository.getReferenceById(dados.tipoId());
			if (tipo != null){
				associado.setTipo(tipo);
			}
		}
		if (dados.cep() != null && !dados.cep().isBlank()){
			associado.setCep(dados.cep());
		}
		if (dados.bairro() != null && !dados.bairro().isBlank()) {
			associado.setBairro(dados.bairro());
		}
		if (dados.cidade() != null && !dados.cidade().isBlank()){
			associado.setCidade(dados.cidade());
		}
		if (dados.logradouro() != null && !dados.logradouro().isBlank()) {
			associado.setLogradouro(dados.logradouro());
		}
		if (dados.estado() != null && !dados.estado().isBlank()) {
			associado.setEstado(dados.estado());
		}
		if (dados.carteirinhaBloqueada() != null){
			associado.setCarteirinhaBloqueada(dados.carteirinhaBloqueada());
		}
		if (dados.telefoneCelular() != null){
			associado.setTelefoneCelular(dados.telefoneCelular());
		}
		if (dados.telefoneComercial() != null){
			associado.setTelefoneComercial(dados.telefoneComercial());
		}
		if (dados.telefoneResidencial() != null){
			associado.setTelefoneResidencial(dados.telefoneResidencial());
		}

		return new AssociadoDTO(associado.getNomeCompleto(),
				associado.getRg(),
				associado.getCpf(),
				associado.getTipo(),
				associado.getCarteirinhaBloqueada(),
				associado.getTelefoneResidencial(),
				associado.getTelefoneComercial(),
				associado.getTelefoneCelular(),
				associado.getCep(),
				associado.getLogradouro(),
				associado.getBairro(),
				associado.getCidade(),
				associado.getEstado());
	}
}
