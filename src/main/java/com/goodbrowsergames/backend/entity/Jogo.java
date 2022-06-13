package com.goodbrowsergames.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Jogo {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "uuid", unique = true)
    private Integer id;

    private String nome;
    private String descricao;
    private String urlJogo;
    private Integer nota;
    private String categoriaCodigo;
    private String usuarioCodigo;
    private Integer qtdDeAvaliacao;

    @Lob
    private byte[] imagem;
}
