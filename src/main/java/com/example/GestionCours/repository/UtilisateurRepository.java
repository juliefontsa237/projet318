package com.example.GestionCours.repository;

import com.example.GestionCours.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Optional<Utilisateur> findByNom(String nom);
    Utilisateur findByEmail(String email);
}
