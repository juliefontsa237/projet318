package com.example.GestionCours.service;

import com.example.GestionCours.model.Role;
import com.example.GestionCours.model.Utilisateur;
import com.example.GestionCours.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void saveUtilisateur(Utilisateur utilisateur) {
        utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
        utilisateur.setRole(Role.APPRENANT); // Définir le rôle ici
        utilisateurRepository.save(utilisateur);
    }

    public Optional<Utilisateur> findByNom(String nom) {
        return utilisateurRepository.findByNom(nom);
    }

    public Utilisateur findById(Long utilisateurId) {
        return utilisateurRepository.findById(utilisateurId).orElse(null);
    }

    public Utilisateur findByEmail(String email) {
        return utilisateurRepository.findByEmail(email);
    }
    // Autres méthodes selon les besoins
}
