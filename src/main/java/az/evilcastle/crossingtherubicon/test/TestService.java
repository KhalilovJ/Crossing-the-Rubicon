package az.evilcastle.crossingtherubicon.test;

import az.evilcastle.crossingtherubicon.test.model.TestEntity;
import az.evilcastle.crossingtherubicon.test.repository.TestRedisRepo;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestService {
    private final TestRedisRepo testRedisRepo;

    @PostConstruct
    private void testRedis(){
        System.out.println(testRedisRepo.save(TestEntity.builder().string("str").build()));
    }

}
