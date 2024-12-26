package com.salas.manager.app.service;

import com.salas.manager.app.entity.Authority;
import com.salas.manager.app.entity.ProjectUser;
import com.salas.manager.app.repo.ProjectUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProjectUserDetailsService implements UserDetailsService {

    @Autowired
    private ProjectUserRepository userRepository;

    public ProjectUserDetailsService(ProjectUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(user -> User.builder()
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .authorities(simpleGrantedAuthorities(user))
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("User %s not found".formatted(username)));
    }

    private static List<SimpleGrantedAuthority> simpleGrantedAuthorities(ProjectUser user) {
        return user.getAuthorities().stream()
                .map(Authority::getAuthority)
                .map(SimpleGrantedAuthority::new)
                .toList();
    }
}
