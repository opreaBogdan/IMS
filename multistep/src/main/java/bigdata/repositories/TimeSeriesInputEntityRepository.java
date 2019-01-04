package bigdata.repositories;

import bigdata.entities.TimeSeriesInputEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by radu on 9/19/2016.
 */
@Repository
public interface TimeSeriesInputEntityRepository extends JpaRepository<TimeSeriesInputEntity, Long> {

    @Modifying
    @Transactional
    @Query("update TimeSeriesInputEntity set algorithm = ?1 where token = ?2")
    void updateAlgorithm(String algorithm, String token);

    @Query("select t from TimeSeriesInputEntity as t where t.token = ?1")
    List<TimeSeriesInputEntity> findByToken(String token);
}

