//package com.fintech.appuser;
//
//import com.fintech.model.User;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import javax.persistence.EnumType;
//import javax.persistence.Enumerated;
//import java.util.Collection;
//import java.util.Collections;
//
//@Getter
//@Setter
//@NoArgsConstructor
//public class CustomUserDetails implements UserDetails {
//
//    private User user;
//
//    @Enumerated(EnumType.STRING)
//    private UserRole role;
//
//
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        SimpleGrantedAuthority authority =
//                new SimpleGrantedAuthority(role.name());
//        return Collections.singletonList(authority);
//    }
//
//    @Override
//    public String getPassword() {
//        return user.;
//    }
//
//    @Override
//    public String getUsername() {
//        return user.getUserName();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return !user.getLocked();
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return user.getEnabled();
//    }
//
//}
