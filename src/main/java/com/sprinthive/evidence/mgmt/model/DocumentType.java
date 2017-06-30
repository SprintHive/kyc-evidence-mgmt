package com.sprinthive.evidence.mgmt.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@org.springframework.data.elasticsearch.annotations.Document(indexName="document-type")
public class DocumentType {

    @Id
    private long Id;
    private String documentTypeKey;
    private String documentTypeDescription;
}
