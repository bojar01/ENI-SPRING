package fr.eni.ludotheque.bll;

import fr.eni.ludotheque.bo.Exemplaire;

import java.util.List;

public interface ExemplaireService {
    void createExemplaire(Exemplaire exemplaire);
    Exemplaire getExemplaire(String noExemplaire);
    void deleteExemplaire(String noExemplaire);
    List<Exemplaire> getAllExemplaire();
}
