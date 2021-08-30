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

		String stringa;
		switch (this) {
		case BORGOPANIGALE_RENO:
			stringa = "Borgo Panigale - Reno";
			break;

		case PORTO_SARAGOZZA:
			stringa = "Porto - Saragozza";
			break;

		case NAVILE:
			stringa = "Navile";
			break;

		case SANDONATO_SANVITALE:
			stringa = "San Donato - San Vitale";
			break;

		case SAVENA:
			stringa = "Savena";
			break;

		case SANTOSTEFANO:
			stringa = "Santo Stefano";
			break;

		default:
			throw new IllegalArgumentException();
		}

		return stringa;
	}

}
