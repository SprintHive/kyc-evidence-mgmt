package com.sprinthive.document.mgmt.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * Created by dirk on 2017/06/19.
 */
@Data
@org.springframework.data.elasticsearch.annotations.Document(indexName="document-type")
public class DocumentType {

    @Id
    private long Id;
    private String documentTypeKey;
    private String documentTypeDescription;
}
