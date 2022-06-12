package com.goodbrowsergames.backend.repository;

import com.goodbrowsergames.backend.entity.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvaliacaoRepository  extends JpaRepository<Avaliacao, Integer> {
}
