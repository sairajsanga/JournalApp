package net.engineeringdigest.journalApp.Repositry;

import net.engineeringdigest.journalApp.entity.usersEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;



public interface UserRepository extends MongoRepository<usersEntity,ObjectId> {
    usersEntity findByUserName(String userName);
    void deleteByuserName(String userName);



}
