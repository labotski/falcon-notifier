package za.co.standardbank.falconnotifier.repository.icm;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import za.co.standardbank.falconnotifier.entites.icm.FalconCaseIdEntity;

@Repository
public interface IcmRepository extends CrudRepository<FalconCaseIdEntity, Long> {

	@Query(value = "select FALCON_CASE_ID from ic_cases where name = 'Falcon6 Import' and created > (sysdate -1)", nativeQuery = true)
	List<FalconCaseIdEntity> fetchFalconCaseIds();

	@Query(value = "select FALCON_CASE_ID from ic_cases where name = 'Falcon6 Import' and TRUNC(created) = TO_DATE(:date, 'YYYY-MM-DD')", nativeQuery = true)
	List<FalconCaseIdEntity> fetchFalconCaseIdsByDate(@Param("date") String date);

}
