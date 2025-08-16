package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.Repositry.UserRepository;
import net.engineeringdigest.journalApp.entity.usersEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.mockito.Mockito.when;



public class UserDetailsServiceImpTests {

    @InjectMocks
    private UserDetailsServiceImp userDetailsServiceImp;

    @Mock
    private UserRepository userRepository;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
    }


    void loadUserByUsernameTest(){
        when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(usersEntity.builder().userName("kalyan").password("mnbv").roles(new ArrayList<>()).build());
        UserDetails user = userDetailsServiceImp.loadUserByUsername("kalyan");
        Assertions.assertNotNull(user);
    }
}
