package com.sprinthive.document.mgmt.model;

import lombok.Builder;
import lombok.Data;

/**
 * Created by dirk on 2017/06/19.
 */
@Data
@Builder
@org.springframework.data.elasticsearch.annotations.Document(indexName="evidence")
public class Evidence {
}
