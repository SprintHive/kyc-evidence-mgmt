package com.sprinthive.document.mngnt.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder(toBuilder = true)
public class Lead {

    private String guid;
    private Date creationDate;
    private Integer requestedAmount;
    private Integer requestedPeriod;
    private Integer monthlyRepayment;
    private Integer interestAndFees;
    private String mobileNumber;
    private Boolean mobileVerified;
    private String homeStatus;
    private Integer numberOfDependants;
    private Boolean receiveMarketing;
    private String firstName;
    private String surname;
    private String idNumber;
    private String idDocURL;
    private String homeLanguage;
    private String maritalStatus;
    private String email;
    private String street1;
    private String street2;
    private String suburb;
    private String city;
    private String province;
    private String postalCode;
    private String proofOfAddressUrl;
    private String employmentStatus;
    private Integer grossMonthlyIncome;
    private Integer netMonthlyIncome;
    private String frequencyOfIncome;
    private Integer payDateDay;
    private Integer monthlyRentalElectricityRatesExpenses;
    private Integer monthlyHouseholdExpenses;
    private Integer monthlyTransportExpenses;
    private Integer monthlyLoanRepayments;
    private Integer monthlyChildMaintenance;
    private Integer monthlyDisposableIncome;
    private String bank;
    private String[] bankStatementURLs;
    private String bankAccountNumber;
    private String bankAccountType;
    private String bankAccountHolderFullName;

}
