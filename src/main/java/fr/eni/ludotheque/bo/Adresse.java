package fr.eni.ludotheque.bo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Document(collection = "Adresses")
public class Adresse {
	@Id
	@EqualsAndHashCode.Exclude
	private String noAdresse;
	
	@Field("rue")
	@NonNull private String rue;
	
	@Field("code_postal")
	@NonNull private String codePostal;
	
	@Field("ville")
	@NonNull private String ville;


}
