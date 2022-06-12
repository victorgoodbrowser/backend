package com.goodbrowsergames.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Usuario {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Integer id;

    private String nome;
    private String userName;
    private String senha;
    private String dataNascimento;
    private String estado;
    private String pais;
    private String ehAdmin;
    private String email;

    @ElementCollection
    @CollectionTable(name="jogoUsuario", joinColumns=@JoinColumn(name="jogoUsuario_id"))
    @Column(name="id_jogoUsuario")
    private List<Integer> jogos;

    @ElementCollection
    @CollectionTable(name="jogoAvaliadoUsuario", joinColumns=@JoinColumn(name="jogoAvaliadoUsuario_id"))
    @Column(name="id_jogoAvaliadoUsuario")
    private List<Integer> jogosAvaliados;

    private Integer qtdJogosAvaliados;

}
