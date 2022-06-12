package com.goodbrowsergames.backend.model;

import com.goodbrowsergames.backend.entity.Jogo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AvaliacaoJogoComentario {
    private Jogo jogo;
    private String comentario;
}
