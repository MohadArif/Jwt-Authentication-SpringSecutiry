package com.example.jwtAutenticationDemo.config;

import com.example.jwtAutenticationDemo.entity.UserInfo;
import com.example.jwtAutenticationDemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserInfoDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userInfo = userRepository.findByusername(username);
        return userInfo.map(UserInfoDetails::new).orElseThrow(()->
                new UsernameNotFoundException("usern not found!!" + username));
    }
}
