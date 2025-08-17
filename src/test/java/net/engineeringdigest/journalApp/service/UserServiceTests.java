package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.Repositry.UserRepository;
import net.engineeringdigest.journalApp.entity.usersEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserServiceTests {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void TestfindByUserName(){
        usersEntity user = userRepository.findByUserName("Kodi");
        assertTrue(!user.getJournalentries().isEmpty());
    }
    //parameterised testing it uses source to specify the aramters in methods.
//
    @ParameterizedTest
    @ValueSource(strings = {
            "Kodi"
    })
    public void TestfindByUserName(String userName){
        assertNotNull(userRepository.findByUserName(userName));
    }

    // for custom source we use @ArgumentsSource
    //we need to create a class and store the data and then call in the method
//    @ParameterizedTest
//    @ArgumentsSource(UserArgumentsProvider.class)
//    public void TestfindByUserName(usersEntity user){
//        assertTrue(userService.saveNewuser(user));
//    }
}
