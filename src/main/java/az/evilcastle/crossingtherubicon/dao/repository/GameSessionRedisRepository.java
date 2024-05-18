package az.evilcastle.crossingtherubicon.dao.repository;

import az.evilcastle.crossingtherubicon.dao.entity.GameSessionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameSessionRedisRepository extends CrudRepository<GameSessionEntity, String> {
}
