package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.Repositry.UserRepository;
import net.engineeringdigest.journalApp.entity.usersEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImp implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        usersEntity user=userRepository.findByUserName(userName);
        if(user!=null){
            UserDetails userdetails = User.builder()
                    .username(user.getUserName())
                    .password(user.getPassword())
                    .roles(user.getRoles().toArray(new String[0]))
                    .build();
            return userdetails;
        }
        throw new UsernameNotFoundException("User Not Found"+userName);
    }
}
