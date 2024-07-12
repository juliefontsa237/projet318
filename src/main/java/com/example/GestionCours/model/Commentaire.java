package com.example.GestionCours.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Commentaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String texte;
    private LocalDateTime dateCommentaire;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "cours_id")
    private Cours cours;

    @ManyToOne
    @JoinColumn(name = "parent_commentaire_id")
    private Commentaire parentCommentaire;

    @OneToMany(mappedBy = "parentCommentaire", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Commentaire> reponses = new ArrayList<>();

    public Commentaire() {}

    public Commentaire(String texte, Utilisateur utilisateur, Cours cours) {
        this.texte = texte;
        this.utilisateur = utilisateur;
        this.cours = cours;
        this.dateCommentaire = LocalDateTime.now();
    }

    // getters and setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public LocalDateTime getDateCommentaire() {
        return dateCommentaire;
    }

    public void setDateCommentaire(LocalDateTime dateCommentaire) {
        this.dateCommentaire = dateCommentaire;
    }

    public Cours getCours() {
        return cours;
    }

    public void setCours(Cours cours) {
        this.cours = cours;
    }

    public Commentaire getParentCommentaire() {
        return parentCommentaire;
    }

    public void setParentCommentaire(Commentaire parentCommentaire) {
        this.parentCommentaire = parentCommentaire;
    }

    public List<Commentaire> getReponses() {
        return reponses;
    }

    public void setReponses(List<Commentaire> reponses) {
        this.reponses = reponses;
    }


}
