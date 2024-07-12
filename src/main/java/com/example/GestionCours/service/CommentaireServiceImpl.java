package com.example.GestionCours.service;

import com.example.GestionCours.model.Commentaire;
import com.example.GestionCours.model.Cours;
import com.example.GestionCours.repository.CommentaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentaireServiceImpl implements CommentaireService {

    @Autowired
    private CommentaireRepository commentaireRepository;

    @Override
    public List<Commentaire> findByCours(Cours cours) {
        return commentaireRepository.findByCours(cours);
    }

    @Override
    public Commentaire findById(Long id) {
        return commentaireRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Commentaire commentaire) {
        commentaireRepository.save(commentaire);
    }
}
