package com.goodbrowsergames.backend.repository;

import com.goodbrowsergames.backend.entity.Util;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilRepository extends JpaRepository<Util, Integer> {
}
