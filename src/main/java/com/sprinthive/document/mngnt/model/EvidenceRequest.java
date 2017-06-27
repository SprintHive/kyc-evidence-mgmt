package com.sprinthive.document.mngnt.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * Created by dirk on 2017/06/20.
 */
@Data
@org.springframework.data.elasticsearch.annotations.Document(indexName="document")
public class EvidenceRequest {

    @Id
    private long id;


}
