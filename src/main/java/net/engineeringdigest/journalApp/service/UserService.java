package net.engineeringdigest.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.Repositry.UserRepository;
import net.engineeringdigest.journalApp.entity.usersEntity;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;
    private static final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    public void saveEntry(usersEntity user){
       userRepository.save(user);
    }

    public boolean saveNewuser(usersEntity user){
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
            return true;
        }catch(Exception e){
            log.info("user already exist");
            log.warn("user already exist");
            log.error("user already exist");
            log.debug("user already exist");
            log.trace("user already exist");
            return false;
        }

    }
    public List<usersEntity> getAll(){
        return userRepository.findAll();
    }

    public Optional<usersEntity> findById(ObjectId ids){
        return userRepository.findById(ids);
    }
    public void deleteOne(ObjectId ids){
         userRepository.deleteById(ids);
    }

    public usersEntity findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }
    public void saveAdmin(usersEntity user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        userRepository.save(user);
    }
}
//controller-->Service-->repositry.