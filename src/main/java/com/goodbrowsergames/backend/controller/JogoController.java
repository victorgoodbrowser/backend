package com.goodbrowsergames.backend.controller;

import com.goodbrowsergames.backend.entity.Categoria;
import com.goodbrowsergames.backend.entity.Jogo;
import com.goodbrowsergames.backend.entity.Usuario;
import com.goodbrowsergames.backend.service.JogoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/goodbrowsergames/jogo")
@CrossOrigin("*")
public class JogoController {
    @Autowired
    private JogoService jogoService;

    @PostMapping("/cadastro")
    public Jogo cadastroJogo(@RequestBody Jogo jogo){
        return jogoService.create(jogo);
    }


    @GetMapping("/listar")
    public List<Jogo> listaJogo(){
        return jogoService.readAll();
    }

    @PostMapping("/salvarImagem/{id}")
    public Jogo uploadFile(@RequestParam("myFile") MultipartFile file,
                              @PathVariable Integer id) throws IOException {
        return jogoService.save(file, id);
    }

    @PostMapping("/buscarJogo")
    public List<Jogo> buscarJogo(@RequestBody Jogo jogo){
        return jogoService.findByNome(jogo.getNome());
    }

    @PutMapping("/editarJogo")
    public Jogo editarJogo(@RequestBody Jogo jogo) {
         return jogoService.update(jogo);
    }

    @GetMapping("/buscarPorId/{id}")
    public Jogo buscarPorId(@PathVariable Integer id) {
        return jogoService.readById(id);
    }

    @DeleteMapping("/deletarJogo/{id}")
    public boolean deletarJogo(@PathVariable Integer id){
        return jogoService.delete(id);
    }

    //Ignorar esse
    @GetMapping("/buscarPorPioresNotas")
    public List<Jogo> buscarPorPioresNotas() { return jogoService.readAllPioresNotas(); }

    //5 jogos que receberam maior numero de avaliações - ok -
    @GetMapping("/buscarQtdAvaliacao")
    public List<Jogo> buscarQtdAvaliacao() { return jogoService.readAllQtdDeAvaliacao(); }

    //5 membros que realizaram o maior numero de avaliacoes - ok - ok
    @GetMapping("/buscarMembrosMaiorNumeroAvaliacao")
    public List<Usuario> buscarMembrosMaiorNumeroAvaliacao() { return jogoService.readAllMembrosMaiorAvaliacao(); }

    //5 jogos com a melhor media com no minimo 4 avaliações - ok - ok
    @GetMapping("/buscarPorMelhoresNotas")
    public List<Jogo> buscarPorMelhoresNotas() { return jogoService.readAllMelhoresNotas(); }

    //3 categorias que receberam maior numero de avaliacoes - ok - ok
    @GetMapping("/buscarPorMelhoresCategorias")
    public List<Categoria> buscarPorMelhoresCategorias() { return jogoService.readAllCategoriasMelhoresAvaliadas(); }

    @PostMapping("/obterAutorAvaliacaoJogo")
    public Usuario obterAutorAvaliacaoJogo(@RequestBody Jogo jogo) { return jogoService.getUsuarioJogo(jogo); }

    @GetMapping("/listaRecomendados/{idUsuario}")
    public  List<Jogo> listaRecomendados(@PathVariable Integer idUsuario) {
        return jogoService.listaDeRecomendados(idUsuario);
    }
}
