package org.dromara.kb.job.file;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.kb.recycle.service.impl.RecycleServiceImpl;
import org.springframework.stereotype.Component;
import tech.powerjob.worker.core.processor.ProcessResult;
import tech.powerjob.worker.core.processor.TaskContext;
import tech.powerjob.worker.core.processor.sdk.BasicProcessor;
import tech.powerjob.worker.log.OmsLogger;

@Slf4j
@Component
@RequiredArgsConstructor
public class RecyleJob implements BasicProcessor{
    private final RecycleServiceImpl recycleService;

    @Override
    public ProcessResult process(TaskContext context) throws Exception {
        log.info("#####定时任务:回收站释放定时回收器...");
        recycleService.releaseExpireRecord();
        return new ProcessResult(true, "success" );
    }
}
