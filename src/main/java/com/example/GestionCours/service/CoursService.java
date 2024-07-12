package com.example.GestionCours.service;

import com.example.GestionCours.model.Cours;
import com.example.GestionCours.model.Utilisateur;

import java.util.List;
import java.util.Optional;

public interface CoursService {
    List<Cours> findAll();
    List<Cours> findAllPublished();
    Cours findById(Long id);
    Cours save(Cours cours);
    void deleteById(Long id);

    void publishCourse(Long coursId, boolean published);

    List<Cours> getCoursPublies();

    List<Cours> getCoursPubliesByUE(Long ueId);

    List<Cours> findByUtilisateur(Optional<Utilisateur> utilisateur);
}
