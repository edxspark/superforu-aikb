package org.dromara.ai.service.imp;

import cn.hutool.json.JSONUtil;
import jodd.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.ai.service.IEmbeddingService;
import org.dromara.ai.service.bo.EmbeddingServiceBo;
import org.dromara.enums.FileTypeEnum;
import org.dromara.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
@Slf4j
@RequiredArgsConstructor
@Service
public class EmbeddingService implements IEmbeddingService {

    @Value("${ai.server}")
    private String AI_SERVER;

    /**
     * 文档向量化
     * 1. 判断参数
     * 2. 调用向量化接口
     * */
    @Override
    public Boolean documentToVectorDB(EmbeddingServiceBo bo) {
        boolean rt = true;

        // 1. 判断参数
        if(FileTypeEnum.PDF.getValue().equals(bo.getFileType())
        || FileTypeEnum.MARKDOWN.getValue().equals(bo.getFileType())){

            // 2. 调用向量化接口
            try {
                String params = JSONUtil.toJsonStr(bo);
                String embeddingAPI = AI_SERVER+"/ai/embedding/document_to_vector_db";
                log.info("#####AI向量化params:"+params);
                log.info("#####AI向量化API:"+embeddingAPI);
                String response = HttpClientUtils.doPost(embeddingAPI,params);
                log.info("#####AI向量化response:"+response);
                if(StringUtil.isEmpty(response)){
                    rt = false;
                }else{
                    rt = Integer.parseInt(response) > 0;
                }

            } catch (IOException e) {
                log.info("#####AI向量化失败:"+e.getMessage());
                rt = false;
            }
        } else{
            log.info("#####AI向量化暂不支持此类文档:"+bo.getFileType());
            return false;
        }
        log.info("#####AI向量化成功，fileId="+bo.getFileId());
        return rt;
    }

    /**
     * 删除向量化数据
     * */
    @Override
    public Boolean deleteDocumentFromVectorDB(EmbeddingServiceBo bo) {
        return null;
    }
}
