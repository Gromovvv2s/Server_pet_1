package sg.spring_pet1.model.aut;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;


public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private final String principal;
    public JwtAuthenticationToken(String principal) {
        super(null);
        this.principal = principal;
        setAuthenticated(false); // Изначально не аутентифицирован
    }

    public JwtAuthenticationToken(String principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        super.setAuthenticated(true); // Аутентифицирован
        this.principal = principal;
    }

    @Override
    public Object getCredentials() {
        return null;
    } // пароль

    @Override
    public Object getPrincipal() {
        return principal; //имя
    }
}
