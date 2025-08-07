package org.dromara.kb.km.service;

import org.dromara.common.core.domain.R;
import org.dromara.kb.km.domain.Km;
import org.dromara.kb.km.domain.bo.KmTreeDataBo;
import org.dromara.kb.km.domain.vo.KmTreeDataVo;
import org.dromara.kb.km.domain.vo.KmVo;
import org.dromara.kb.km.domain.bo.KmBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 知识库Service接口
 *
 * @author zzg
 * @date 2023-12-07
 */
public interface IKmService {

    /**
     * 获取开启AI的知识库列表
     * */
    List<KmVo> listIsOpenAI();

    /**
     * AI向量化(批量)
     * */
    Boolean kmEmbedding();

    /**
     * AI向量化(单个文件)
     * */
    Boolean kmEmbeddingSingleFile(Long fileId);

    /**
     * 查询知识库
     */
    KmVo queryById(Long id);

    /**
     * 查询知识库列表
     */
    TableDataInfo<KmVo> queryPageList(KmBo bo, PageQuery pageQuery);

    /**
     * 查询主知识库ID
     */
    String getMainKmId();

    /**
     * 查询知识库列表
     */
    List<KmVo> queryList(KmBo bo);


    /**
     * 新增知识库
     */
    R<KmVo> insertByBo(KmBo bo);

    /**
     * 修改知识库
     */
    Boolean updateByBo(KmBo bo);

    /**
     * 校验并批量删除知识库信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 查询知识库目录树
     */
    R<KmTreeDataVo> queryTreeData(KmTreeDataBo bo);

    /**
     * 更新知识库目录树
     */
    R<Void> updateTreeData(KmTreeDataBo bo);
}
