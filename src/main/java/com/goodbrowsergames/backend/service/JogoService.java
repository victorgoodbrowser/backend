package com.goodbrowsergames.backend.service;

import com.goodbrowsergames.backend.entity.Avaliacao;
import com.goodbrowsergames.backend.entity.Categoria;
import com.goodbrowsergames.backend.entity.Jogo;
import com.goodbrowsergames.backend.entity.Usuario;
import com.goodbrowsergames.backend.repository.AvaliacaoRepository;
import com.goodbrowsergames.backend.repository.CategoriaRepository;
import com.goodbrowsergames.backend.repository.JogoRepository;
import com.goodbrowsergames.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class JogoService {
    @Autowired
    private JogoRepository jogoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    public Jogo create(Jogo jogo) {
        return jogoRepository.save(jogo);
    }

    public List<Jogo> readAll() {
        return jogoRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public List<Jogo> readAllMelhoresNotas() {
        List<Jogo> jogoList = jogoRepository.findAll(Sort.by(Sort.Direction.DESC, "nota"));
        List<Jogo> auxList = new ArrayList<>();
        for (Jogo jogo: jogoList) {
            if (jogo.getQtdDeAvaliacao() > 4) {
                auxList.add(jogo);
            }
        }
        return auxList;
    }

    public List<Jogo> readAllPioresNotas() {
        return jogoRepository.findAll(Sort.by(Sort.Direction.ASC, "nota"));
    }

    public List<Jogo> readAllQtdDeAvaliacao() {
        return jogoRepository.findAll(Sort.by(Sort.Direction.DESC, "qtdDeAvaliacao"));
    }

    public List<Usuario> readAllMembrosMaiorAvaliacao() {
        return usuarioRepository.findAll(Sort.by(Sort.Direction.DESC, "qtdJogosAvaliados"));
    }

    public List<Categoria> readAllCategoriasMelhoresAvaliadas() {
        return categoriaRepository.findAll(Sort.by(Sort.Direction.DESC, "qtdCategoriaAvaliada"));
    }

    public Jogo save(MultipartFile file, Integer id) throws IOException {
        Jogo jogo = jogoRepository.findById(id).get();
        jogo.setImagem(file.getBytes());

        return jogoRepository.save(jogo);
    }

    public Usuario getUsuarioJogo(Jogo jogo) {
        Integer idUsuario = Integer.parseInt(jogo.getUsuarioCodigo());
        return usuarioRepository.findById(idUsuario).get();
    }

    public List<Jogo> findByNome(String nome) {
        List<Jogo> aux = new ArrayList<Jogo>();
        List<Jogo> allJogos = jogoRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        for(Jogo jogo: allJogos){
            if (jogo.getNome().toLowerCase().contains(nome.toLowerCase())){
                aux.add(jogo);
            }
        }
        return aux;
    }

    public Jogo update(Jogo jogo) {
        if (jogoRepository.existsById(jogo.getId())) {
            Jogo j = jogoRepository.findById(jogo.getId()).get();

            j.setNome(jogo.getNome());
            j.setDescricao(jogo.getDescricao());
            j.setUrlJogo(jogo.getUrlJogo());
            j.setNota(jogo.getNota());
            j.setCategoriaCodigo(jogo.getCategoriaCodigo());
            j.setUsuarioCodigo(jogo.getUsuarioCodigo());

            j.setImagem(jogo.getImagem());

            return jogoRepository.save(j);
        }
        return null;
    }

    public Jogo readById(Integer id) {
        return jogoRepository.findById(id).get();
    }

    public boolean delete(Integer id) {
        if (jogoRepository.existsById(id)) {
            Jogo jogo = jogoRepository.findById(id).get();
            jogoRepository.delete(jogo);

            categoriaRepository.delete(categoriaRepository
                    .findById(Integer.parseInt(jogo.getCategoriaCodigo())).get());

            Usuario usuario =  usuarioRepository
                    .findById(Integer.parseInt(jogo.getUsuarioCodigo()))
                    .get();
            List<Integer> listaJogos = usuario.getJogos();
            for (int i = 0; i < listaJogos.size(); i++) {
                if (listaJogos.get(i) == id) {
                    listaJogos.remove(i);
                }
            }
            usuario.setJogos(listaJogos);
            usuarioRepository.save(usuario);
            return true;
        }
        return false;
    }

    public List<Jogo> listaDeRecomendados(Integer id) {
        List<Jogo> listaJogosAvaliados = new ArrayList<Jogo>();
        for (Integer idAvaliados : usuarioRepository.findById(id).get().getJogosAvaliados()) {
            listaJogosAvaliados.add(jogoRepository.findById(idAvaliados).get());
        }

        List<Integer> categoriaDosJogosAvaliados = new ArrayList<Integer>();
        for (Jogo jogosAvaliados : listaJogosAvaliados) {
            categoriaDosJogosAvaliados.add(Integer.parseInt(jogosAvaliados.getCategoriaCodigo()));
        }

        // orderna
        Collections.sort(categoriaDosJogosAvaliados);

        // busca a categoria mais avaliada pelo usuario
        Integer categoriaMaisAvaliada = categoriaMaisRepetida(categoriaDosJogosAvaliados);

        // busca todas as categorias com o codigo de categoria mais avaliado
        List<Jogo> allByCategoriaCodigoJogos = findAllByCategoriaCodigo(categoriaMaisAvaliada.toString());

        return verificaSeJaAvaliou(listaJogosAvaliados, allByCategoriaCodigoJogos);
    }

    public List<Jogo> verificaSeJaAvaliou(List<Jogo> listaJogosAvaliadosUsuario, List<Jogo> allByCategoriaCodigoJogos) {
        List<Jogo> differences = new ArrayList<>(allByCategoriaCodigoJogos);
        differences.removeAll(listaJogosAvaliadosUsuario);

        return differences;
    }

    public List<Jogo> findAllByCategoriaCodigo(String idCategoria) {
        List<Jogo> allJogos = jogoRepository.findAll();
        List<Jogo> aux = new ArrayList<Jogo>();

        for (Jogo jogo : allJogos) {
            if (Objects.equals(jogo.getCategoriaCodigo(), idCategoria)) {
                aux.add(jogo);
            }
        }

        return aux;
    }

    public Integer categoriaMaisRepetida(List<Integer> arr) {
        int element = Integer.MIN_VALUE, max_count=0, count=0;
        for(int i=1; i<arr.size(); i++){
            if(arr.get(i) == arr.get(i))
                count++;

            if(arr.get(i) != arr.get(i) || i==arr.size()-1){
                if(count>max_count){
                    max_count = count;
                    element = arr.get(i-1);
                }
                count=0;
            }
        }
        return element;
    }

}
