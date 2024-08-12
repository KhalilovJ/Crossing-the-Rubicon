package az.evilcastle.crossingtherubicon.dao.repository;


import az.evilcastle.crossingtherubicon.dao.entity.player.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface PlayerRepository extends JpaRepository<PlayerEntity,Long> {

    //findUserByEmailAndActiveIsTrue
    Optional<PlayerEntity> findPlayerEntityByEmailAndActiveIsTrue(String email);
}
