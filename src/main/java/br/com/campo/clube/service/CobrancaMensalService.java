package br.com.campo.clube.service;

import br.com.campo.clube.dto.*;
import br.com.campo.clube.model.Associado;
import br.com.campo.clube.model.CobrancaMensal;
import br.com.campo.clube.repository.CobrancaMensalRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CobrancaMensalService {
    @Autowired
    private CobrancaMensalRepository repository;

    @Autowired
    private AssociadoService associadoService;

    //TODO: VERIFICAR COMO FAZER O SISTEMA GERAR AUTOMATICAMENTE A COBRANÇa
    @Transactional
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
                //A multa vai ser verificada quando o pagamento for realizado, por enquanto o valor final é o base
                cobranca.setValorFinal(cobranca.getValorPadrao());
                cobranca.setPago(false);
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

    @Transactional
    public CobrancaMensalDadosExibicao atualizar(CobrancaMensal cobranca, @Valid CobrancaMensalDadosAtualizacao dados) {

        //Associado
        if (dados.associadoId() != null ){
            Optional<Associado> associado = associadoService.buscarPeloId(dados.associadoId());
            if (associado.isPresent()){
                cobranca.setAssociado(associado.get());
            }
        }//TODO: Lançar exceção caso associado não esteja presente ou encontrado

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
        return new CobrancaMensalDadosExibicao(
                cobranca.getId(),
                new AssociadoSimplesDTO(cobranca.getAssociado().getId(), cobranca.getAssociado().getNomeCompleto()),
                cobranca.getDtVencimento(),
                cobranca.getValorPadrao(),
                cobranca.getValorFinal(),
                cobranca.getMesAno(),
                cobranca.getPago());
    }

    //Verifica a quantidade de meses de inadimplência
    public Integer verificaQntInadimplencia(Associado associado){
        //Busca as cobranças com paga == false e dtVencimento que ja passou, comparando com o dia de hoje, e retorna a quantidade de ocorrencias
        List<CobrancaMensal> cobrancas = repository.findByAssociadoAndPago(associado, false);
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
       //retorna a quantidade de inadimplências
       return quantidade;

    }


}
