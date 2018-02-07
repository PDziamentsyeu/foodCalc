package main.java.application.web.security.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import main.java.application.web.exceptions.AuthorizationException;
import main.java.application.web.security.jwt.model.UserClaims;

import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;

@Service
public class JwtService {

    private static final String KEY = "dsfae5ut12134151252asasfgfdrewnfdaswr3dfsg2t3y67icxdz21";
    private static final String ID_KEY = "id";
    private static final String ROLE_KEY = "role";
    private static final String AUTH_HEADER_NAME = "Authorization";

    public static Claims getClaims(final String token) {
        return Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody();
    }

    public static String getRoleFromToken(final String token) {
        return Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody().get(ROLE_KEY, String.class);
    }

    public static UserClaims getUserClaims(final HttpServletRequest request) throws JwtParseClaimsException {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
        final String token = request.getHeader(AUTH_HEADER_NAME);
        if (token == null) {
            throw new JwtParseClaimsException(String.format("Header [%s] does not exists in request", AUTH_HEADER_NAME));
        }
        final Claims claims = Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody();
        if (claims == null) {
            throw new JwtParseClaimsException("Claims is null");
        }
        return new UserClaims(
                claims.get(ID_KEY) instanceof Integer ? claims.get(ID_KEY, Integer.class) : claims.get(ID_KEY, Long.class),
                claims.get(ROLE_KEY, String.class),
                claims.getSubject()
        );
    }

    public static String generateToken(final String name, long id, final String role) throws AuthorizationException {
        Claims claims = Jwts.claims().setSubject(name);
        claims.put(ID_KEY, id);
        claims.put(ROLE_KEY, role.toUpperCase());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 24);

        String token = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, KEY)
                .setExpiration(calendar.getTime()).compact();
        if (token == null) {
            throw new AuthorizationException("Token can not be generated");
        }
        return token;
    }

}
