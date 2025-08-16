package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.Repositry.UserRepository;
import net.engineeringdigest.journalApp.entity.usersEntity;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserControllerV2 {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;


   @PutMapping
   public ResponseEntity<?> updateUser(@RequestBody usersEntity user){
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       String userName = authentication.getName();
       usersEntity userIndb= userService.findByUserName(userName);
       userIndb.setUserName(user.getUserName());
       userIndb.setPassword(user.getPassword());
       userService.saveNewuser(userIndb);
       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }
    @DeleteMapping()
    public ResponseEntity<?> DeleteUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        userRepository.deleteByuserName(userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
