package za.co.standardbank.falconnotifier.entites.falcon;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString
@Table(name = "cs_case_status")
public class CsCaseStatusEntity {

	@Id
	@Column(name = "CASE_ID")
	private String caseId;

	@Column(name = "CASE_STATUS_CD")
	private String caseStatus;

	@Column(name = "LAST_UPDATED_DTTM")
	private Date lastUpdate;

	@Column(name = "CREATED_DTTM")
	private Date created;

	public CsCaseStatusEntity(String caseId, String caseStatus, Date lastUpdate, Date created) {
		super();
		this.caseId = caseId;
		this.caseStatus = caseStatus;
		this.lastUpdate = lastUpdate;
		this.created = created;
	}

}
