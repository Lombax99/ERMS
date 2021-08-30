package main.java.models.strumenti.scatolaGuanti;

public enum Taglia {

	XS,
	S,
	M,
	L,
	XL;
	
	@Override
	public String toString() {
		switch (this) 
		{
		case XS: return "ExtraSmall";
		
		case S: return "Small";

		case M: return "Medium";

		case L: return "Large";

		case XL: return "ExtraLarge";
		
		default: throw new IllegalArgumentException();
		}
	}

	}
