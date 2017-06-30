package com.sprinthive.evidence.mgmt.dao;

import com.sprinthive.evidence.mgmt.model.IdentityEvidenceRequest;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

import java.util.List;

public interface IdentityEvidenceRequestRepository extends ElasticsearchCrudRepository<IdentityEvidenceRequest, Long> {

    List<IdentityEvidenceRequest> findAllByIdentifyingNumber(String identifyingNumber);
}
