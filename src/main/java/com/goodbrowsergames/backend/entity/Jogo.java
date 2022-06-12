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
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Integer id;

    private String nome;
    private String descricao;
    private String urlJogo;
    private Integer nota;
    private String categoriaCodigo;
    private String usuarioCodigo;
    private Integer qtdDeAvaliacao;
    private String comentario;

    @Lob
    private byte[] imagem;
}
