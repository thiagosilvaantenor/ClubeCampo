package br.com.campo.clube.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tipo_associado")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
public class TipoAssociado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_associado")
    private Long id;
    @Column(name = "nome", unique = true, nullable = false)
    private String nome;
    @Column(name = "valor", precision = 10, scale = 2, nullable = false)
    private BigDecimal valor;

}
