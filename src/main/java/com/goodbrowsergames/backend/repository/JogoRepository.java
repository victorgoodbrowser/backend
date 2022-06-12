package com.goodbrowsergames.backend.repository;

import com.goodbrowsergames.backend.entity.Jogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JogoRepository extends JpaRepository<Jogo, Integer> {
    List<Jogo> findByNome(String nome);
    List<Jogo> findAllByCategoriaCodigo(String categoriaCodigo);

}
