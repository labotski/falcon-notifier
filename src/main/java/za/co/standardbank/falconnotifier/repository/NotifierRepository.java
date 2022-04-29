package za.co.standardbank.falconnotifier.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import za.co.standardbank.falconnotifier.model.NotifierEntity;

@Repository
public interface NotifierRepository extends CrudRepository<NotifierEntity, Long> {

	@Query(value = "select * from R_CS_CASE_H rc inner join secrty_user su on su.user_party_id = rc.action_user_party_id where TRUNC(rc.ACTION_DTTM) = TO_DATE(:date, 'YYYY-MM-DD') order by ACTION_DTTM asc", nativeQuery = true)
	List<NotifierEntity> fetchData(@Param("date") String date);

}
