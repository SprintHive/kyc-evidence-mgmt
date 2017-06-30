package com.sprinthive.evidence.mgmt.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@org.springframework.data.elasticsearch.annotations.Document(indexName="document")
public class EvidenceRequest {

    @Id
    private long id;


}
