package org.dromara.kb.kmShare.service;

import org.dromara.common.core.domain.R;
import org.dromara.kb.kmShare.domain.KmShare;
import org.dromara.kb.kmShare.domain.vo.KmShareVo;
import org.dromara.kb.kmShare.domain.bo.KmShareBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 分享预览Service接口
 *
 * @author zzg
 * @date 2023-12-08
 */
public interface IKmShareService {

    /**
     * 查询分享预览
     */
    KmShareVo queryById(Long id);

    /**
     * 查询分享预览
     */
    KmShareVo queryByKmShareId(String shaerId);

    /**
     * 查询分享预览列表
     */
    TableDataInfo<KmShareVo> queryPageList(KmShareBo bo, PageQuery pageQuery);

    /**
     * 查询分享预览列表
     */
    List<KmShareVo> queryList(KmShareBo bo);

    /**
     * 新增分享预览
     */
    R<KmShareVo> insertByBo(KmShareBo bo);

    /**
     * 修改分享预览
     */
    Boolean updateByBo(KmShareBo bo);

    /**
     * 校验并批量删除分享预览信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 根据知识库id获取单条分享预览信息
     * @param linkKmId
     * @return
     */
    KmShareVo getOneByLinkKmId(Long linkKmId);
}
