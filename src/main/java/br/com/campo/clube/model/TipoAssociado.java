package br.com.campo.clube.model;

import br.com.campo.clube.dto.TipoAssociadoDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "tipo_associado")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class TipoAssociado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_associado")
    private Long id;
    @Column(name = "nome", unique = true, nullable = true)
    private String nome;
    @Column(name = "valor", precision = 10, scale = 2, nullable = false)
    private BigDecimal valor;
    @OneToMany(mappedBy = "tipo")
    @JsonIgnore
    private List<Associado> associados;

    public TipoAssociado(TipoAssociadoDTO dados) {
        this.nome = dados.nome();
        this.valor = dados.valor();
    }

}
