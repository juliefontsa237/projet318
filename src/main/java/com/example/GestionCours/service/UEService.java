package com.example.GestionCours.service;

import com.example.GestionCours.model.UE;
import com.example.GestionCours.model.Utilisateur;

import java.util.List;

public interface UEService {
    List<UE> findAll();
    UE findById(Long id);
    UE save(UE ue);
    void delete(Long id);

    List<UE> findAllByUtilisateur(Utilisateur utilisateur);
}
