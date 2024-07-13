package az.evilcastle.crossingtherubicon.dao.repository;

import az.evilcastle.crossingtherubicon.dao.entity.player.PlayerEntity;
import az.evilcastle.crossingtherubicon.dao.entity.player.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token,Long> {

    @Query("select t from Token t inner join PlayerEntity p on t.player.id=p.id where p.id=?1 and t.expired=false or t.revoked=false ")
    List<Token> findAllValidTokenByPlayer(Long id);
    List<Token> findAllByExpiredIsFalseAndRevokedIsFalseAndPlayer(PlayerEntity player);
    Optional<Token> findByToken(String token);

    void deleteAllByPlayerId(Long playerId);
}
