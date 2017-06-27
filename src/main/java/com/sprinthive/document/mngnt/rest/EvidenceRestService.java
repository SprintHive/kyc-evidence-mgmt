package com.sprinthive.document.mngnt.rest;

import com.sprinthive.document.mngnt.model.EvidenceRequest;
import com.sprinthive.document.mngnt.model.IdentityEvidenceRequest;
import com.sprinthive.document.mngnt.service.EvidenceManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by dirk on 2017/06/19.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Service
public class EvidenceRestService {

    @Autowired
    private EvidenceManagement evidenceManagement;


    @RequestMapping(method = RequestMethod.GET, value = "/evidence/v1/ping")
    public String ping(){
        return new Date().toString();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/evidence/v1/identity/id/{idNumber}")
    public List<IdentityEvidenceRequest> findIdDocumentEvidance(@PathVariable String idNumber) {
        return evidenceManagement.findIdDocumentEvidance(idNumber);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/evidence/v1/identity/id/{idNumber}/proof/{proofkey}")
    public Map<String, Object> getProofToIdentity(@PathVariable String idNumber, @PathVariable String proofkey) {
        return evidenceManagement.getProofToIdentity(idNumber, proofkey);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/evidence/v1/identity")
    public IdentityEvidenceRequest findIdDocumentEvidance(@RequestBody  IdentityEvidenceRequest evidenceRequest) {
        return evidenceManagement.createIdentityEvidenceRequest(evidenceRequest.getFirstName(), evidenceRequest.getMiddleNames(), evidenceRequest.getLastName(), evidenceRequest.getDateOfBirth(), evidenceRequest.getNationality(), evidenceRequest.getIdentifyingNumber());
    }

    @RequestMapping(method = RequestMethod.POST, value = "/evidence/v1/identity/id/{idNumber}/proof/{proofkey}")
    public EvidenceRequest addProofToIdentity(@PathVariable String idNumber, @PathVariable String proofkey, @RequestBody Map<String, Object> payload) {
        return evidenceManagement.addProofToIdentity(idNumber, proofkey, payload);
    }
}
