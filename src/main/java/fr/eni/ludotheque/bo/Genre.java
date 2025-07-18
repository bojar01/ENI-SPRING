package fr.eni.ludotheque.bo;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Document(collection = "Genres")
public class Genre {
	@Id
	@NonNull
	private String id;

	@NonNull
	private String libelle;
	
}
