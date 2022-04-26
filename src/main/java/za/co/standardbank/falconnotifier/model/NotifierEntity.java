package za.co.standardbank.falconnotifier.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString
public class NotifierEntity {

	@Id
	@Column(name = "CASE_HISTORY_ID")
	private String caseHistoryId;

	@Column(name = "CASE_ID")
	private String caseId;

	@Column(name = "TENANT_CD")
	private String tenantCd;

	@Column(name = "CASE_REFERENCE_NUM")
	private String caseReferenceNum;

	@Column(name = "CASE_LEVEL_CD")
	private String caseLevelCd;

	@Column(name = "CASE_TYPEV2_CD")
	private String caseTypev2Cd;

	@Column(name = "CLIENT_XID")
	private String cclientXid;

	@Column(name = "ACTION_DTTM")
	private String cactionDttm;

	@Column(name = "FRAUD_TYPE_CD")
	private String fraudTypeCd;

	@Column(name = "QUEUE_NAME")
	private String queueName;

	@Column(name = "CASE_STATUS_TYPE_CD")
	private String caseStatusTypeCd;

	@Column(name = "CASE_STATUS_CD")
	private String caseStatusCd;

	@Column(name = "ACTION_USER_PARTY_ID")
	private String actionUserPartyId;

	@Column(name = "ACCOUNT_REFERENCE_XID")
	private String accountReferenceXid;

	@Column(name = "CASE_ACTION_CD1")
	private String caseActionCd1;

	@Column(name = "CASE_ACTION_CD2")
	private String caseActionCd2;

	@Column(name = "CASE_ACTION_CD3")
	private String caseActionCd3;

	@Column(name = "CASE_CREATED_DTTM")
	private String caseCreatedDttm;

	@Column(name = "CASE_OPENED_DTTM")
	private String caseOpenedDttm;

	@Column(name = "PROTECTED_AMT")
	private String protectedAmt;

	@Column(name = "CREATE_OPEN_LOSS_AMT")
	private String createOpenLossAmt;

	@Column(name = "OPEN_CLOSED_LOSS_AMT")
	private String openClosedLossAmt;

	@Column(name = "CLOSED_LOSS_AMT")
	private String closedLossAmt;

	@Column(name = "USER_PARTY_ID")
	private String userPartyId;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "ACTIVE_TENANT_CD")
	private String activeTennantCd;

	@Column(name = "CREATED_DTTM")
	private String createdDttm;

	@Column(name = "CREATED_BY_USER_ID")
	private String createdByUserId;

	@Column(name = "CREATED_BY_MODULE_CD")
	private String createdByModuleCd;

	@Column(name = "LAST_UPDATED_DTTM")
	private String lastUpdatedDttm;

	@Column(name = "LAST_UPDATED_BY_USER_ID")
	private String lastUpdatedByUserId;

	@Column(name = "ROW_VERSION_SEQ")
	private String rowVersionSeq;

	@Column(name = "ROW_STATUS_CD")
	private String rowStatusCd;

	@Column(name = "USER_NAME")
	private String userName;

	@Column(name = "LOCALE_CD")
	private String localeCd;

	@Column(name = "TIME_ZONE_CD")
	private String timeZoneCd;

	@Column(name = "SUPERVISOR_ID")
	private String supervisorId;

	@Column(name = "SUPERVISOR_FLG")
	private String supervisorFlg;

	@Column(name = "DEFAULT_TENANT_CD")
	private String defaultTenantCd;

	@Column(name = "PREFERRED_THEME_NAME")
	private String preferedThemeName;

}
