package com.studies.financialmanagement.api.security;

import com.studies.financialmanagement.api.models.Users;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class UserSystem extends User {

    private Users user;

    public UserSystem(Users user,  Collection<? extends GrantedAuthority> authorities) {
        super(user.getEmail(), user.getPassword(), authorities);
        this.user = user;
    }
}
