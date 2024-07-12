// Ajoutez un contrôleur pour la gestion des commentaires
package com.example.GestionCours.controller;

import com.example.GestionCours.model.Commentaire;
import com.example.GestionCours.model.Cours;
import com.example.GestionCours.model.Utilisateur;
import com.example.GestionCours.service.CommentaireService;
import com.example.GestionCours.service.CoursService;
import com.example.GestionCours.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cours")
public class CommentaireController {

    @Autowired
    private CoursService coursService;

    @Autowired
    private CommentaireService commentaireService;

    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping("/{coursId}")
    public String viewCours(@PathVariable("coursId") Long coursId, Model model) {
        Cours cours = coursService.findById(coursId);
        model.addAttribute("cours", cours);
        model.addAttribute("commentaires", commentaireService.findByCours(cours));
        return "cours/view_cours";
    }

    @PostMapping("/{coursId}/comment")
    public String addComment(@PathVariable("coursId") Long coursId, @RequestParam("texte") String texte, @RequestParam("utilisateurId") Long utilisateurId) {
        Cours cours = coursService.findById(coursId);
        Utilisateur utilisateur = utilisateurService.findById(utilisateurId);
        Commentaire commentaire = new Commentaire();
        commentaire.setTexte(texte);
        commentaire.setCours(cours);
        commentaire.setUtilisateur(utilisateur);
        commentaireService.save(commentaire);
        return "redirect:/cours/" + coursId;
    }

    @PostMapping("/{coursId}/comment/{commentaireId}/reply")
    public String replyToComment(@PathVariable("coursId") Long coursId, @PathVariable("commentaireId") Long commentaireId, @RequestParam("texte") String texte, @RequestParam("utilisateurId") Long utilisateurId) {
        Commentaire parentCommentaire = commentaireService.findById(commentaireId);
        Utilisateur utilisateur = utilisateurService.findById(utilisateurId);
        Commentaire reponse = new Commentaire();
        reponse.setTexte(texte);
        reponse.setCours(parentCommentaire.getCours());
        reponse.setUtilisateur(utilisateur);
        reponse.setParentCommentaire(parentCommentaire);
        commentaireService.save(reponse);
        return "redirect:/cours/" + coursId;
    }
}