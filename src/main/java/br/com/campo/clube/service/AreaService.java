package br.com.campo.clube.service;

import br.com.campo.clube.dto.*;
import br.com.campo.clube.model.Area;
import br.com.campo.clube.model.Associado;
import br.com.campo.clube.model.TipoAssociado;
import br.com.campo.clube.repository.AreaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AreaService {
    @Autowired
    private AreaRepository repository;

    @Transactional
    public Area salvar( AreaDadosCadastro dados){
        Area area = new Area(dados);
        return repository.save(area);
    }

    public Optional<Area> buscarAreaPeloId(Long id){
        return repository.findById(id);
    }

    public List<Area> buscarTodos(){
        return repository.findAll();
    }

    public void excluir(Area encontrado) {
        repository.delete(encontrado);
    }

    public AreaDadosExibicao atualizar(Area area, @Valid AreaDadosCadastro dados) {

        if (dados.nomeArea() != null && !dados.nomeArea().isBlank()){
            area.setNomeArea(dados.nomeArea());
        }
        if (dados.tipoArea() != null) {
            area.setTipoArea(dados.tipoArea());
        }
        if (dados.quantidade() != null){
            area.setQuantidade(dados.quantidade());
        }
        if (dados.valorArea() != null ){
            area.setValorArea(dados.valorArea());
        }
        if (dados.reservavel() != null) {
            area.setReservavel(dados.reservavel());
        }
        repository.save(area);

        return new AreaDadosExibicao(
                area.getId(), area.getNomeArea(), area.getTipoArea(),
                area.getValorArea(),area.getReservavel(), area.getQuantidade());
    }
}
