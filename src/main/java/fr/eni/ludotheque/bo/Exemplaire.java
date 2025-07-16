package fr.eni.ludotheque.bo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Document(collection = "exemplaires")
public class Exemplaire {

	@Id
	@EqualsAndHashCode.Exclude
	private String noExemplaire;

	@Field("code_barre")
	@NonNull
	private String codebarre;

	@Field("louable")
	private boolean louable = true;

	@DBRef
	@Field("jeu")
	@NonNull
	private Jeu jeu;

}