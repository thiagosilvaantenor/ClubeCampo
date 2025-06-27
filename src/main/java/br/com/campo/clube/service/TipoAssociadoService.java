package br.com.campo.clube.service;

import br.com.campo.clube.dto.AreaDadosCadastro;
import br.com.campo.clube.dto.AreaDadosExibicao;
import br.com.campo.clube.dto.TipoAssociadoDTO;
import br.com.campo.clube.dto.TipoAssociadoDadosExibicao;
import br.com.campo.clube.model.Area;
import br.com.campo.clube.model.TipoAssociado;
import br.com.campo.clube.repository.TipoAssociadoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TipoAssociadoService {
    @Autowired
    private TipoAssociadoRepository repository;

    @Transactional
    public TipoAssociado salvar(TipoAssociadoDTO dados){
        TipoAssociado tipo = new TipoAssociado(dados);
        //Verifica se o nome, que é unico, ja existe no banco de dados
        if (!repository.findByNome(dados.nome()).isEmpty()){
            return null;
        }
        tipo.setAssociados(new ArrayList<>());
        return repository.save(tipo);
    }

    public Optional<TipoAssociado> buscarTipoAssociadoPorId(Long id){
        return repository.findById(id);
    }

    public List<TipoAssociado> buscaTodos(){
        return repository.findAll();
    }
    @Transactional
    public void excluir(TipoAssociado encontrado) {
        repository.delete(encontrado);
    }
    @Transactional
    public TipoAssociadoDadosExibicao atualizar(TipoAssociado tipo, @Valid TipoAssociadoDTO dados) {
        if (dados.nome() != null && !dados.nome().isBlank()){
            tipo.setNome(dados.nome());
        }
        if (dados.valor() != null){
            tipo.setValor(dados.valor());
        }

        repository.save(tipo);
        return new TipoAssociadoDadosExibicao(
                tipo.getId(), tipo.getNome(), tipo.getValor()
        );
    }
}
