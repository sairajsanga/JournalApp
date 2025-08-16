package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.Journalentry;
import net.engineeringdigest.journalApp.entity.usersEntity;
import net.engineeringdigest.journalApp.service.UserService;
import net.engineeringdigest.journalApp.service.journalentryservice;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private journalentryservice Entryservice;
    @Autowired
    private UserService userService;


    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesofUser(){  //localhost:8080/journal-->GET
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        usersEntity user= userService.findByUserName(userName);
        List<Journalentry> temp= user.getJournalentries();
        if(temp!=null && !temp.isEmpty()){
            return new ResponseEntity<>(temp,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> createntry(@RequestBody Journalentry myentry){  //localhost:8080/journal-->POST
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            Entryservice.saveEntry(myentry,userName);
            return new ResponseEntity<>(myentry,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("id/{myid}")    //get a particular journal using id
    public ResponseEntity<Journalentry> getJournalEntrys(@PathVariable ObjectId myid){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        usersEntity user = userService.findByUserName(userName);
        List<Journalentry> collect = user.getJournalentries().stream().filter(x -> x.getId().equals(myid)).collect(Collectors.toList());
        if(!collect.isEmpty()){
            Optional<Journalentry> journalentry= Entryservice.findById(myid);

            if(journalentry.isPresent()){
                return new ResponseEntity<>(journalentry.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("id/{myid}")
    public ResponseEntity<?> removeJournalEntrys(@PathVariable ObjectId myid){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        boolean removed = Entryservice.deleteOne(myid, userName);
        if(removed){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PutMapping("id/{myid}")   //in which id u want to put what body type of id information
    public ResponseEntity<Journalentry> updateJournal(@PathVariable ObjectId myid,@RequestBody Journalentry newEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        usersEntity user = userService.findByUserName(userName);
        List<Journalentry> collect = user.getJournalentries().stream().filter(x -> x.getId().equals(myid)).collect(Collectors.toList());
        if(!collect.isEmpty()){
            Optional<Journalentry> journalentry= Entryservice.findById(myid);
            if(journalentry.isPresent()){
                Journalentry old = journalentry.get();
                old.setTitle(old.getTitle()!=null&&!old.getTitle().equals("")? newEntry.getTitle() : old.getTitle());
                old.setContent(old.getContent()!=null&&!old.getContent().equals("")?newEntry.getContent(): old.getContent());
                Entryservice.saveEntry(old);
                return new ResponseEntity<>(old,HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
