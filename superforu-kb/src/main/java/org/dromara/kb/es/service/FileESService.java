package org.dromara.kb.es.service;

import org.dromara.kb.fileContent.domain.FileContent;
import org.dromara.kb.folderFile.domain.FolderFile;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.DeleteResponse;
import co.elastic.clients.elasticsearch.core.UpdateResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;


public interface FileESService {
    IndexResponse insertFileESData(FolderFile folderFile);
    UpdateResponse renameFileESData(FolderFile folderFile);
    UpdateResponse updateFileContentESData(FileContent fileContent);
    DeleteResponse deleteFileESData(FolderFile folderFile);
    SearchResponse searchFileESData(String key);
}
