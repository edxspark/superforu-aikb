package org.dromara.com.userPackageUseDetail.service;

import org.dromara.com.userPackageUseDetail.domain.bo.AddUseDetailBo;
import org.dromara.com.userPackageUseDetail.domain.vo.ComAddUserPackageUseDetailVo;
import org.dromara.com.userPackageUseDetail.domain.vo.ComUserPackageUseDetailVo;
import org.dromara.com.userPackageUseDetail.domain.bo.ComUserPackageUseDetailBo;
import org.dromara.common.core.domain.R;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 用户充值消费明细Service接口
 *
 * @author Lion Li
 * @date 2024-03-21
 */
public interface IComUserPackageUseDetailService {

    /**
     * 查询用户充值消费明细
     */
    ComUserPackageUseDetailVo queryById(Long id);

    /**
     * 查询用户充值消费明细列表
     */
    TableDataInfo<ComUserPackageUseDetailVo> queryPageList(ComUserPackageUseDetailBo bo, PageQuery pageQuery);

    /**
     * 查询用户充值消费明细列表
     */
    List<ComUserPackageUseDetailVo> queryList(ComUserPackageUseDetailBo bo);

    /**
     * 新增用户充值消费明细
     */
    Boolean insertByBo(ComUserPackageUseDetailBo bo);

    /**
     * 新增用户充值消费明细
     */
    ComAddUserPackageUseDetailVo addUseDetailByBo(AddUseDetailBo bo);

    /**
     * 修改用户充值消费明细
     */
    Boolean updateByBo(ComUserPackageUseDetailBo bo);

    /**
     * 校验并批量删除用户充值消费明细信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
