package com.practica.examen.CodeMaster.controllers;

import com.practica.examen.CodeMaster.entities.GrupoEntity;
import com.practica.examen.CodeMaster.entities.MatriculaEntity;
import com.practica.examen.CodeMaster.services.ICursoService;
import com.practica.examen.CodeMaster.services.IGrupoService;
import com.practica.examen.CodeMaster.services.IMatriculaService;
import com.practica.examen.CodeMaster.services.IPersonaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MatriculaController {
    private final IMatriculaService service;
    private final IPersonaService pService;
    private final IGrupoService gService;

    @GetMapping("/listar-matricula")
    public String listar(Model model) {
        model.addAttribute("datos", service.listar());
        return "pages/listar-matricula";
    }

    @GetMapping("nueva-matricula")
    public String nuevo(Model model) {
        model.addAttribute("nuevo", new MatriculaEntity());
        model.addAttribute("personas", pService.listarEstudiantes());
        model.addAttribute("cursos", gService.listar());
        return "/pages/nueva-matricula";
    }

    @PostMapping("/guardar-matricula")
    public String guardar(@ModelAttribute("nuevo") MatriculaEntity entity) {
        service.guardar(entity);
        return "redirect:/listar-matricula";
    }
}
