package com.goodbrowsergames.backend.service;

import com.goodbrowsergames.backend.entity.Categoria;
import com.goodbrowsergames.backend.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    public Categoria create(Categoria categoria) { return categoriaRepository.save(categoria); }

    public List<Categoria> readAll() { return categoriaRepository.findAll(Sort.by(Sort.Direction.ASC, "id")); }

    public Categoria readById(Integer id) {
        return categoriaRepository.findById(id).get();
    }

    public Categoria update(Categoria categoria) {
        if (categoriaRepository.existsById(categoria.getId()) && findByNome(categoria.getNome()) == null) {
            Categoria c = categoriaRepository.findById(categoria.getId()).get();
            c.setNome(categoria.getNome());
            c.setQtdCategoriaAvaliada(
                    categoria.getQtdCategoriaAvaliada() == null ? 0 : categoria.getQtdCategoriaAvaliada());

            return categoriaRepository.save(c);
        }
        return null;
    }

    public Categoria findByNome(String nome) {
        List<Categoria> categorias = categoriaRepository.findAll();
        for (Categoria categoria : categorias) {
            if (categoria.getNome().toLowerCase().equals(nome.toLowerCase())) return categoria;
        }

        return null;
    }

}
