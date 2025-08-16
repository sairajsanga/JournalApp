package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.usersEntity;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping
    public void getEntry(@RequestBody usersEntity user){
        userService.saveNewuser(user);
    }
    @PostMapping("/create-user")
    public void createEntry(@RequestBody usersEntity user){
            userService.saveNewuser(user);
    }

}
