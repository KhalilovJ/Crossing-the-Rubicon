package az.evilcastle.crossingtherubicon.dao.repository;

import az.evilcastle.crossingtherubicon.dao.entity.WebsocketClientCollection;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface WebSocketClientMongoRepository extends MongoRepository<WebsocketClientCollection,String> {

    Optional<WebsocketClientCollection> findBySocketId(String websocketClientId);

}
