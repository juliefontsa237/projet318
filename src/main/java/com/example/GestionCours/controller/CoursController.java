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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/cours")
public class CoursController {

    @Autowired
    private CoursService coursService;

    @Autowired
    private UEService ueService;

    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<UE> ues = ueService.findAll();
        model.addAttribute("ues", ues);
        return "admin/dashboard";
    }

    @GetMapping
    public String listCours(Model model) {
        model.addAttribute("cours", coursService.findAll());
        return "/admin/cours/list_cours";
    }

    @GetMapping("/create")
    public String showCreateCoursForm(Model model) {
        model.addAttribute("cours", new Cours());
        model.addAttribute("ues", ueService.findAll());
        return "/admin/cours/add_course";
    }

    @PostMapping
    public String createCours(@ModelAttribute("cours") Cours cours, @RequestParam("file") MultipartFile file) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        Optional<Utilisateur> optionalUtilisateur = utilisateurService.findByNom(currentUserName);

        if (optionalUtilisateur.isPresent()) {
            Utilisateur utilisateur = optionalUtilisateur.get();
            cours.setUtilisateur(utilisateur);

            if (!file.isEmpty()) {
                try {
                    // Sauvegarder le fichier
                    String fileName = file.getOriginalFilename();
                    Path uploadDir = Paths.get("uploads").toAbsolutePath().normalize();
                    if (!Files.exists(uploadDir)) {
                        Files.createDirectories(uploadDir);
                    }
                    Path filePath = uploadDir.resolve(fileName);
                    Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                    // Enregistrer le chemin du fichier dans le cours (chemin relatif)
                    cours.setSupportCours(fileName);
                } catch (IOException e) {
                    e.printStackTrace();
                    return "redirect:/admin/cours/create?error";
                }
            }
            coursService.save(cours);
            return "redirect:/admin/dashboard";
        } else {
            return "redirect:/error";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditCoursForm(@PathVariable("id") Long id, Model model) {
        Cours cours = coursService.findById(id);
        if (cours == null) {
            return "redirect:/admin/cours";
        }
        model.addAttribute("cours", cours);
        model.addAttribute("ues", ueService.findAll());
        return "/admin/cours/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateCours(@PathVariable("id") Long id, @ModelAttribute("cours") Cours cours,
                              @RequestParam(value = "file", required = false) MultipartFile file) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        Optional<Utilisateur> optionalUtilisateur = utilisateurService.findByNom(currentUserName);

        if (optionalUtilisateur.isPresent()) {
            Utilisateur utilisateur = optionalUtilisateur.get();
            cours.setUtilisateur(utilisateur);

            if (file != null && !file.isEmpty()) {
                try {
                    String fileName = file.getOriginalFilename();
                    Path uploadDir = Paths.get("uploads").toAbsolutePath().normalize();
                    if (!Files.exists(uploadDir)) {
                        Files.createDirectories(uploadDir);
                    }
                    Path filePath = uploadDir.resolve(fileName);
                    Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                    cours.setSupportCours(fileName);
                } catch (IOException e) {
                    e.printStackTrace();
                    return "redirect:/admin/cours/edit/{id}?error";
                }
            }
            cours.setId(id); // Assurez-vous de définir l'ID du cours correctement pour l'update
            coursService.save(cours);
            return "redirect:/admin/dashboard";
        } else {
            return "redirect:/error";
        }
    }

    @GetMapping("/view_cours")
    public String viewPublishedCourses(Model model) {
        List<Cours> publishedCourses = coursService.findAllPublished();
        model.addAttribute("cours", publishedCourses);
        return "admin/view_cours";
    }


}
