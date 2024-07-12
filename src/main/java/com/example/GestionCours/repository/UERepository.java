package com.example.GestionCours.repository;

import com.example.GestionCours.model.UE;
import com.example.GestionCours.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UERepository extends JpaRepository<UE, Long> {
    List<UE> findAllByUtilisateur(Utilisateur utilisateur);


}
