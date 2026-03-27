package com.practica.examen.CodeMaster.controllers;

import com.practica.examen.CodeMaster.entities.PersonaEntity;
import com.practica.examen.CodeMaster.entities.TipoPersonaEntity;
import com.practica.examen.CodeMaster.services.IPersonaService;
import com.practica.examen.CodeMaster.services.impl.TipoPersonaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class PersonaController {
    private final IPersonaService service;
    private final TipoPersonaService tPService;

    @GetMapping("/listar-persona")
    public String listar(Model model) {
        model.addAttribute("datos", service.listar());
        return "pages/listar-persona";
    }

    @GetMapping("nuevo-persona")
    public String nuevo(Model model) {
        model.addAttribute("nuevo", new TipoPersonaEntity());
        model.addAttribute("tipos-persona", tPService.listar());
        return "/pages/nuevo-persona";
    }

    @PostMapping("/guardar-persona")
    public String guardar(@ModelAttribute("nuevo") PersonaEntity entity) {
        service.guardar(entity);
        return "redirect:/listar-persona";
    }

    @GetMapping("eliminar-persona/{id}")
    public String eliminar(@PathVariable("id") Long id) throws Exception {
        service.eliminarEstudiante(id);
        return "redirect:/listar-persona";
    }
}