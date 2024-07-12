package com.example.GestionCours.repository;

import com.example.GestionCours.model.Commentaire;
import com.example.GestionCours.model.Cours;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentaireRepository extends JpaRepository<Commentaire, Long> {
    List<Commentaire> findByCours(Cours cours);
}
