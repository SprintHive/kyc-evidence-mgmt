package com.sprinthive.evidence.mgmt.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dirk on 2017/06/19.
 */
public class IdNumberUtil {

    private static final Pattern ID_NUMBER_PATTERN = Pattern.compile("((\\d{2}((0[13578]|1[02])(0[1-9]|[12]\\d|3[01])|(0[13456789]|1[012])(0[1-9]|[12]\\d|30)|02(0[1-9]|1\\d|2[0-8])))|([02468][048]|[13579][26])0229)(\\d{4})([01])(\\d{2})");

    public static String validate(String idNumber) {
        idNumber = cleanUp(idNumber);
        if (idNumber == null)
            throw new RuntimeException("No ID number provided");
        Matcher matcher = ID_NUMBER_PATTERN.matcher(idNumber);
        if(!matcher.find()){
            throw new RuntimeException("ID number not a valid SA id");
        }
        return idNumber;
    }

    public static String cleanUp(String idNumber) {
        if (idNumber == null)
            return null;
        idNumber = idNumber.replaceAll("\\s+","");
        return idNumber;
    }
}
