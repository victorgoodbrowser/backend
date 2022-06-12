package com.goodbrowsergames.backend.controller;

import com.goodbrowsergames.backend.entity.Categoria;
import com.goodbrowsergames.backend.entity.Jogo;
import com.goodbrowsergames.backend.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goodbrowsergames/categoria")
@CrossOrigin("*")
public class CategoriaController {
    @Autowired
    private CategoriaService categoriaService;

    @PostMapping("/cadastro")
    public Categoria cadastroCategoria(@RequestBody Categoria categoria){
        return categoriaService.create(categoria);
    }

    @GetMapping("/listar")
    public List<Categoria> listaCategoria(){
        return categoriaService.readAll();
    }

    @GetMapping("/buscarPorId/{id}")
    public Categoria buscarPorId(@PathVariable Integer id) {
        return categoriaService.readById(id);
    }

    @PutMapping("/editarCategoria")
    public Categoria editarCategoria(@RequestBody Categoria categoria) {
        return categoriaService.update(categoria);
    }

    @PostMapping("/buscarNomeCategoria")
    public Categoria buscarJogo(@RequestBody Categoria categoria){
        return categoriaService.findByNome(categoria.getNome());
    }
}
