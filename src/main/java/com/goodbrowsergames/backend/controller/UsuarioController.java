package com.goodbrowsergames.backend.controller;

import com.goodbrowsergames.backend.entity.Usuario;
import com.goodbrowsergames.backend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goodbrowsergames/usuario")
@CrossOrigin("*")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public Usuario login(@RequestBody Usuario usuario){
        return usuarioService.login(usuario);
    }

    @PostMapping("/cadastro")
    public Usuario cadastroPessoa(@RequestBody Usuario usuario){
        return usuarioService.create(usuario);
    }

    @GetMapping("/listar")
    public List<Usuario> listaPessoa(){
        return usuarioService.readAll();
    }

    @GetMapping("/listarExceto/{email}")
    public List<Usuario> listaExceto(@PathVariable String email){
        return usuarioService.readAllExceptEmail(email);
    }

    @GetMapping("/buscarPorId/{id}")
    public Usuario buscaPessoaPorId(@PathVariable Integer id){
        return usuarioService.readById(id);
    }

    @GetMapping("/emailpessoa/{email}")
    public Usuario buscaPessoaPorEmail(@PathVariable String email) { return usuarioService.readByEmail(email); }

    @PutMapping("/atualizar")
    public Usuario atualizarPessoa(@RequestBody Usuario pessoa){
        return usuarioService.update(pessoa);
    }

    @DeleteMapping("/deletar/{id}")
    public boolean deletarPessoa(@PathVariable Integer id){
        return usuarioService.delete(id);
    }

    @GetMapping("/atualizarJogos/{idUsuario}/{idJogo}")
    public Usuario atualizarJogos(@PathVariable Integer idUsuario, @PathVariable Integer idJogo){
        return usuarioService.updateJogos(idUsuario, idJogo);
    }

    @GetMapping("/inserirJogoAvaliado/{idUsuario}/{idJogo}")
    public Usuario inserirJogoAvaliado(@PathVariable Integer idUsuario, @PathVariable Integer idJogo){
        return usuarioService.setJogoAvaliado(idUsuario, idJogo);
    }
}
