package fr.eni.ludotheque.bo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "jeux")
public class Jeu {

	@Id
	private String noJeu;

	@Field("titre")
	@NonNull
	private String titre;

	@EqualsAndHashCode.Include
	@Field("reference")
	@NonNull
	private String reference;

	@Field("age_min")
	private int ageMin;

	@Field("description")
	private String description;

	@Field("duree")
	private int duree;

	@Field("tarif_jour")
	@NonNull
	private Float tarifJour;

	@Transient
	private int nbExemplairesDisponibles;

	@Field("genres")
	private List<Genre> genres = new ArrayList<>();

	public void addGenre(Genre g) {
		genres.add(g);
	}

	public void removeGenre(Genre g) {
		genres.remove(g);
	}
}