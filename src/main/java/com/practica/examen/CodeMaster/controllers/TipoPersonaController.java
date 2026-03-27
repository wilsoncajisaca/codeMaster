package com.practica.examen.CodeMaster.controllers;

import com.practica.examen.CodeMaster.entities.TipoPersonaEntity;
import com.practica.examen.CodeMaster.services.ITipoPersonaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class TipoPersonaController {
    private final ITipoPersonaService service;

    @GetMapping("/listar-tipo-persona")
    public String listar(Model model) {
        model.addAttribute("datos", this.service.listar());
        return "pages/listar-tipo-persona";
    }

    @GetMapping("nuevo-tipo-persona")
    public String nuevo(Model model) {
        model.addAttribute("nuevo", new TipoPersonaEntity());
        return "/pages/nuevo-tipo-persona";
    }

    @PostMapping("/guardar-tipo-persona")
    public String guardar(@ModelAttribute("nuevo") TipoPersonaEntity entity) {
        service.guardar(entity);
        return "redirect:/listar-tipo-persona";
    }
}