package com.example.GestionCours.controller;

import com.example.GestionCours.model.Utilisateur;
import com.example.GestionCours.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        return "users/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("utilisateur") @Valid Utilisateur utilisateur,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            return "users/register";
        }

        try {
            utilisateurService.saveUtilisateur(utilisateur);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "users/register";
        }

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "users/login";
    }

    @GetMapping("/default")
    public String defaultPage(@RequestParam(name = "loginSuccess", required = false) String loginSuccess, Model model, Authentication authentication) {
        if ("true".equals(loginSuccess)) {
            model.addAttribute("loginSuccessMessage", "Connexion réussie !");
        }

        // Vérifiez les rôles et redirigez en fonction du rôle
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            String role = authority.getAuthority();
            if (role.equals("ROLE_APPRENANT")) {
                return "redirect:/admin/cours/view_cours"; // Remplacez par le chemin de la page pour les apprenants
            } else if (role.equals("ROLE_ENSEIGNANT")) {
                return "redirect:/admin/dashboard"; // Remplacez par le chemin du dashboard enseignant
            }
        }
        return "defaultPage"; // Remplacez par le nom de votre vue par défaut
    }
}
