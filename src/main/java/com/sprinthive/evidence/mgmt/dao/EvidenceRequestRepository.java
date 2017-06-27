package com.sprinthive.evidence.mgmt.dao;

import com.sprinthive.evidence.mgmt.model.IdentityEvidenceRequest;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

import java.util.List;

/**
 * Created by dirk on 2017/06/19.
 */
public interface EvidenceRequestRepository extends ElasticsearchCrudRepository<IdentityEvidenceRequest, Long> {

    List<IdentityEvidenceRequest> findAllByIdentifyingNumber (String identifyingNumber);
}
