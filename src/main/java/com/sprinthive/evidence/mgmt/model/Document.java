package com.sprinthive.evidence.mgmt.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Document {

    @Id
    private long id;
    private String originalFilename;
    private String name;
    private String documentTypeKey;
    private String contentType;
    private String originChannel;
    private long size;

}
