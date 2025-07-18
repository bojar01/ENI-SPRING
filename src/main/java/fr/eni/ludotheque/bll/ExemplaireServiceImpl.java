package fr.eni.ludotheque.bll.impl;

import fr.eni.ludotheque.bll.ExemplaireService;
import fr.eni.ludotheque.bo.Exemplaire;
import fr.eni.ludotheque.exceptions.ExemplaireNotFoundException;
import fr.eni.ludotheque.dal.ExemplaireRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExemplaireServiceImpl implements ExemplaireService {

    private final ExemplaireRepository exemplaireRepository;

    @Override
    public void createExemplaire(Exemplaire exemplaire) {
        exemplaireRepository.save(exemplaire);
    }

    @Override
    public Exemplaire getExemplaire(String noExemplaire) {
        return exemplaireRepository.findById(noExemplaire)
                .orElseThrow(() -> new ExemplaireNotFoundException(noExemplaire));
    }

    @Override
    public void deleteExemplaire(String noExemplaire) {
        if (!exemplaireRepository.existsById(noExemplaire)) {
            throw new ExemplaireNotFoundException(noExemplaire);
        }
        exemplaireRepository.deleteById(noExemplaire);
    }

    @Override
    public List<Exemplaire> getAllExemplaire() {
        return exemplaireRepository.findAll();
    }
}

