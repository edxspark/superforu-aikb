package org.dromara.kb.job.file;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.kb.km.service.IKmService;
import org.springframework.stereotype.Component;
import tech.powerjob.worker.core.processor.ProcessResult;
import tech.powerjob.worker.core.processor.TaskContext;
import tech.powerjob.worker.core.processor.sdk.BasicProcessor;

@Slf4j
@Component
@RequiredArgsConstructor
public class AIEmbeddingJob implements BasicProcessor{

    private final IKmService iKmService;

    @Override
    public ProcessResult process(TaskContext context) throws Exception {
        log.info("#####定时任务:AI Embedding....");
        iKmService.kmEmbedding();
        return new ProcessResult(true, "success" );
    }
}
