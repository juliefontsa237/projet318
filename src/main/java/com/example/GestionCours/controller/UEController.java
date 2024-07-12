package com.example.GestionCours.controller;

import com.example.GestionCours.model.UE;
import com.example.GestionCours.model.Utilisateur;
import com.example.GestionCours.service.UEService;
import com.example.GestionCours.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/admin/ues")
public class UEController {

    @Autowired
    private UEService ueService;

    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping
    public String listUEs(Model model) {
        model.addAttribute("ues", ueService.findAll());
        return "/admin/ue/list_ues";
    }

    @GetMapping("/create")
    public String showCreateUEForm(Model model) {
        model.addAttribute("ue", new UE());
        return "/admin/ue/create_ue";
    }

    @PostMapping
    public String createUE(@ModelAttribute("ue") UE ue) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        Optional<Utilisateur> optionalUtilisateur = utilisateurService.findByNom(currentUserName);

        if (optionalUtilisateur.isPresent()) {
            Utilisateur utilisateur = optionalUtilisateur.get();
            ue.setUtilisateur(utilisateur);
            ueService.save(ue);
            return "redirect:/admin/dashboard";
        } else {
            return "redirect:/error";
        }
    }
}
