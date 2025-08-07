package org.dromara.ai.service;

import org.dromara.ai.service.bo.EmbeddingServiceBo;

public interface IEmbeddingService {
    Boolean documentToVectorDB(EmbeddingServiceBo bo);
    Boolean deleteDocumentFromVectorDB(EmbeddingServiceBo bo);
}
