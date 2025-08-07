package org.dromara.kb.kmCollaboration.service;

import org.dromara.common.core.domain.R;
import org.dromara.kb.kmCollaboration.domain.KmCollaboration;
import org.dromara.kb.kmCollaboration.domain.vo.KmCollaborationVo;
import org.dromara.kb.kmCollaboration.domain.bo.KmCollaborationBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 协同管理Service接口
 *
 * @author zzg
 * @date 2023-12-08
 */
public interface IKmCollaborationService {

    /**
     * 查询协同管理
     */
    KmCollaborationVo queryById(Long id);

    /**
     * 查询协同管理列表
     */
    TableDataInfo<KmCollaborationVo> queryPageList(KmCollaborationBo bo, PageQuery pageQuery);

    /**
     * 查询协同管理列表
     */
    List<KmCollaborationVo> queryList(KmCollaborationBo bo);

    /**
     * 新增协同管理
     */
    R<KmCollaborationVo> insertByBo(KmCollaborationBo bo);

    /**
     * 修改协同管理
     */
    Boolean updateByBo(KmCollaborationBo bo);

    /**
     * 校验并批量删除协同管理信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 据知识库ID获取团队协助集合信息
     * @param linkKmId 知识库id
     * @author zzg
     */
    List<KmCollaborationVo> listByLinkKmId(Long linkKmId);
}
