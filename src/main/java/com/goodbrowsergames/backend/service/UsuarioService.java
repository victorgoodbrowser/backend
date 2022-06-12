package com.goodbrowsergames.backend.service;

import com.goodbrowsergames.backend.entity.Categoria;
import com.goodbrowsergames.backend.entity.Usuario;
import com.goodbrowsergames.backend.repository.CategoriaRepository;
import com.goodbrowsergames.backend.repository.JogoRepository;
import com.goodbrowsergames.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private JogoRepository jogoRepository;

    public Usuario login(Usuario usuario) {
        Usuario _usuario = usuarioRepository.findByEmail(usuario.getEmail());
        return _usuario.getSenha().contains(usuario.getSenha()) ? _usuario : null;
    }

    public Usuario create(Usuario usuario) {
        Usuario _auxUsuario = usuarioRepository.findByEmail(usuario.getEmail());
        return _auxUsuario == null ? usuarioRepository.save(usuario) : null;
    }

    public List<Usuario> readAll() {
        return usuarioRepository.findAll();
    }

    public List<Usuario> readAllExceptEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        List<Usuario> lista = usuarioRepository.findAll();
        for(Usuario u: lista){
            if (u == usuario){
                lista.remove(u);
                return lista;
            }
        }
        return null;
    }

    public Usuario readById(Integer id) {
        return usuarioRepository.findById(id).get();
    }

    public Usuario readByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Usuario update(Usuario usuario) {
        if (usuarioRepository.existsById(usuario.getId())) {
            Usuario u = usuarioRepository.findById(usuario.getId()).get();
            u.setNome(usuario.getNome());
            u.setUserName(usuario.getUserName());
            u.setSenha(usuario.getSenha());
            u.setDataNascimento(usuario.getDataNascimento());
            u.setEstado(usuario.getEstado());
            u.setPais(usuario.getPais());
            u.setEhAdmin(usuario.getEhAdmin());
            u.setEmail(usuario.getEmail());
            u.setJogos(usuario.getJogos());
            u.setQtdJogosAvaliados(usuario.getQtdJogosAvaliados());
            return usuarioRepository.save(u);
        }
        return null;
    }

    public Usuario updateJogos(Integer idUsuario, Integer idJogo) {
        Usuario usuario = usuarioRepository.findById(idUsuario).get();

        usuario.getJogos().add(idJogo);
        usuarioRepository.save(usuario);
        return usuario;
    }

    public boolean delete(Integer id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.delete(usuarioRepository.findById(id).get());
            return true;
        }
        return false;
    }

    public Usuario setJogoAvaliado(Integer idUsuario, Integer idJogo) {
        Usuario usuario = usuarioRepository.findById(idUsuario).get();
        if (!usuario.getJogosAvaliados().contains(idJogo)) {
            usuario.setQtdJogosAvaliados(usuario.getQtdJogosAvaliados() == null? 1: usuario.getQtdJogosAvaliados()+1);
            usuario.getJogosAvaliados().add(idJogo);
            usuarioRepository.save(usuario);
        }
        return usuario;
    }

}
