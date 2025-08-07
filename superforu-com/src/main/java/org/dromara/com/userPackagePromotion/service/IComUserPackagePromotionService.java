package org.dromara.com.userPackagePromotion.service;

import org.dromara.com.userPackagePromotion.domain.ComUserPackagePromotion;
import org.dromara.com.userPackagePromotion.domain.vo.ComUserPackagePromotionVo;
import org.dromara.com.userPackagePromotion.domain.bo.ComUserPackagePromotionBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 优惠码Service接口
 *
 * @author Lion Li
 * @date 2024-04-07
 */
public interface IComUserPackagePromotionService {

    /**
     * 查询优惠码
     */
    ComUserPackagePromotionVo queryById(Long id);

    /**
     * 查询优惠码列表
     */
    TableDataInfo<ComUserPackagePromotionVo> queryPageList(ComUserPackagePromotionBo bo, PageQuery pageQuery);

    /**
     * 查询优惠码列表
     */
    List<ComUserPackagePromotionVo> queryList(ComUserPackagePromotionBo bo);

    /**
     * 新增优惠码
     */
    Boolean insertByBo(ComUserPackagePromotionBo bo);

    /**
     * 修改优惠码
     */
    Boolean updateByBo(ComUserPackagePromotionBo bo);

    /**
     * 校验并批量删除优惠码信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
