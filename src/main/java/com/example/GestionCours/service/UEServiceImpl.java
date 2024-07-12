package com.example.GestionCours.service;

import com.example.GestionCours.model.UE;
import com.example.GestionCours.model.Utilisateur;
import com.example.GestionCours.repository.UERepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UEServiceImpl implements UEService {
    @Autowired
    private UERepository ueRepository;

    @Override
    public List<UE> findAllByUtilisateur(Utilisateur utilisateur) {
        return ueRepository.findAllByUtilisateur(utilisateur);
    }

    @Override
    public List<UE> findAll() {
        return ueRepository.findAll();
    }

    @Override
    public UE findById(Long id) {
        return ueRepository.findById(id).orElse(null);
    }

    @Override
    public UE save(UE ue) {
        return ueRepository.save(ue);
    }

    @Override
    public void delete(Long id) {
        ueRepository.deleteById(id);
    }
}
