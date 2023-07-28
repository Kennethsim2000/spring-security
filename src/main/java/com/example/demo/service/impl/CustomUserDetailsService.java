package com.example.demo.service.impl;

import com.example.demo.models.UserEntity;
import com.example.demo.models.Roles;
import com.example.demo.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Load user details from the data source based on the username
        System.out.println("UserDetails method called");
        UserEntity user = userEntityRepository.findByName(username);
        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        Collection<GrantedAuthority> lst = new ArrayList<>();
        lst.add(new SimpleGrantedAuthority("ADMIN"));
        UserDetails returnUser = User.withUsername(user.getName())
                .password(user.getPassword())
//                .authorities(getAuthorities(user)).build();
                .authorities(lst) .build();
        // Convert the user entity to Spring Security's UserDetails
        return  returnUser;
    }
    //return collection of authorities of user
    private Collection<GrantedAuthority> getAuthorities(UserEntity customer){
        List<Roles> userGroups = customer.getUserRoles();
        Collection<GrantedAuthority> authorities = new ArrayList<>(userGroups.size());
        for(Roles role : userGroups){
            authorities.add(new SimpleGrantedAuthority(role.getCode().toUpperCase()));
        } //using code of role to determine authority
        return authorities;
    }

}
