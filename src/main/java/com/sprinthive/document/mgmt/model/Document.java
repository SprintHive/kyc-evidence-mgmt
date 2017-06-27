package com.sprinthive.document.mgmt.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * Created by dirk on 2017/06/19.
 */
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
