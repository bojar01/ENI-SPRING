package fr.eni.ludotheque.bo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
@Document(collection = "factures")
public class Facture {

	@Id
	private String noFacture;

	@Field("date_paiement")
	private LocalDateTime datePaiement;

	@DBRef
	@Field("locations")
	private List<Location> locations = new ArrayList<>();

	@Transient
	private float prix;

	public void addLocation(Location location) {
		this.locations.add(location);
	}

	public void removeLocation(Location location) {
		this.locations.remove(location);
	}

	// Méthode pour calculer le prix total
	public float calculerPrix() {
		return locations.stream()
				.map(location -> location.getTarifJour() * getNombreJours(location))
				.reduce(0f, Float::sum);
	}

	private long getNombreJours(Location location) {
		if (location.getDateRetour() == null) {
			return java.time.Duration.between(location.getDateDebut(), LocalDateTime.now()).toDays() + 1;
		}
		return java.time.Duration.between(location.getDateDebut(), location.getDateRetour()).toDays() + 1;
	}
}