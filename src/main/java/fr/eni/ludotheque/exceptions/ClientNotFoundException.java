package fr.eni.ludotheque.exceptions;

import com.mongodb.lang.NonNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ClientNotFoundException extends RuntimeException {
    @NonNull
    private final Integer idClient;

    @Override
    public String getMessage() {
        return "Le client avec l'ID " + idClient + " n'a pas été trouvé.";
    }

}
