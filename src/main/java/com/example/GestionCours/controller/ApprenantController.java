package com.example.GestionCours.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import com.example.GestionCours.model.Commentaire;
import com.example.GestionCours.model.Cours;
import com.example.GestionCours.model.Role;
import com.example.GestionCours.model.Utilisateur;
import com.example.GestionCours.service.CommentaireService;
import com.example.GestionCours.service.CoursService;
import com.example.GestionCours.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class ApprenantController {

    @Autowired
    private CommentaireService commentaireService;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private CoursService coursService;

    @GetMapping("/view_cours")
    public String viewCours(Model model, Principal principal) {
        List<Cours> coursPublies = coursService.findAllPublished();
        model.addAttribute("cours", coursPublies);

        // Récupérer l'utilisateur connecté
        Optional<Utilisateur> utilisateurOptional = utilisateurService.findByNom(principal.getName());

        if (utilisateurOptional.isPresent()) {
            Utilisateur utilisateur = utilisateurOptional.get();
            model.addAttribute("utilisateur", utilisateur);
            model.addAttribute("isApprenant", utilisateur.getRole() == Role.APPRENANT);
        } else {
            // Gérer le cas où l'utilisateur n'est pas trouvé
            // Peut-être rediriger ou afficher une erreur
        }

        return "admin/cours/view_cours";
    }

    @PostMapping("/cours/{coursId}/comment")
    public String ajouterCommentaire(@PathVariable Long coursId, @RequestParam String commentaire, Authentication authentication) {
        Utilisateur utilisateur = utilisateurService.findByNom(authentication.getName()).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        Cours cours = coursService.findById(coursId);
        Commentaire newCommentaire = new Commentaire(commentaire, utilisateur, cours);
        commentaireService.save(newCommentaire);
        return "redirect:/admin/view_cours";
    }

    @PostMapping("/cours/{coursId}/comment/{commentId}/reply")
    public String ajouterReponse(@PathVariable Long coursId, @PathVariable Long commentId,
                                 @RequestParam String reponse, Authentication authentication) {
        Utilisateur utilisateur = utilisateurService.findByNom(authentication.getName()).orElseThrow(); // Récupère directement l'utilisateur ou lance une exception si absent
        Commentaire parentCommentaire = commentaireService.findById(commentId);
        Commentaire newReponse = new Commentaire(reponse, utilisateur, parentCommentaire.getCours());
        newReponse.setParentCommentaire(parentCommentaire);
        commentaireService.save(newReponse);
        return "redirect:/admin/view_cours";
    }


}
