package br.com.campo.clube.service;

import br.com.campo.clube.dto.*;
import br.com.campo.clube.exceptions.AssociadoException;
import br.com.campo.clube.exceptions.ParametroInvalidoException;
import br.com.campo.clube.model.Associado;
import br.com.campo.clube.model.CobrancaMensal;
import br.com.campo.clube.repository.CobrancaMensalRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class CobrancaMensalService {
    @Autowired
    private CobrancaMensalRepository repository;

    @Autowired
    private AssociadoService associadoService;


    @Transactional
    public CobrancaMensal salvar(@Valid CobrancaMensalDadosCadastro novo) {
        if (novo.associadoId() == null) {
            throw new AssociadoException("AssociadoId não pode estar nullo");
        }
        CobrancaMensal cobranca = new CobrancaMensal(novo);
        //Busca o tipo pelo id, validação feita no service
        Associado encontrado = associadoService.buscarPeloId(novo.associadoId());
        cobranca.setAssociado(encontrado);
        //Coloca o valor padrão de acordo com o tipoAssociado
        cobranca.setValorPadrao(encontrado.getTipo().getValor());
        //A multa vai ser verificada quando o pagamento for realizado, por enquanto o valor final é o base
        cobranca.setValorFinal(cobranca.getValorPadrao());
        cobranca.setPago(false);
        return repository.save(cobranca);
    }

    @Transactional(readOnly = true)
    public List<CobrancaMensal> buscarTodos(){
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public CobrancaMensal buscarPeloId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ParametroInvalidoException("Nenhuma Cobrança encontrada com este id: " + id));
    }

    @Transactional(readOnly = true)
    public List<CobrancaMensal> buscarPeloAssociado(Long id) {
        return repository.findByAssociadoId(id);
    }


    @Transactional
    public void excluir(CobrancaMensal encontrado) {
        repository.delete(encontrado);
    }

    @Transactional
    public CobrancaMensalDadosExibicao atualizar(CobrancaMensal cobranca, @Valid CobrancaMensalDadosAtualizacao dados) {

        //Associado
        try{
            if (dados.associadoId() != null ){
                Associado associado = associadoService.buscarPeloId(dados.associadoId());
                cobranca.setAssociado(associado);
            }

            //dtVencimento
            if (dados.dtVencimento() != null) {
                cobranca.setDtVencimento(dados.dtVencimento());
            }
            //valorPadrao
            if (dados.valorPadrao() != null){
                cobranca.setValorPadrao(dados.valorPadrao());
            }
            //valorFinal
            if (dados.valorFinal() != null ) {
                cobranca.setValorFinal(dados.valorFinal());
            }
            //mesAno
            if (dados.mesAno() != null){
                cobranca.setMesAno(dados.mesAno());
            }
            //Pago
            if (dados.pago() != null) {
                cobranca.setPago(dados.pago());
            }
            repository.save(cobranca);
        }catch (ParametroInvalidoException parametroInvalidoException){
            throw new ParametroInvalidoException("Não foi possivel salvar a cobrança " + parametroInvalidoException.getMessage());
        }
        return new CobrancaMensalDadosExibicao(
                cobranca.getId(),
                new AssociadoSimplesDTO(cobranca.getAssociado().getId(), cobranca.getAssociado().getNomeCompleto()),
                cobranca.getDtVencimento(),
                cobranca.getValorPadrao(),
                cobranca.getValorFinal(),
                cobranca.getMesAno(),
                cobranca.getPago());
    }

}
