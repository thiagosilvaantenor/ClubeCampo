package br.com.campo.clube.service;

import br.com.campo.clube.dto.AreaDadosCadastro;
import br.com.campo.clube.dto.AreaDadosExibicao;
import br.com.campo.clube.exceptions.ParametroInvalidoException;
import br.com.campo.clube.model.Area;

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
        try {
         repository.save(area);
        }catch (ParametroInvalidoException e){
            throw new ParametroInvalidoException("Não foi possivel salvar a Area. " + e.getMessage());
        }
        return area;
    }

    @Transactional(readOnly = true)
    public Area buscarAreaPeloId(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ParametroInvalidoException("Nenhuma área encontrada com o id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Area> buscarTodos(){
        return repository.findAll();
    }
    @Transactional
    public void excluir(Area encontrado) {
        repository.delete(encontrado);
    }

    @Transactional
    public AreaDadosExibicao atualizar(Area area, @Valid AreaDadosCadastro dados) {

        if (dados.nomeArea() != null && !dados.nomeArea().isBlank()){
            area.setNomeArea(dados.nomeArea());
        }
        if (dados.quantidade() != null){
            area.setQuantidade(dados.quantidade());
        }
        if (dados.reservavel() != null) {
            area.setReservavel(dados.reservavel());
        }
        repository.save(area);

        return new AreaDadosExibicao(
                area.getId(), area.getNomeArea(),
                area.getReservavel(), area.getQuantidade());
    }


}
