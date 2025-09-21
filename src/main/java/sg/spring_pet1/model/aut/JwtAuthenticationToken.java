package sg.spring_pet1.model.aut;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;


public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final String token;

    public JwtAuthenticationToken(String token) {
        super(null);
        this.token = token;
        setAuthenticated(true); // Изначально не аутентифицирован
    }

    public JwtAuthenticationToken(String token, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.token = token;
        super.setAuthenticated(true); // Аутентифицирован
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return null; // В principal можешь положить ID пользователя или другие данные, извлеченные из JWT
    }

    public String getToken() {
        return token;
    }

}
