package com.example.GestionCours.service;

import com.example.GestionCours.model.Utilisateur;
import com.example.GestionCours.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Override
    public UserDetails loadUserByUsername(String nom) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurRepository.findByNom(nom)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec le nom d'utilisateur: " + nom));
        return utilisateur;
    }
}
