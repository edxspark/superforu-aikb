package org.dromara.com.userEquity.service;

import org.dromara.com.userEquity.domain.ComUserEquity;
import org.dromara.com.userEquity.domain.vo.ComUserEquityVo;
import org.dromara.com.userEquity.domain.bo.ComUserEquityBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 用户权益套餐配置Service接口
 *
 * @author Lion Li
 * @date 2023-11-28
 */
public interface IComUserEquityService {

    /**
     * 查询用户权益套餐配置
     */
    ComUserEquityVo queryById(Long id);

    /**
     * 查询用户权益套餐配置列表
     */
    TableDataInfo<ComUserEquityVo> queryPageList(ComUserEquityBo bo, PageQuery pageQuery);

    /**
     * 查询用户权益套餐配置列表
     */
    List<ComUserEquityVo> queryList(ComUserEquityBo bo);

    /**
     * 新增用户权益套餐配置
     */
    Boolean insertByBo(ComUserEquityBo bo);

    /**
     * 修改用户权益套餐配置
     */
    Boolean updateByBo(ComUserEquityBo bo);

    /**
     * 校验并批量删除用户权益套餐配置信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
