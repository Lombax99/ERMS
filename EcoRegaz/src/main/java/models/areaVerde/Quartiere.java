package main.java.models.areaVerde;

public enum Quartiere {
	BORGOPANIGALE_RENO,
	PORTO_SARAGOZZA,
	NAVILE,
	SANDONATO_SANVITALE,
	SAVENA,
	SANTOSTEFANO;



@Override
public String toString() {
	switch (this) 
	{
	case BORGOPANIGALE_RENO: return "Borgo Panigale - Reno";
	
	case PORTO_SARAGOZZA: return "Porto - Saragozza";

	case NAVILE: return "Navile";

	case SANDONATO_SANVITALE: return "San Donato - San Vitale";

	case SAVENA: return "Savena";

	case SANTOSTEFANO: return "Santo Stefano";
	
	default: throw new IllegalArgumentException();
	}
}

}
