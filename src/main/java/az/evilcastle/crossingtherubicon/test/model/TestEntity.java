package az.evilcastle.crossingtherubicon.test.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@RedisHash("test_entity")
public class TestEntity implements Serializable {
    @Id
    private String id;
    private String string;
}
