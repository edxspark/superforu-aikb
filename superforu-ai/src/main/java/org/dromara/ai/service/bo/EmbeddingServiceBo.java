package org.dromara.ai.service.bo;

import lombok.Data;

@Data
public class EmbeddingServiceBo {
    private String agentType;
    private String kmId;
    private String fileType;
    private String fileId;
    private String fileName;
    private String fileContent;
    private String fileURL;
}
