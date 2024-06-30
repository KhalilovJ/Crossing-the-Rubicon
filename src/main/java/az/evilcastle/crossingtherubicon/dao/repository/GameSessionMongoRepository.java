package az.evilcastle.crossingtherubicon.dao.repository;

import az.evilcastle.crossingtherubicon.dao.entity.GameSessionEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameSessionMongoRepository extends MongoRepository<GameSessionEntity, String> {

    Optional<GameSessionEntity> findByLobbyName(String sessionName);
}
