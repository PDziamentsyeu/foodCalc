package main.java.application.web.security.jwt;

public class JwtParseClaimsException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JwtParseClaimsException(final String message) {
        super(message);
    }
}
