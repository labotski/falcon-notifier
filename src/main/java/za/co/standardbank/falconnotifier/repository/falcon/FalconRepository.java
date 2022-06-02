package za.co.standardbank.falconnotifier.repository.falcon;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;
import za.co.standardbank.falconnotifier.entites.falcon.CsCaseStatusEntity;
import za.co.standardbank.falconnotifier.entites.falcon.FalconEntity;

@Repository
@Transactional
public interface FalconRepository extends CrudRepository<FalconEntity, Long> {

	@Query(value = "select * from R_CS_CASE_H rc inner join secrty_user su on su.user_party_id = rc.action_user_party_id where TRUNC(rc.ACTION_DTTM) = TO_DATE(:date, 'YYYY-MM-DD') order by ACTION_DTTM asc", nativeQuery = true)
	List<FalconEntity> fetchDataByDate(@Param("date") String date);

	@Query(value = "select * from R_CS_CASE_H rc inner join secrty_user su on su.user_party_id = rc.action_user_party_id where rc.ACTION_DTTM > (sysdate -1) order by ACTION_DTTM asc", nativeQuery = true)
	List<FalconEntity> fetchData();

	@Query(value = "SELECT NEW CsCaseStatusEntity(e.caseId, e.caseStatus, e.lastUpdate, e.created) FROM CsCaseStatusEntity e where e.caseStatus = 'CONFIRMED_FRAUD' and e.created > (sysdate -1) order by e.lastUpdate asc")
	List<CsCaseStatusEntity> fetchCsCaseStatuses();
	
	@Query(value = "SELECT NEW CsCaseStatusEntity(e.caseId, e.caseStatus, e.lastUpdate, e.created) FROM CsCaseStatusEntity e where e.caseStatus = 'CONFIRMED_FRAUD' and TRUNC(e.created) = TO_DATE(:date, 'YYYY-MM-DD') order by e.lastUpdate asc")
	List<CsCaseStatusEntity> fetchCsCaseStatusesByDate(@Param("date") String date);

	@Modifying
	@Query(value = "UPDATE EXTRACT_CASE_DATA set process_flg = 'Y' where case_id in (:list)", nativeQuery = true)
	void updateExtractCaseData(@Param("list") List<String> list);

	@Query(value = "select count(*) from import_credit_auth_post c where c.created_dttm > TRUNC(sysdate)", nativeQuery = true)
	Long healthCheckCreditTrn();

	@Query(value = "select count(*) from import_debit_auth_post c where c.created_dttm > TRUNC(sysdate)", nativeQuery = true)
	Long healthCheckDebitTrn();

	@Query(value = "select count(*) from cs_case where created_dttm > TRUNC(sysdate)", nativeQuery = true)
	Long healthCheckFilterQuery();
}
