package com.example.GestionCours.service;

import com.example.GestionCours.model.Cours;
import com.example.GestionCours.model.Utilisateur;
import com.example.GestionCours.repository.CoursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CoursServiceImpl implements CoursService {

    private final CoursRepository coursRepository;

    @Autowired
    public CoursServiceImpl(CoursRepository coursRepository) {
        this.coursRepository = coursRepository;
    }

    @Override
    public List<Cours> findAll() {
        return coursRepository.findAll();
    }

    @Override
    public List<Cours> findAllPublished() {
        return coursRepository.findByPublieTrue();
    }

    @Override
    public Cours findById(Long id) {
        Optional<Cours> optionalCours = coursRepository.findById(id);
        return optionalCours.orElse(null);
    }


    @Override
    public Cours save(Cours cours) {
        return coursRepository.save(cours);
    }

    @Override
    public void deleteById(Long id) {
        coursRepository.deleteById(id);
    }

    @Override
    public void publishCourse(Long coursId, boolean published) {
        Cours cours = coursRepository.findById(coursId)
                .orElseThrow(() -> new IllegalArgumentException("Cours non trouvé: " + coursId));
        cours.setPublie(published);
        coursRepository.save(cours);
    }

    @Override
    public List<Cours> getCoursPublies() {
        return coursRepository.findByPublieTrue();
    }

    @Override
    public List<Cours> getCoursPubliesByUE(Long ueId) {
        return coursRepository.findByUeIdAndPublieTrue(ueId);
    }

    @Override
    public List<Cours> findByUtilisateur(Optional<Utilisateur> utilisateur) {
        return coursRepository.findByUtilisateur(utilisateur);
    }
}
