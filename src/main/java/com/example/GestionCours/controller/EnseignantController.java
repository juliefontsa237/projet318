package com.example.GestionCours.controller;

import com.example.GestionCours.model.Cours;
import com.example.GestionCours.model.UE;
import com.example.GestionCours.model.Utilisateur;
import com.example.GestionCours.service.CoursService;
import com.example.GestionCours.service.UEService;
import com.example.GestionCours.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class EnseignantController {

    @Autowired
    private UEService ueService;

    @Autowired
    private CoursService coursService;

    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        Optional<Utilisateur> optionalUtilisateur = utilisateurService.findByNom(currentUserName);

        if (optionalUtilisateur.isPresent()) {
            Utilisateur utilisateur = optionalUtilisateur.get();
            List<UE> ueList = ueService.findAllByUtilisateur(utilisateur);
            List<Cours> coursList = coursService.findByUtilisateur(Optional.of(utilisateur));

            model.addAttribute("ues", ueList);
            model.addAttribute("cours", coursList);
            model.addAttribute("utilisateur", utilisateur);

            return "admin/dashboard";
        } else {
            return "redirect:/error";
        }
    }

    @GetMapping("/profile")
    public String showProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        Optional<Utilisateur> optionalUtilisateur = utilisateurService.findByNom(currentUserName);

        if (optionalUtilisateur.isPresent()) {
            Utilisateur utilisateur = optionalUtilisateur.get();
            List<UE> ueList = ueService.findAllByUtilisateur(utilisateur);

            model.addAttribute("enseignant", utilisateur);
            model.addAttribute("ues", ueList);
            return "admin/profile";
        } else {
            return "redirect:/error";
        }
    }

    @GetMapping("/edit/{ueId}")
    public String showEditForm(@PathVariable("ueId") Long ueId, Model model) {
        Optional<UE> optionalUE = Optional.ofNullable(ueService.findById(ueId));
        if (optionalUE.isPresent()) {
            UE ue = optionalUE.get();
            model.addAttribute("ue", ue);
            model.addAttribute("coursList", ue.getCours());
            return "admin/edit_ue_cours";
        } else {
            return "redirect:/error";
        }
    }

    @PostMapping("/update")
    public String updateUEAndCourses(@ModelAttribute("ue") UE ue, @ModelAttribute("coursList") List<Cours> coursList) {
        ueService.save(ue);
        for (Cours cours : coursList) {
            coursService.save(cours);
        }
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/delete/{ueId}")
    public String deleteUEAndCourses(@PathVariable("ueId") Long ueId) {
        ueService.delete(ueId);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/cours/publishCourse/{coursId}")
    public String publishCourse(@PathVariable("coursId") Long coursId) {
        Optional<Cours> optionalCours = Optional.ofNullable(coursService.findById(coursId));
        if (optionalCours.isPresent()) {
            Cours cours = optionalCours.get();
            cours.setPublie(true);
            cours.setDatePublication(LocalDateTime.now());
            coursService.save(cours);
            return "redirect:/admin/dashboard";
        } else {
            return "redirect:/error";
        }
    }

    @GetMapping("/view_published_courses")
    public String viewPublishedCourses(Model model) {
        List<Cours> publishedCourses = coursService.findAllPublished();
        model.addAttribute("cours", publishedCourses);
        return "admin/view_published_courses";
    }
}
