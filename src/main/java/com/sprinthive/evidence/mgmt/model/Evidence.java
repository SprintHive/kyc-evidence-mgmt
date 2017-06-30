package com.sprinthive.evidence.mgmt.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@org.springframework.data.elasticsearch.annotations.Document(indexName = "evidence")
public class Evidence {
}
