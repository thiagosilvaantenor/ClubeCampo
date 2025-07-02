package br.com.campo.clube.service;

import br.com.campo.clube.dto.*;
import br.com.campo.clube.model.Associado;
import br.com.campo.clube.model.CobrancaMensal;
import br.com.campo.clube.model.PagamentoRealizado;
import br.com.campo.clube.repository.PagamentoRealizadoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class PagamentoRealizadoService {
    @Autowired
    private PagamentoRealizadoRepository repository;

    @Autowired
    private CobrancaMensalService cobrancaService;


    @Transactional
    public PagamentoRealizado salvar(PagamentoRealizadoDadosCadastro novo) {
        PagamentoRealizado pagamento = new PagamentoRealizado(novo);
        //Busca a cobrança do pagamento pelo id
        if(novo.cobrancaId() != null){
            Optional<CobrancaMensal> cobranca = cobrancaService.buscarPeloId(novo.cobrancaId());
            if (cobranca.isPresent()){
                CobrancaMensal encontrado = cobranca.get();
                //Coloca a cobrança
                pagamento.setCobrancaMensal(encontrado);
                //Com o pagamento realizado é possivel verificar se houve multa devido a atraso
                calcularValorFinal(pagamento);
                pagamento.getCobrancaMensal().setPago(true);
                //Verifica se o associado tem carteirinha bloqueada
                //Se sim, desbloqueia ela, já se tiver inadimplência, feito este pagamento será no maximo 3 meses
                desbloqueioCarteirinha(encontrado.getAssociado());
                return repository.save(pagamento);
            }
        }
        //Sem cobrança não se pode salvar pagamentoRealizado
        return null;

    }

    public List<PagamentoRealizado> buscarTodos(){
        return repository.findAll();
    }

    public Optional<PagamentoRealizado> buscarPeloId(Long id) {
        return repository.findById(id);
    }

    public void excluir(PagamentoRealizado encontrado) {
        //Sem pagamento no sistema, então deve ser marcado como não pago
        encontrado.getCobrancaMensal().setPago(false);
        repository.delete(encontrado);
    }

    @Transactional
    public PagamentoRealizadoDadosExibicao atualizar(PagamentoRealizado pagamento, @Valid PagamentoRealizadoDadosCadastro dados) {

        //Cobrança
        if (dados.cobrancaId() != null ){
            Optional<CobrancaMensal> cobranca = cobrancaService.buscarPeloId(dados.cobrancaId());
            if (cobranca.isPresent()){
                pagamento.setCobrancaMensal(cobranca.get());
            }else{
                //Se não achar a cobrança não pode continuar
                return null;
            }
        }

        //dtPagamento
        if (dados.dtPagamento() != null) {
            pagamento.setDtPagamento(dados.dtPagamento());
            calcularValorFinal(pagamento);
        }
        //formaPagamento
        if (dados.formaPagamento() != null){
            pagamento.setFormaPagamento(dados.formaPagamento());
        }

        repository.save(pagamento);
        return new PagamentoRealizadoDadosExibicao(
                pagamento.getId(),
                pagamento.getDtPagamento(),
                pagamento.getFormaPagamento(),
                new CobrancaMensalDadosExibicao(
                        pagamento.getCobrancaMensal().getId(),
                        new AssociadoSimplesDTO(pagamento.getCobrancaMensal().getAssociado().getId(),
                                pagamento.getCobrancaMensal().getAssociado().getNomeCompleto()),
                        pagamento.getCobrancaMensal().getDtVencimento(), pagamento.getCobrancaMensal().getValorPadrao(),
                        pagamento.getCobrancaMensal().getValorFinal(), pagamento.getCobrancaMensal().getMesAno(),
                        pagamento.getCobrancaMensal().getPago())
        );
    }

    // CALCULAR MULTA
    private void calcularValorFinal(PagamentoRealizado pagamento ) {
        CobrancaMensal cobranca = pagamento.getCobrancaMensal();
       //Verifica se a data do pagamento é anterior ou igual ao dia de vencimento
       if (pagamento.getDtPagamento().isBefore(cobranca.getDtVencimento()) ||pagamento.getDtPagamento().isEqual(cobranca.getDtVencimento()) ) {
           //Se sim, a cobrança não esta em atraso, o valor final atual será o valor padrão
           pagamento.getCobrancaMensal().setValorFinal(pagamento.getCobrancaMensal().getValorPadrao());
       }
       //Se não, então é preciso calcular a multa
       else {
           //Gera a multa de 5%
           BigDecimal multa = cobranca.getValorPadrao().multiply(BigDecimal.valueOf(0.05));
           //Soma o valor padrão com a multa
           cobranca.setValorFinal(cobranca.getValorPadrao().add(multa));
       }
    }

    //Desbloqueio de carterinha
    private void desbloqueioCarteirinha(Associado associado){
        //Usado equals para evitar NulPointer
        if (Boolean.TRUE.equals(associado.getCarteirinhaBloqueada())){
            associado.setCarteirinhaBloqueada(false);
        }
    }
}
