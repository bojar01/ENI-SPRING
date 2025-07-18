package fr.eni.ludotheque.exceptions;

public class ExemplaireNotFoundException extends RuntimeException  {
    public ExemplaireNotFoundException(String noExemplaire) {
        super("Exemplaire non trouvé avec l'identifiant : " + noExemplaire);
    }
}
