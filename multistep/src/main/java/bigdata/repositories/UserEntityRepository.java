package bigdata.repositories;

import bigdata.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

    @Modifying
    @Transactional
    @Query("update UserEntity set password = ?2 where username = ?1")
    void updateUserInfo(String username, String password);

    @Transactional
    @Query("select t from UserEntity as t where t.username = ?1 AND t.password = ?2")
    List<UserEntity> findUser(String username, String password);
}