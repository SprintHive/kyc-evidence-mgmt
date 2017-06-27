package com.sprinthive.document.mngnt.model;

import static com.sprinthive.document.mngnt.model.VerificationType.*;

/**
 * Created by dirk on 2017/06/21.
 */
public enum CustomerType {

    INDIVIDUAL(INDIVIDUAL_DETAILS, ADDRESS, TAX_NUMBER),
    SOLE_PROPRIETORSHIP, CLOSE_CORPORATION, SA_COMPANY, FOREIGN_COMPANY, LISTED_COMPANY, PARTNERSHIP, TRUST;

    private final VerificationType[] verificationTypes;

    CustomerType() {
        verificationTypes = new VerificationType[0];
    }

    CustomerType(VerificationType... verificationTypes) {
        this.verificationTypes = verificationTypes;
    }
}
