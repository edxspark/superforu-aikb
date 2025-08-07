package org.dromara.com.userPackageDetail.service;

import org.dromara.com.userPackageDetail.domain.ComUserPackageDetail;
import org.dromara.com.userPackageDetail.domain.vo.ComUserPackageDetailVo;
import org.dromara.com.userPackageDetail.domain.bo.ComUserPackageDetailBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 用户权益资源套餐明细Service接口
 *
 * @author Lion Li
 * @date 2024-03-21
 */
public interface IComUserPackageDetailService {

    /**
     * 查询用户权益资源套餐明细
     */
    ComUserPackageDetailVo queryById(Long id);

    /**
     * 查询用户权益资源套餐明细列表
     */
    TableDataInfo<ComUserPackageDetailVo> queryPageList(ComUserPackageDetailBo bo, PageQuery pageQuery);

    /**
     * 查询用户权益资源套餐明细列表
     */
    List<ComUserPackageDetailVo> queryList(ComUserPackageDetailBo bo);

    /**
     * 新增用户权益资源套餐明细
     */
    Boolean insertByBo(ComUserPackageDetailBo bo);

    /**
     * 修改用户权益资源套餐明细
     */
    Boolean updateByBo(ComUserPackageDetailBo bo);

    /**
     * 校验并批量删除用户权益资源套餐明细信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
