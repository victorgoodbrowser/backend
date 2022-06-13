package com.goodbrowsergames.backend.controller;

import com.goodbrowsergames.backend.entity.Avaliacao;
import com.goodbrowsergames.backend.entity.Jogo;
import com.goodbrowsergames.backend.model.AvaliacaoJogoComentario;
import com.goodbrowsergames.backend.service.AvaliacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goodbrowsergames/avaliacao")
@CrossOrigin("*")
public class AvaliacaoController {
    @Autowired
    private AvaliacaoService avaliacaoService;

    @GetMapping("/listar")
    public List<Avaliacao> listarAvaliacao() { return avaliacaoService.readAll(); }

    @PostMapping("/qtdDeAvaliacao/{idUsuario}/{nota}")
    public Jogo jogo(@RequestBody AvaliacaoJogoComentario avaliacaoJogoComentario,
                     @PathVariable Integer idUsuario,
                     @PathVariable Integer nota) {
        return avaliacaoService.qtdDeAvaliacao(avaliacaoJogoComentario , idUsuario, nota);
    }

    @GetMapping("/listarPorIdJogo/{idJogo}")
    public List<Avaliacao> lsitarAvaliacaoPorIdJogo(@PathVariable Integer idJogo) {
        return avaliacaoService.visualizarOutrasAvaliacoes(idJogo);
    }

    @GetMapping("/buscarAvaliacaoPorJogo/{idUsuario}/{idJogo}")
    public Avaliacao buscarNotaPorJogo(@PathVariable Integer idUsuario, @PathVariable Integer idJogo) {
        return avaliacaoService.buscarNotaPorJogo(idUsuario, idJogo);
    }
}
