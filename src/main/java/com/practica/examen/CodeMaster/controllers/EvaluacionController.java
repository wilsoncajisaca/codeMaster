package com.practica.examen.CodeMaster.controllers;

import com.practica.examen.CodeMaster.entities.EvaluacionEntity;
import com.practica.examen.CodeMaster.services.IEvaluacionService;
import com.practica.examen.CodeMaster.services.IMatriculaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class EvaluacionController {
    private final IEvaluacionService service;
    private final IMatriculaService mService;

    @GetMapping("/listar-evaluacion")
    public String listar(Model model) {
        model.addAttribute("datos", service.listar());
        return "pages/listar-evaluacion";
    }

    @GetMapping("nueva-evaluacion")
    public String nuevo(Model model) {
        model.addAttribute("nuevo", new EvaluacionEntity());
        model.addAttribute("matriculas", mService.listar());
        return "/pages/nueva-evaluacion";
    }

    @PostMapping("/guardar-evaluacion")
    public String guardar(@ModelAttribute("nuevo") EvaluacionEntity entity) {
        service.guardar(entity);
        return "redirect:/listar-evaluacion";
    }
}