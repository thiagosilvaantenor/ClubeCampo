package br.com.campo.clube.service;

import br.com.campo.clube.dto.*;
import br.com.campo.clube.model.Associado;
import br.com.campo.clube.model.CobrancaMensal;
import br.com.campo.clube.model.TipoAssociado;
import br.com.campo.clube.repository.CobrancaMensalRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CobrancaMensalService {
    @Autowired
    private CobrancaMensalRepository repository;

    @Autowired
    private AssociadoService associadoService;

    //TODO: VERIFICAR COMO FAZER O SISTEMA GERAR AUTOMATICAMENTE A COBRANÇÃ
    public CobrancaMensal salvar(CobrancaMensalDadosCadastro novo) {
        CobrancaMensal cobranca = new CobrancaMensal(novo);
        //Busca o tipo pelo id
        if(novo.associadoId() != null){
            Optional<Associado> associado = associadoService.buscarPeloId(novo.associadoId());
            if (associado.isPresent()){
                Associado encontrado = associado.get();
                cobranca.setAssociado(encontrado);
                //Coloca o valor padrão de acordo com o tipoAssociado
                cobranca.setValorPadrao(encontrado.getTipo().getValor());
                //Chama função para calcular valor final, verificando se existe multa
                calcularValorFinal(cobranca);
                //associado.getCobrancas.add(cobranca);
                return repository.save(cobranca);
            }
        }
        return null;

    }

    public List<CobrancaMensal> buscarTodos(){
        return repository.findAll();
    }

    public Optional<CobrancaMensal> buscarPeloId(Long id) {
        return repository.findById(id);
    }

    public void excluir(CobrancaMensal encontrado) {
        repository.delete(encontrado);
    }

    public CobrancaMensalDadosExibicao atualizar(CobrancaMensal cobranca, @Valid CobrancaMensalDadosAtualizacao dados) {

        //Associado
        if (dados.associadoId() != null ){
            Optional<Associado> associado = associadoService.buscarPeloId(dados.associadoId());
            if (associado.isPresent()){
                cobranca.setAssociado(associado.get());
            }
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
        //EmAtraso
        if (dados.emAtraso() != null) {
            cobranca.setMesAno(dados.mesAno());
        }
        return new CobrancaMensalDadosExibicao(
                cobranca.getId(),
                new AssociadoSimplesDTO(cobranca.getAssociado().getId(), cobranca.getAssociado().getNomeCompleto()),
                cobranca.getDtVencimento(),
                cobranca.getValorPadrao(),
                cobranca.getValorFinal(),
                cobranca.getMesAno(),
                cobranca.getEmAtraso());
    }

    //TODO: VERIFICAR LÓGICA PARA MARCAR CONTA COMO ATRASADA
    // CALCULAR MULTA
    private void calcularValorFinal(CobrancaMensal cobranca ) {

       //Verifica se a data de hoje é anterior ou igual ao dia de hoje
       if (LocalDate.now().isBefore(cobranca.getDtVencimento()) || LocalDate.now().isEqual(cobranca.getDtVencimento()) ) {
           cobranca.setEmAtraso(false);
           //Se sim, a cobrança não esta em atraso, o valor final atual será o valor padrão

           cobranca.setValorFinal(cobranca.getValorPadrao());
       }
       //Se não, então é preciso calcular a multa
       else {
           cobranca.setEmAtraso(false);
           //Gera a multa de 5%
           BigDecimal multa = cobranca.getValorPadrao().multiply(BigDecimal.valueOf(0.05));
           //Soma o valor padrão com a multa
           cobranca.setValorFinal(cobranca.getValorPadrao().add(multa));
       }
    }
}
