package com.goodbrowsergames.backend.service;

import com.goodbrowsergames.backend.entity.Util;
import com.goodbrowsergames.backend.repository.UtilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilService {
    @Autowired
    private UtilRepository utilRepository;

    public Util marcar(Util util) {
        return utilRepository.save(util);
    }

    public boolean desmarcar(Integer idJogo, Integer idUsuario) {
        List<Util> lista = utilRepository.findAll();
        for (Util util : lista) {
            if (util.getJogoCodigo().equals(idJogo)
                    && util.getUsuarioCodigo().equals(idUsuario)) {
                utilRepository.deleteById(util.getId());
                return true;
            }
        }
        return false;
    }

    public List<Util> readAll() { return utilRepository.findAll(); }

    public boolean verificaMarcacao (Integer idJogo, Integer idUsuario) {
        List<Util> lista = utilRepository.findAll();
        for (Util util : lista) {
            if (util.getJogoCodigo().equals(idJogo)
                && util.getUsuarioCodigo().equals(idUsuario)) {
                return true;
            }
        }
        return false;
    }

}
