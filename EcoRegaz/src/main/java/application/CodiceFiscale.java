package main.java.application;

/**
 * Italian Codice Fiscale normalization, formatting and validation routines.
 * A <u>regular CF</u> is composed by 16 among letters and digits; the last
 * character is always a letter representing the control code.
 * A <u>temporary CF</u> could also be assigned; a temporary CF is composed of
 * 11 digits, the last digit being the control code.
 * Examples: MRORSS00A00A000U, 12345678903.
 * @author Umberto Salsi <salsi@icosaedro.it>
 * @version 2020-01-24
 */
public class CodiceFiscale {
	
	/**
	 * Normalizes a CF by removing white spaces and converting to upper-case.
	 * Useful to clean-up user's input and to save the result in the DB.
	 * @param cf Raw CF, possibly with spaces.
	 * @return Normalized CF.
	 */
	static String normalize(String cf)
	{
		cf = cf.replaceAll("[ \t\r\n]", "");
		cf = cf.toUpperCase();
		return cf;
	}
	
	/**
	 * Returns the formatted CF. Currently does nothing but normalization.
	 * @param cf Raw CF, possibly with spaces.
	 * @return Formatted CF.
	 */
	static String format(String cf)
	{
		return normalize(cf);
	}
	
	/**
	 * Validates a regular CF.
	 * @param cf Normalized, 16 characters CF.
	 * @return Null if valid, or string describing why this CF must be rejected.
	 */
	private static String validate_regular(String cf)
	{
		if( ! cf.matches("^[0-9A-Z]{16}$") )
			return "Invalid characters.";
		int s = 0;
		String even_map = "BAFHJNPRTVCESULDGIMOQKWZYX";
		for(int i = 0; i < 15; i++){
			int c = cf.charAt(i);
			int n;
			if( '0' <= c && c <= '9' )
				n = c - '0';
			else
				n = c - 'A';
			if( (i & 1) == 0 )
				n = even_map.charAt(n) - 'A';
			s += n;
		}
		if( s%26 + 'A' != cf.charAt(15) )
			return "Invalid checksum.";
		return null;
	}
	
	/**
	 * Validates a temporary CF.
	 * @param cf Normalized, 11 characters CF.
	 * @return Null if valid, or string describing why this CF must be rejected.
	 */
	private static String validate_temporary(String cf)
	{
		if( ! cf.matches("^[0-9]{11}$") )
			return "Invalid characters.";
		int s = 0;
		for(int i = 0; i < 11; i++){
			int n = cf.charAt(i) - '0';
			if( (i & 1) == 1 ){
				n *= 2;
				if( n > 9 )
					n -= 9;
			}
			s += n;
		}
		if( s % 10 != 0 )
			return "Invalid checksum.";
		return null;
	}
	
	/**
	 * Verifies the basic syntax, length and control code of the given CF.
	 * @param cf Raw CF, possibly with spaces.
	 * @return Null if valid, or string describing why this CF must be rejected.
	 */
	static String validate(String cf)
	{
		cf = normalize(cf);
		if( cf.length() == 0 )
			return "Empty.";
		else if( cf.length() == 16 )
			return validate_regular(cf);
		else if( cf.length() == 11 )
			return validate_temporary(cf);
		else
			return "Invalid length.";
	}
	
	// From here on, test code.
	// ========================
	
	/**
	 * @param raw_cf Raw CF, possibly with white spaces.
	 * @param exp_err Expected validation error.
	 * @throws RuntimeException The test failed.
	 */
	private static void test(String raw_cf, String exp_err)
	{
		String got_err = validate(raw_cf);
		if( ! ("" + got_err).equals("" + exp_err) )
				throw new RuntimeException("got validation: " + got_err);
	}

	/*
	 * Runs all the tests.
	 * @throws RuntimeException The test failed.
	 */
	public static void main(String args[])
	{
		test("", "Empty.");
		test("@@@", "Invalid length.");
		test("@@@@@@@@@@@@@@@@", "Invalid characters.");
		test("@@@@@@@@@@@", "Invalid characters.");
		test("MRORSS00A00A000V", "Invalid checksum.");
		test("MRORSS00A+0A000V", "Invalid characters.");
		test("00000+00000", "Invalid characters.");
		test("12345678901", "Invalid checksum.");
		test("00000000001", "Invalid checksum.");

		test("MRO rSs 00a00 A000U", null);
		test("KJWMFE88C50E205S", null);
		test("GNNTIS14L02X498V", null);
		test("JKNXZK26E16Y097M", null);
		test("00000000000", null);
		test("44444444440", null);
		test("12345678903", null);
		test("74700694370", null);
		test("57636564049", null);
		test("19258897628", null);
		test("08882740981", null);
		test("4730 9842  806", null);
	}
	
}