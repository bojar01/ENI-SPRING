package fr.eni.ludotheque.bo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Document(collection = "clients")
public class Client {

	@Id
	@EqualsAndHashCode.Exclude
	private String noClient;

	@Field("nom")
	@NonNull
	private String nom;

	@Field("prenom")
	@NonNull
	private String prenom;

	@Field("email")
	@NonNull
	private String email;

	@Field("no_telephone")
	private String noTelephone;

	@Field("adresse")
	@NonNull
	private Adresse adresse;

}