package br.com.campo.clube.service;

import br.com.campo.clube.dto.ReservaDadosCadastro;
import br.com.campo.clube.dto.ReservaDadosExibicao;
import br.com.campo.clube.model.Area;
import br.com.campo.clube.model.Associado;
import br.com.campo.clube.model.Reserva;
import br.com.campo.clube.repository.ReservaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

        Optional<Area> area = areaService.buscarAreaPeloId(dados.areaId());
        Optional<Associado> associado = associadoService.buscarPeloId(dados.associadoId());
        if (area.isPresent() && associado.isPresent()){
            Reserva reserva = new Reserva(dados);
            reserva.setArea(area.get());
            reserva.setAssociado(associado.get());
            return repository.save(reserva);
        }
        return null;

    }

    public Optional<Reserva> buscarReservaPeloId(Long id){
        return repository.findById(id);
    }

    public List<Reserva> buscarTodos(){
        return repository.findAll();
    }

    @Transactional
    public ReservaDadosExibicao atualizar(Reserva reserva, @Valid ReservaDadosCadastro dados) {

        if (dados.areaId() != null){
            Optional<Area> area = areaService.buscarAreaPeloId(dados.areaId());
            if (area.isPresent()){
                reserva.setArea(area.get());
            }
        }
        if (dados.associadoId() != null) {
            Optional<Associado> associado = associadoService.buscarPeloId(dados.associadoId());
            if (associado.isPresent()){
                reserva.setAssociado(associado.get());
            }
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
}
