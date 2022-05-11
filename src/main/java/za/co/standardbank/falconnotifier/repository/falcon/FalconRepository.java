package za.co.standardbank.falconnotifier.repository.falcon;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import za.co.standardbank.falconnotifier.entites.falcon.CsCaseStatusEntity;
import za.co.standardbank.falconnotifier.entites.falcon.FalconEntity;

@Repository
public interface FalconRepository extends CrudRepository<FalconEntity, Long> {

	@Query(value = "select * from R_CS_CASE_H rc inner join secrty_user su on su.user_party_id = rc.action_user_party_id where TRUNC(rc.ACTION_DTTM) = TO_DATE(:date, 'YYYY-MM-DD') order by ACTION_DTTM asc", nativeQuery = true)
	List<FalconEntity> fetchDataByDate(@Param("date") String date);

	@Query(value = "select * from R_CS_CASE_H rc inner join secrty_user su on su.user_party_id = rc.action_user_party_id where rc.ACTION_DTTM > (sysdate -1) order by ACTION_DTTM asc", nativeQuery = true)
	List<FalconEntity> fetchData();

	@Query(value = "SELECT NEW CsCaseStatusEntity(e.caseId, e.caseStatus, e.lastUpdate, e.created) FROM CsCaseStatusEntity e where e.caseStatus = 'CONFIRMED_FRAUD' and e.created > (sysdate -1) order by e.created asc")
	List<CsCaseStatusEntity> fetchCsCaseStatuses();
	
	@Query(value = "SELECT NEW CsCaseStatusEntity(e.caseId, e.caseStatus, e.lastUpdate, e.created) FROM CsCaseStatusEntity e where e.caseStatus = 'CONFIRMED_FRAUD' and TRUNC(e.created) = TO_DATE(:date, 'YYYY-MM-DD') order by e.lastUpdate asc")
	List<CsCaseStatusEntity> fetchCsCaseStatusesByDate(@Param("date") String date);
}
