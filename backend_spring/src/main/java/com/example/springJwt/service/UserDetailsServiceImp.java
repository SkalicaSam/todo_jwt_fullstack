package com.example.springJwt.service;


import com.example.springJwt.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImp implements UserDetailsService {  // on UserDetailsService click implemnet methods and then UserDetails..

    private final UserRepository repository;

    public UserDetailsServiceImp(UserRepository repository){
        this.repository = repository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("User not Found"));
    }
}
