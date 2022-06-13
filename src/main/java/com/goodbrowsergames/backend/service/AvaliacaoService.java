package com.goodbrowsergames.backend.service;

import com.goodbrowsergames.backend.entity.Avaliacao;
import com.goodbrowsergames.backend.entity.Categoria;
import com.goodbrowsergames.backend.entity.Jogo;
import com.goodbrowsergames.backend.model.AvaliacaoJogoComentario;
import com.goodbrowsergames.backend.repository.AvaliacaoRepository;
import com.goodbrowsergames.backend.repository.CategoriaRepository;
import com.goodbrowsergames.backend.repository.JogoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AvaliacaoService {
    @Autowired
    private AvaliacaoRepository avaliacaoRepository;
    @Autowired
    private JogoRepository jogoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Avaliacao> readAll() {
        return avaliacaoRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public Jogo qtdDeAvaliacao(AvaliacaoJogoComentario avaliacaoJogoComentario, Integer idUsuario, Integer nota) {
        List<Avaliacao> lista = avaliacaoRepository.findAll();
        Jogo jogo = jogoRepository.findById(avaliacaoJogoComentario.getJogoId()).get();
        Avaliacao avaliacao = contemAvaliacao(lista, jogo.getId(), idUsuario);
        Categoria categoria = categoriaRepository.findById(Integer.parseInt(jogo.getCategoriaCodigo())).get();
        if (avaliacao != null){
            categoria.setQtdCategoriaAvaliada(
                            categoria.getQtdCategoriaAvaliada() == null ? 1 : categoria.getQtdCategoriaAvaliada()+1);
            categoriaRepository.save(categoria);

            avaliacao.setNota(nota);
            avaliacao.setComentario(avaliacaoJogoComentario.getComentario());
            avaliacaoRepository.save(avaliacao);

            jogo.setNota(notaNova(jogo.getId()));
            jogoRepository.save(jogo);
            return jogoRepository.save(jogo);
        } else {
            categoria.setQtdCategoriaAvaliada(
                    categoria.getQtdCategoriaAvaliada() == null ? 1 : categoria.getQtdCategoriaAvaliada()+1);
            categoriaRepository.save(categoria);

            Avaliacao _avaliacao = new Avaliacao();
            _avaliacao.setJogoCodigo(jogo.getId());
            _avaliacao.setUsuarioCodigo(idUsuario);
            _avaliacao.setNota(nota);
            _avaliacao.setComentario(avaliacaoJogoComentario.getComentario());
            avaliacaoRepository.save(_avaliacao);

            jogo.setQtdDeAvaliacao(jogo.getQtdDeAvaliacao()+1);
            jogo.setNota(notaNova(jogo.getId()));
            jogoRepository.save(jogo);
            return jogoRepository.save(jogo);
        }
    }

    public Integer notaNova(Integer idJogoCodigo) {
        Integer nota = 0;
        Integer qtdAvaliacao = 0;
        List<Avaliacao> lista = avaliacaoRepository.findAll();
        for (Avaliacao avaliacao : lista) {
            if (avaliacao.getJogoCodigo() == idJogoCodigo) {
                nota += avaliacao.getNota();
                qtdAvaliacao++;
            }
        }

        return nota/qtdAvaliacao;
    }

    public Avaliacao contemAvaliacao(List<Avaliacao> lista, Integer idJogo, Integer idUsuario) {
        for (Avaliacao avaliacao: lista) {
            if (avaliacao.getUsuarioCodigo().equals(idUsuario)
                    && avaliacao.getJogoCodigo().equals(idJogo)) return avaliacao;
        }
        return null;
    }

    public List<Avaliacao> visualizarOutrasAvaliacoes(Integer idJogo) {
        List<Avaliacao> lista = new ArrayList<>();
        List<Avaliacao> aux = avaliacaoRepository.findAll();
        for (Avaliacao avaliacao : aux) {
            if (avaliacao.getJogoCodigo().equals(idJogo)) {
                lista.add(avaliacao);
            }
        }
        return lista;
    }

    public Avaliacao buscarNotaPorJogo(Integer idUsuario, Integer idJogo) {
        List<Avaliacao> listaAvaliacao = avaliacaoRepository.findAll();
        Avaliacao avaliacao = listaAvaliacao
                .stream()
                .filter(element ->  idJogo.toString().equals(element.getJogoCodigo().toString()) &&
                        idUsuario.toString().equals(element.getUsuarioCodigo().toString()))
                .findAny()
                .orElse(null);
        return avaliacao;
    }
}
