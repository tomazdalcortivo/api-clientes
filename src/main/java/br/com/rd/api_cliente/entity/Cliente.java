package br.com.rd.api_cliente.entity;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cliente_seq_gen")
    @SequenceGenerator(name = "cliente_seq_gen", sequenceName = "cliente_seq", allocationSize = 1)
    private Long id;

    private String nome;

    @Column(unique = true)
    private String cpf;

    @Column(unique = true, nullable = false)
    private String email;

    private String endereco;

    private String telefone;

}
