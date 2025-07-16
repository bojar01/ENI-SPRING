package fr.eni.ludotheque.bo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "Locations")
public class Location {

	@Id
	private String noLocation; // MongoDB utilise String pour les IDs

	@EqualsAndHashCode.Include
	@Field("date_debut")
	@NonNull
	private LocalDateTime dateDebut;

	@Field("date_retour")
	private LocalDateTime dateRetour;

	@Field("tarif_jour")
	private float tarifJour;

	// Relations avec MongoDB: Référence par DBRef
	@EqualsAndHashCode.Include
	@DBRef
	@Field("client")
	@NonNull
	private Client client;

	@EqualsAndHashCode.Include
	@DBRef
	@Field("exemplaire")
	@NonNull
	private Exemplaire exemplaire;
}
