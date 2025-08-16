package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.Repositry.jornalRepo;
import net.engineeringdigest.journalApp.entity.Journalentry;
import net.engineeringdigest.journalApp.entity.usersEntity;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class journalentryservice {
    @Autowired
    private jornalRepo jornalRepo;
    @Autowired
    private UserService userService;


    @Transactional
    public void saveEntry(Journalentry entry, String userName){
        try{
            usersEntity user= userService.findByUserName(userName);
            entry.setDate(LocalDateTime.now());
            Journalentry saved = jornalRepo.save(entry);
            user.getJournalentries().add(saved);
            userService.saveEntry(user);
        }catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("An error ocuured",e);
        }
    }
    public void saveEntry(Journalentry entry){
        jornalRepo.save(entry);
    }
    public List<Journalentry> getAll(){
        return jornalRepo.findAll();
    }

    public Optional<Journalentry> findById(ObjectId ids){
        return jornalRepo.findById(ids);
    }
    @Transactional
    public boolean deleteOne(ObjectId ids,String userName){
        boolean removed=false;
        try{
            usersEntity byUserName = userService.findByUserName(userName);
            removed= byUserName.getJournalentries().removeIf(x->x.getId().equals(ids));
            if(removed) {
                userService.saveEntry(byUserName);
                jornalRepo.deleteById(ids);
            }
        }catch(Exception e){
            System.out.println(e);
            throw new RuntimeException("error occured while deleting entry",e);
        }
        return  removed;
    }
}
//controller-->Service-->repositry.