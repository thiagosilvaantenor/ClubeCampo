package br.com.campo.clube.service;


import br.com.campo.clube.dto.TipoAssociadoDTO;
import br.com.campo.clube.dto.TipoAssociadoDadosExibicao;
import br.com.campo.clube.exceptions.AssociadoException;
import br.com.campo.clube.exceptions.ParametroInvalidoException;
import br.com.campo.clube.model.TipoAssociado;
import br.com.campo.clube.repository.TipoAssociadoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TipoAssociadoService {
    @Autowired
    private TipoAssociadoRepository repository;

    @Transactional
    public TipoAssociado salvar(TipoAssociadoDTO dados){
        TipoAssociado tipo = null;
        try{
            tipo = new TipoAssociado(dados);
            //Verifica se o nome, que é unico, ja existe no banco de dados
            if (!repository.findByNome(dados.nome()).isEmpty()){
                throw new ParametroInvalidoException("Já existe um tipo associado com este nome");
            }
            tipo.setAssociados(new ArrayList<>());
            repository.save(tipo);
        }catch (AssociadoException e){
            throw new AssociadoException("Não foi possivel criar o tipoAssociado");
        }
        return tipo;

    }
    @Transactional(readOnly = true)
    public TipoAssociado buscarTipoAssociadoPorId(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ParametroInvalidoException("Nenhum tipoAssociado encontrado com este id: " + id));
    }
    @Transactional(readOnly = true)
    public List<TipoAssociado> buscaTodos(){
        return repository.findAll();
    }
    @Transactional
    public void excluir(TipoAssociado encontrado) {
        repository.delete(encontrado);
    }
    @Transactional
    public TipoAssociadoDadosExibicao atualizar(TipoAssociado tipo, @Valid TipoAssociadoDTO dados) {
        try {

            if (dados.nome() != null && !dados.nome().isBlank()){
                tipo.setNome(dados.nome());
            }
            if (dados.valor() != null){
                tipo.setValor(dados.valor());
            }

            repository.save(tipo);
        }catch (AssociadoException e){
            throw new AssociadoException("Não foi possivel atualizar o tipoAssociado de id: " + tipo.getId());
        }
        return new TipoAssociadoDadosExibicao(
                tipo.getId(), tipo.getNome(), tipo.getValor()
        );
    }
}
