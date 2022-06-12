package com.goodbrowsergames.backend.controller;

import com.goodbrowsergames.backend.entity.Util;
import com.goodbrowsergames.backend.service.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goodbrowsergames/util")
@CrossOrigin("*")
public class UtilController {
    @Autowired
    private UtilService utilService;

    @PostMapping("/marcar")
    public Util marcar(@RequestBody Util util) {
        return utilService.marcar(util);
    }

    @DeleteMapping("/desmarcar/{idJogo}/{idUsuario}")
    public boolean desmarcar(@PathVariable Integer idJogo,
                             @PathVariable Integer idUsuario)  {
        return utilService.desmarcar(idJogo, idUsuario);
    }

    @GetMapping("/listar")
    public List<Util> listar(){
        return utilService.readAll();
    }

    @GetMapping("/verificaMarcacao/{idJogo}/{idUsuario}")
    public boolean verificaMarcacao(@PathVariable Integer idJogo,
                                    @PathVariable Integer idUsuario) {
        return utilService.verificaMarcacao(idJogo, idUsuario);
    }

}
