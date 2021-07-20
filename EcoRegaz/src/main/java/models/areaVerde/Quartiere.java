package main.java.models.areaVerde;

public enum Quartiere {
	BORGOPANIGALE_RENO, PORTO_SARAGOZZA, NAVILE, SANDONATO_SANVITALE, SAVENA, SANTOSTEFANO;

	/**
	 * Metodo che da stringa restituisce l'enum corretto.
	 */
	public static Quartiere toEnum(String quartiere) {
		switch (quartiere) {
		case "Borgo Panigale - Reno":
			return BORGOPANIGALE_RENO;
		case "Porto - Saragozza":
			return PORTO_SARAGOZZA;
		case "Navile":
			return NAVILE;
		case "San Donato - San Vitale":
			return SANDONATO_SANVITALE;
		case "Savena":
			return SAVENA;
		case "Santo Stefano":
			return SANTOSTEFANO;
		default:
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Metodo adattato che restituisce la corrispondente forma corretta.
	 */
	@Override
	public String toString() {
		switch (this) {
		case BORGOPANIGALE_RENO:
			return "Borgo Panigale - Reno";

		case PORTO_SARAGOZZA:
			return "Porto - Saragozza";

		case NAVILE:
			return "Navile";

		case SANDONATO_SANVITALE:
			return "San Donato - San Vitale";

		case SAVENA:
			return "Savena";

		case SANTOSTEFANO:
			return "Santo Stefano";

		default:
			throw new IllegalArgumentException();
		}
	}

}
