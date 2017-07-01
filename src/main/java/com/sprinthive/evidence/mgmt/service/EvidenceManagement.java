package com.sprinthive.evidence.mgmt.service;

import com.sprinthive.evidence.mgmt.dao.IdentityEvidenceRequestRepository;
import com.sprinthive.evidence.mgmt.exception.ProofNotFoundException;
import com.sprinthive.evidence.mgmt.exception.ResourceNotFoundException;
import com.sprinthive.evidence.mgmt.model.IdentityEvidenceRequest;
import com.sprinthive.evidence.mgmt.util.IdNumberUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@EnableBinding({ProducerChannels.class})
@Slf4j
@Service
@EnableIntegration
public class EvidenceManagement {


    private final MessageChannel evidenceRequestCreatedPush;
    private final MessageChannel evidenceRequestProofAddedPush;
    @Autowired
    private IdentityEvidenceRequestRepository evidenceRequestRepository;


    public EvidenceManagement(ProducerChannels producerChannels) {
        this.evidenceRequestCreatedPush = producerChannels.evidenceRequestCreatedPush();
        this.evidenceRequestProofAddedPush = producerChannels.evidenceRequestProofAddedPush();
    }

    public List<IdentityEvidenceRequest> findIdentityEvidenceRequests(String idNumber) {
        idNumber = IdNumberUtil.validate(idNumber);
        List<IdentityEvidenceRequest> evidenceRequests = evidenceRequestRepository.findAllByIdentifyingNumber(idNumber);

        return evidenceRequests;
    }

    public IdentityEvidenceRequest createIdentityEvidenceRequest(String firstNames, String middleNames, String lastName,
                                                                 Date dateOfBirth, String nationality, String identifyingNumber) {
        IdentityEvidenceRequest evidenceRequest = new IdentityEvidenceRequest();
        evidenceRequest.setFirstName(firstNames);
        evidenceRequest.setMiddleNames(middleNames);
        evidenceRequest.setLastName(lastName);
        evidenceRequest.setDateOfBirth(dateOfBirth);
        evidenceRequest.setNationality(nationality);
        evidenceRequest.setIdentifyingNumber(identifyingNumber);
        evidenceRequest = evidenceRequestRepository.save(evidenceRequest);
        Message<IdentityEvidenceRequest> message = MessageBuilder.withPayload(evidenceRequest).build();
        this.evidenceRequestCreatedPush.send(message);
        return evidenceRequest;
    }

    public IdentityEvidenceRequest addProofToIdentity(String idNumber, String key, Map<String, Object> payload) {
        IdentityEvidenceRequest request = getIdentityEvidenceRequest(idNumber);
        request.addProof(key, payload);
        IdentityEvidenceRequest evidenceRequest = evidenceRequestRepository.save(request);
        try {
            Message<IdentityEvidenceRequest> message = MessageBuilder.withPayload(evidenceRequest).build();
            this.evidenceRequestProofAddedPush.send(message);
        } catch (Exception e) {
            evidenceRequestRepository.delete(evidenceRequest.getId());
            throw e;
        }
        return evidenceRequest;
    }

    private IdentityEvidenceRequest getIdentityEvidenceRequest(String idNumber) {
        List<IdentityEvidenceRequest> evidenceRequests = findIdentityEvidenceRequests(idNumber);
        if (evidenceRequests == null || evidenceRequests.isEmpty()) {
            throw new ResourceNotFoundException("Can't find an Identity Evidence Request for identifyingNumber: " + idNumber);
        }
        return evidenceRequests.get(0);
    }

    public Map<String, Object> getIdentityProof(String idNumber, String proofKey) {
        IdentityEvidenceRequest evidenceRequest = getIdentityEvidenceRequest(idNumber);
        Map<String, Object> proof = evidenceRequest.getProofs(proofKey);
        if (proof == null){
            throw new ResourceNotFoundException("Can't find an proof "+proofKey+" on Identity Evidence Request:" + evidenceRequest.getId());
        }
        return proof;
    }

    @StreamListener("evidenceRequestProofAddedListener")
    public void process(IdentityEvidenceRequest evidenceRequest) throws URISyntaxException {
        log.info("Handling evidenceRequestProofAdded: " + evidenceRequest);
    }
}

interface ProducerChannels {

    @Output
    MessageChannel evidenceRequestCreatedPush();

    @Output
    MessageChannel evidenceRequestProofAddedPush();

    @Input()
    SubscribableChannel evidenceRequestProofAddedListener();
}
