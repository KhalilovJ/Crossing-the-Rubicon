package az.evilcastle.crossingtherubicon.dao.repository;

import az.evilcastle.crossingtherubicon.dao.entity.WebSocketLobbyEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WebSocketLobbyMongoRepository extends MongoRepository<WebSocketLobbyEntity,String> {

}
