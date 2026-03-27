package com.practica.examen.CodeMaster.controllers;

import com.practica.examen.CodeMaster.entities.GrupoEntity;
import com.practica.examen.CodeMaster.services.ICursoService;
import com.practica.examen.CodeMaster.services.IGrupoService;
import com.practica.examen.CodeMaster.services.IPersonaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class GrupoController {
    private final IGrupoService service;
    private final IPersonaService pService;
    private final ICursoService cService;

    @GetMapping("/listar-grupo")
    public String listar(Model model) {
        model.addAttribute("datos", service.listar());
        return "pages/listar-grupo";
    }

    @GetMapping("nuevo-grupo")
    public String nuevo(Model model) {
        model.addAttribute("nuevo", new GrupoEntity());
        model.addAttribute("personas", pService.listarDocentes());
        model.addAttribute("cursos", cService.listar());
        return "/pages/nuevo-grupo";
    }

    @PostMapping("/guardar-grupo")
    public String guardar(@ModelAttribute("nuevo") GrupoEntity entity) {
        service.guardar(entity);
        return "redirect:/listar-grupo";
    }
}
