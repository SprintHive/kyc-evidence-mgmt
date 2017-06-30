package com.sprinthive.evidence.mgmt.rest;

import com.sprinthive.evidence.mgmt.model.EvidenceRequest;
import com.sprinthive.evidence.mgmt.model.IdentityEvidenceRequest;
import com.sprinthive.evidence.mgmt.service.EvidenceManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Service
public class EvidenceRestService {

    @Autowired
    private EvidenceManagement evidenceManagement;

    @RequestMapping(method = RequestMethod.GET, value = "/evidence/ping")
    public void ping() {
    }

    @RequestMapping(method = RequestMethod.GET, value = "/evidence/v1/identity/id/{idNumber}")
    public List<IdentityEvidenceRequest> findIdDocumentEvidence(@PathVariable String idNumber) {
        return evidenceManagement.findIdDocumentEvidence(idNumber);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/evidence/v1/identity/id/{idNumber}/proof/{proofkey}")
    public Map<String, Object> getProofToIdentity(@PathVariable String idNumber, @PathVariable String proofkey) {
        return evidenceManagement.getIdentityProof(idNumber, proofkey);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/evidence/v1/identity")
    public IdentityEvidenceRequest findIdDocumentEvidence(@RequestBody IdentityEvidenceRequest evidenceRequest) {
        return evidenceManagement.createIdentityEvidenceRequest(evidenceRequest.getFirstName(), evidenceRequest.getMiddleNames(), evidenceRequest.getLastName(), evidenceRequest.getDateOfBirth(), evidenceRequest.getNationality(), evidenceRequest.getIdentifyingNumber());
    }

    @RequestMapping(method = RequestMethod.POST, value = "/evidence/v1/identity/id/{idNumber}/proof/{proofkey}")
    public EvidenceRequest addProofToIdentity(@PathVariable String idNumber, @PathVariable String proofkey, @RequestBody Map<String, Object> payload) {
        return evidenceManagement.addProofToIdentity(idNumber, proofkey, payload);
    }
}
