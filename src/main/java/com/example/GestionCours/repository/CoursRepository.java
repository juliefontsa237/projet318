package com.example.GestionCours.repository;

import com.example.GestionCours.model.Cours;
import com.example.GestionCours.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CoursRepository extends JpaRepository<Cours, Long> {
    List<Cours> findByPublieTrue(); // Récupère tous les cours publiés

    List<Cours> findByUeIdAndPublieTrue(Long ueId); // Récupère les cours publiés pour une UE spécifique

    List<Cours> findByUtilisateur(Optional<Utilisateur> utilisateur);
}
