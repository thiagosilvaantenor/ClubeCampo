package br.com.campo.clube.service;

import br.com.campo.clube.dto.ReservaDadosCadastro;
import br.com.campo.clube.dto.ReservaDadosExibicao;
import br.com.campo.clube.exceptions.AssociadoException;
import br.com.campo.clube.exceptions.ParametroInvalidoException;
import br.com.campo.clube.exceptions.ReservaInvalidaException;
import br.com.campo.clube.model.Area;
import br.com.campo.clube.model.Associado;
import br.com.campo.clube.model.Reserva;
import br.com.campo.clube.repository.ReservaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ReservaService {
    @Autowired
    private ReservaRepository repository;

    @Autowired
    private AreaService areaService;

    @Autowired
    private AssociadoService associadoService;

    @Transactional
    public Reserva salvar(ReservaDadosCadastro dados){
        Area area = areaService.buscarAreaPeloId(dados.areaId());
        Associado associado = associadoService.buscarPeloId(dados.associadoId());

        //Verifica se associado tem inadimplências que podem impedir a reserva
        lidaComInadimplencia(associado, area);
        //Se não tem inadimplências, cria o objeto e salva no banco de dados
        Reserva reserva = new Reserva(dados);
        reserva.setAssociado(associado);
        reserva.setArea(area);
        //verifica se a reserva pode gerar conflito
        verificarReservaNoMesmoDiaHoraArea(reserva);
        repository.save(reserva);
        return reserva;
    }


    public Reserva buscarReservaPeloId(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ParametroInvalidoException("Nenhuma reserva encontrada com este id: " + id));
    }

    public List<Reserva> buscarTodos(){
        return repository.findAll();
    }

    @Transactional
    public ReservaDadosExibicao atualizar(Reserva reserva, @Valid ReservaDadosCadastro dados) {

        if (dados.areaId() != null){
            Area area = areaService.buscarAreaPeloId(dados.areaId());
            reserva.setArea(area);
            //verifica inadimplência
            lidaComInadimplencia(reserva.getAssociado(), area);
            //verifica conflito de reservas
            verificarReservaNoMesmoDiaHoraArea(reserva);
        }
        if (dados.associadoId() != null) {
            Associado associado = associadoService.buscarPeloId(dados.associadoId());
            reserva.setAssociado(associado);
            lidaComInadimplencia(associado, reserva.getArea());
        }
        if (dados.dtReservaInicio() != null){
            reserva.setDtReservaInicio(dados.dtReservaInicio());
        }
        if (dados.dtReservaFim() != null) {
            reserva.setDtReservaFim(dados.dtReservaFim());
        }
        if (dados.statusReserva() != null) {
            reserva.setStatusReserva(dados.statusReserva());
        }
        repository.save(reserva);

        return new ReservaDadosExibicao(
                reserva.getId(), reserva.getArea(), reserva.getAssociado(),
                reserva.getDtReservaInicio(), reserva.getDtReservaFim(), reserva.getStatusReserva());
    }

    @Transactional
    public void excluir(Reserva encontrado) {
        repository.delete(encontrado);
    }

    //Método para verificar se Associado e Area podem ser usados na reserva
    private void lidaComInadimplencia(Associado associado, Area area) {

        //Verifica inadimplência, usa essa verificação do Boolean para evitar nullPointer
        if (Boolean.TRUE.equals(associado.getCarteirinhaBloqueada())) {
            throw new AssociadoException
                    ("Associado está com carteirinha bloqueada, para desbloquear pague as mensalidades atrasadas");
        }
        //Chama o método de associado para verificar a quantidade de inadimplências
        int qnt = associadoService.verificaQntInadimplencia(associado);
        if (qnt >= 2) {
            // dois meses, o associado perde o direito de realizar reservas para o haras, campo de golfe e piscina
            if (area.getNomeArea().equalsIgnoreCase("haras")
                    || area.getNomeArea().equalsIgnoreCase("campo de golfe")
                    || area.getNomeArea().equalsIgnoreCase("piscina")) {
                throw new AssociadoException("Associado tem inadimplência de " + qnt +
                        " meses e por isso não pode reservar está área");
            }
            //Quando a inadimplência ultrapassa os três meses, o associado terá acesso apenas as quadras
            if (qnt == 3) {
                if (area.getNomeArea().contains("quadra")) {
                    throw new AssociadoException
                            ("Associado tem inadimplência de " + qnt +
                                    " meses e por isso não pode reservar está área");
                }
            }
            if (qnt > 3) {
                //A carteirinha está bloquada
                throw new AssociadoException("Associado tem inadimplência de " + qnt +
                        " meses e por isso está com a carterinha bloqueada");
            }
        }
    }

    public void verificarReservaNoMesmoDiaHoraArea(Reserva reserva){
        //Busca todas as conflitos dessa área
        Area area = reserva.getArea();
        List<Reserva> conflitos = repository.findConflito(area, reserva.getDtReservaInicio(), reserva.getDtReservaFim(),
                reserva.getId());
        //Se lista não está vazia, tem conflitos
        if (!conflitos.isEmpty()){
            throw new ReservaInvalidaException("Horário da reserva entra em conflito com outra reserva na mesma área");
        }
        //Caso a quantidade da área seja menor que a quantidade de reservas em conflitos,
        // então mesmo com mais de uma área não é possivel fazer a reserva
        if (area.getQuantidade() <= conflitos.size()){
            throw new ReservaInvalidaException("Horário da reserva entra em conflito com outra reserva na mesma área");
        }

    }
}
