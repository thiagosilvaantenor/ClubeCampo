package br.com.campo.clube.service;

import br.com.campo.clube.dto.TipoAssociadoDTO;
import br.com.campo.clube.model.TipoAssociado;
import br.com.campo.clube.repository.TipoAssociadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TipoAssociadoService {
    @Autowired
    private TipoAssociadoRepository repository;

    public TipoAssociado salvar(TipoAssociadoDTO dados){
        TipoAssociado tipo = new TipoAssociado(dados);
        //Verifica se o nome, que é unico, ja existe no banco de dados
        if (!repository.findByNome(dados.nome()).isEmpty()){
            return null;
        }
        tipo.setAssociados(new ArrayList<>());
        return repository.save(tipo);
    }

    public TipoAssociado buscaTipoAssociado(Long id){
        return repository.getReferenceById(id);
    }

    public List<TipoAssociado> buscaTodos(){
        return repository.findAll();
    }

}
