package com.sprinthive.document.mngnt.model;

import lombok.Builder;
import lombok.Data;

import java.util.*;

/**
 * Created by dirk on 2017/06/21.
 */
@Data
public class IdentityEvidenceRequest extends EvidenceRequest {

    private String firstName;
    private String middleNames;
    private String lastName;
    private Date dateOfBirth;
    private String nationality;
    private String identifyingNumber;
    private Map<String, Map<String, Object>> proofs;

    public void addProof(String key, Map<String, Object> proof) {

//        if(proof == null){
//            throw new
//        }
        if(proofs == null){
            proofs = new HashMap<>();
        }
        proofs.put(key, proof);
    }

    public Map<String, Object> getProofs(String proofkey) {
        if(proofs == null){
            return null;
        }
        return proofs.get(proofkey);
    }
}
