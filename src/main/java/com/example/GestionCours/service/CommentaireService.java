package com.example.GestionCours.service;

import com.example.GestionCours.model.Commentaire;
import com.example.GestionCours.model.Cours;

import java.util.List;

public interface CommentaireService {
    List<Commentaire> findByCours(Cours cours);
    Commentaire findById(Long id);
    void save(Commentaire commentaire);
}
