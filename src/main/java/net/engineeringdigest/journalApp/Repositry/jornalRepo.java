package net.engineeringdigest.journalApp.Repositry;

import net.engineeringdigest.journalApp.entity.Journalentry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;



public interface jornalRepo extends MongoRepository<Journalentry, ObjectId> {

}
