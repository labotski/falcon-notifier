package za.co.standardbank.falconnotifier.entites.icm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString
public class FalconCaseIdEntity {

	@Id
	@Column(name = "FALCON_CASE_ID")
	private String falconCaseId;
}
