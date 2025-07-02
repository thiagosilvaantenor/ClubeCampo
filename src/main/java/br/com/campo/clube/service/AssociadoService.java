package br.com.campo.clube.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import br.com.campo.clube.dto.AssociadoDadosAtualizacao;
import br.com.campo.clube.dto.AssociadoDadosCadastro;
import br.com.campo.clube.dto.AssociadoDadosExibicao;
import br.com.campo.clube.dto.TipoAssociadoDadosExibicao;
import br.com.campo.clube.model.CobrancaMensal;
import br.com.campo.clube.model.TipoAssociado;
import br.com.campo.clube.repository.CobrancaMensalRepository;
import br.com.campo.clube.repository.TipoAssociadoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.campo.clube.model.Associado;
import br.com.campo.clube.repository.AssociadoRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AssociadoService {
	@Autowired
	private AssociadoRepository repository;

	@Autowired
	private TipoAssociadoRepository tipoRepository;

	@Autowired
	private CobrancaMensalRepository cobrancaRepository;

	public Associado salvar(AssociadoDadosCadastro novo) {
		Associado associado = new Associado(novo);
		//Busca o tipo pelo id
		if(novo.tipoId() != null){
			TipoAssociado tipo = tipoRepository.getReferenceById(novo.tipoId());
			associado.setTipo(tipo);
			tipo.getAssociados().add(associado);
		}
		associado.setCarteirinhaBloqueada(false);
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

	@Transactional
	public AssociadoDadosExibicao atualizar(Associado associado, @Valid AssociadoDadosAtualizacao dados) {

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
				//Remove o associado da lista que esta em tipo (antigo)
				associado.getTipo().getAssociados().remove(associado);
				//Coloca novo tipo
				associado.setTipo(tipo);
				//Add associado ao novo tipo
				tipo.getAssociados().add(associado);
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

		repository.save(associado);

		return new AssociadoDadosExibicao(
				associado.getId(),
				associado.getNomeCompleto(),
				associado.getRg(),
				associado.getCpf(),
				//Cria o DTO de exibicao de tipoAssociado
				new TipoAssociadoDadosExibicao(
						associado.getTipo().getId(),
						associado.getTipo().getNome(),
						associado.getTipo().getValor()
				),
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

	//É necessário a propagation para criar uma transação deste método, já que ele vai ser chamado em outras transações
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	//Verifica a quantidade de meses de inadimplência, o resultado vai ser tratado nas seguintes services: Reserva, Area e ParticipanteTurmaAssociado
	public Integer verificaQntInadimplencia(Associado associado){
		//Busca as cobranças com paga == false e dtVencimento que ja passou, comparando com o dia de hoje, e retorna a quantidade de ocorrencias
		List<CobrancaMensal> cobrancas = cobrancaRepository.findByAssociadoAndPago(associado, false);
		if(cobrancas.isEmpty()){
			//se a lista esta vazia significa que todas cobranças desse associado estão pagas
			return 0;
		}
		int quantidade = 0;
		for(CobrancaMensal cobranca : cobrancas) {
			//Verifica se a data de vencimento já passou
			if (cobranca.getDtVencimento().isBefore(LocalDate.now())) {
				//se sim aumenta a quantidade de inadimplências
				quantidade++;
			}
		}
		//Se for maior ou igual a 5 a carteirinha do associado é bloqueada
		if(quantidade >= 5){
			associado.setCarteirinhaBloqueada(true);
			System.out.println(associado.getCarteirinhaBloqueada());
			repository.save(associado);
		}
		//retorna a quantidade de inadimplências
		System.out.println(associado.getCarteirinhaBloqueada());
		return quantidade;

	}
}
