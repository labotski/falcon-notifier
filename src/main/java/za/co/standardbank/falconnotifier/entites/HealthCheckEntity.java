package za.co.standardbank.falconnotifier.entites;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HealthCheckEntity {

    private Long casesImportToICM;

    private Long creditTrnCount;

    private Long debitTrnCount;

    private Long caseCreateFilterCount;
}
