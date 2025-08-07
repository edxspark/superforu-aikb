package org.dromara.kb.es.service.imp;

import co.elastic.clients.elasticsearch.core.DeleteResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.UpdateResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.dromara.kb.es.service.FileESService;
import org.dromara.kb.fileContent.domain.FileContent;
import org.dromara.kb.folderFile.domain.FolderFile;
import org.dromara.system.service.ISysDictDataService;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class FileESServiceImp implements FileESService {

    private final ISysDictDataService iSysDictDataService;
    private final ObjectMapper objectMapper;
    private final String INDEX_NAME = "km_docs";


    @Override
    public IndexResponse insertFileESData(FolderFile folderFile) {
        return null;
    }

    @Override
    public UpdateResponse renameFileESData(FolderFile folderFile) {
        return null;
    }

    @Override
    public UpdateResponse updateFileContentESData(FileContent fileContent) {
        return null;
    }

    @Override
    public DeleteResponse deleteFileESData(FolderFile folderFile) {
        return null;
    }

    @Override
    public SearchResponse searchFileESData(String key) {

        return null;
    }
}
