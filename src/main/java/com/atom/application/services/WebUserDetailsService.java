package com.atom.application.services;

import java.util.stream.Collectors;

import com.atom.application.models.UserPermission;
import com.atom.application.models.WebUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class WebUserDetailsService implements UserDetailsService {

    @Autowired
    private WebUserService service;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        WebUser user = service.getWebUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("No user found with username: '" + username + "'");
        }
        return User.builder().username(user.getUsername())
            .password(user.getPassword())
            .authorities(user.getRole().getPermissions().stream()
                .map(UserPermission::getName).map(SimpleGrantedAuthority::new).collect(Collectors.toList()))
            .build();
    }
    
}
