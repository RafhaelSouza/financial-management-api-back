package com.studies.financialmanagement.api.security;

import com.studies.financialmanagement.api.models.Users;
import com.studies.financialmanagement.api.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Users> userOptional = usersRepository.findByEmail(email);
        Users user = userOptional.orElseThrow(() -> new UsernameNotFoundException("User or password is not valid."));

        return new User(email, user.getPassword(), getPermissions(user));
    }

    private Collection<? extends GrantedAuthority> getPermissions(Users user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getPermissions()
                .forEach(p -> authorities.add(new SimpleGrantedAuthority(p.getDescription().toUpperCase())));

        return authorities;
    }

}
