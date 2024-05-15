package az.evilcastle.crossingtherubicon.repository;

import az.evilcastle.crossingtherubicon.models.GameSession;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameSessionRedisRepo extends CrudRepository<GameSession, String> {
}
