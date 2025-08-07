package org.dromara.kb.recycle.service;

import org.dromara.kb.recycle.domain.Recycle;
import org.dromara.kb.recycle.domain.vo.RecycleVo;
import org.dromara.kb.recycle.domain.bo.RecycleBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 回收站Service接口
 *
 * @author Lion Li
 * @date 2023-11-28
 */
public interface IRecycleService {

    /**
     * 查询回收站
     */
    RecycleVo queryById(Long id);

    /**
     * 查询回收站列表
     */
    TableDataInfo<RecycleVo> queryPageList(RecycleBo bo, PageQuery pageQuery);

    /**
     * 查询回收站列表
     */
    List<RecycleVo> queryList(RecycleBo bo);

    /**
     * 新增回收站
     */
    Boolean insertByBo(RecycleBo bo);

    /**
     * 修改回收站
     */
    Boolean updateByBo(RecycleBo bo);

    /**
     * 校验并批量删除回收站信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 恢复
     */
    Boolean recover(Long id);

    /**
     * 释放回收站记录
     * */
    Boolean releaseExpireRecord();
}
