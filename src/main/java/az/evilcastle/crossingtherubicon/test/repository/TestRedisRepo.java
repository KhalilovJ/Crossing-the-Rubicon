package az.evilcastle.crossingtherubicon.test.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRedisRepo extends CrudRepository<String, String> {
}
