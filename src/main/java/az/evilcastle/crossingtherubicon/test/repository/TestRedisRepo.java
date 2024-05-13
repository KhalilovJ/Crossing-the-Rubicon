package az.evilcastle.crossingtherubicon.test.repository;

import az.evilcastle.crossingtherubicon.test.model.TestEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRedisRepo extends CrudRepository<TestEntity, String> {
}
