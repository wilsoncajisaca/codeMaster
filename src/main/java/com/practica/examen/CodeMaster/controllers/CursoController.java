package com.practica.examen.CodeMaster.controllers;

import com.practica.examen.CodeMaster.entities.CursoEntity;
import com.practica.examen.CodeMaster.services.ICursoService;
import com.practica.examen.CodeMaster.services.IPersonaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class CursoController {
    private final ICursoService service;

    @GetMapping("/listar-cursos")
    public String listar(Model model) {
        model.addAttribute("datos", service.listar());
        return "pages/listar-cursos";
    }

    @GetMapping("nuevo-curso")
    public String nuevo(Model model) {
        model.addAttribute("nuevo", new CursoEntity());
        return "/pages/nuevo-curso";
    }

    @PostMapping("/guardar-curso")
    public String guardar(@ModelAttribute("nuevo") CursoEntity entity) {
        service.guardar(entity);
        return "redirect:/listar-curso";
    }
}
