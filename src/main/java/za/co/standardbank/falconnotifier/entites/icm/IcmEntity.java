package za.co.standardbank.falconnotifier.entites.icm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString
public class IcmEntity {
	
	@Id
	@Column(name = "ID")
	private String id;

}
