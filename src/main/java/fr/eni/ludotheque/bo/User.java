package fr.eni.ludotheque.bo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "users")
public class User implements UserDetails {
    @Id
    private String id;

    @Field("username")
    @NonNull
    private String username;

    @Field("role")
    @NonNull
    private String role;

    @Field("password")
    @NonNull
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role)); // ✅ Corrigé ici
    }

    @Override public String getPassword() { return this.password; }
    @Override public String getUsername() { return this.username; }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }

    // Tes getters/setters manuels ici si tu veux les garder, sinon @Data suffit
}
