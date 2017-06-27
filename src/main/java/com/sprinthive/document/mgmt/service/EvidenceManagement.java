package com.sprinthive.document.mgmt.service;

import com.sprinthive.document.mgmt.dao.EvidenceRequestRepository;
import com.sprinthive.document.mgmt.exception.ResourceNotFoundException;
import com.sprinthive.document.mgmt.model.IdentityEvidenceRequest;
import com.sprinthive.document.mgmt.util.IdNumberUtil;
import lombok.extern.java.Log;
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

/**
 * Created by dirk on 2017/06/19.
 */
@EnableBinding({ProducerChannels.class})
@Log
@Service
@EnableIntegration
public class EvidenceManagement {


    private final MessageChannel evidenceRequestCreatedPush;
    private final MessageChannel evidenceRequestProofAddedPush;
    @Autowired
    private EvidenceRequestRepository evidenceRequestRepository;


    public EvidenceManagement(ProducerChannels producerChannels) {
        this.evidenceRequestCreatedPush = producerChannels.evidenceRequestCreatedPush();
        this.evidenceRequestProofAddedPush = producerChannels.evidenceRequestProofAddedPush();
    }

    public List<IdentityEvidenceRequest> findIdDocumentEvidance(String idNumber) {
        idNumber = IdNumberUtil.validate(idNumber);
        List<IdentityEvidenceRequest> evidenceRequests = evidenceRequestRepository.findAllByIdentifyingNumber(idNumber);

        return evidenceRequests;
    }

    public IdentityEvidenceRequest createIdentityEvidenceRequest(String firstNames, String middleNames, String lastName,
                                                                 Date dateOfBirth, String nationality,String identifyingNumber) {
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
        List<IdentityEvidenceRequest> evidenceRequests = findIdDocumentEvidance(idNumber);
        if(evidenceRequests == null || evidenceRequests.isEmpty()){
            throw new ResourceNotFoundException("Can't find and Identity Evidence Request for identifyingNumer: "+idNumber);
        }
        IdentityEvidenceRequest request = evidenceRequests.get(0);
        request.addProof(key, payload);
        IdentityEvidenceRequest evidenceRequest = evidenceRequestRepository.save(request);
        Message<IdentityEvidenceRequest> message = MessageBuilder.withPayload(evidenceRequest).build();
        this.evidenceRequestProofAddedPush.send(message);
        return evidenceRequest;
    }

    public Map<String, Object> getProofToIdentity(String idNumber, String proofkey) {
        List<IdentityEvidenceRequest> evidenceRequests = findIdDocumentEvidance(idNumber);
        if(evidenceRequests == null || evidenceRequests.isEmpty()){
            throw new ResourceNotFoundException("Can't find and Identity Evidence Request for identifyingNumer: "+idNumber);
        }
        IdentityEvidenceRequest request = evidenceRequests.get(0);
        Map<String, Object> proof = request.getProofs(proofkey);
        return proof;
    }

    @StreamListener("evidenceRequestProofAddedListener")
    public void process(IdentityEvidenceRequest evidenceRequest) throws URISyntaxException {
        log.info("Handling evidenceRequestProofAdded: "+ evidenceRequest);
    }
}

interface ProducerChannels{

    @Output
    MessageChannel evidenceRequestCreatedPush();

    @Output
    MessageChannel evidenceRequestProofAddedPush();

    @Input()
    SubscribableChannel evidenceRequestProofAddedListener();
}
