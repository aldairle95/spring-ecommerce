package com.curso_ecommerce.controller;

import com.curso_ecommerce.model.Producto;
import com.curso_ecommerce.service.ProductoService;
import jakarta.persistence.GeneratedValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("")
    public String home(Model model) {
        List<Producto> productos= productoService.findAll();
        model.addAttribute("productos",productos);
        return "administrador/home";
    }
}
