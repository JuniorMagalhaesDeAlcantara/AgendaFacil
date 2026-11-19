package br.com.agenda_facil.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Quando acessar http://localhost:8080/
    // redireciona para a listagem de contatos.
    @GetMapping("/")
    public String inicio() {

        return "redirect:/contatos";
    }
}