package org.dromara.com.userConfig.domain.bo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableId;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.com.userConfig.domain.ComUserConfig;
import org.dromara.common.mybatis.core.domain.BaseEntity;

/**
 * 用户配置业务对象 com_user_config
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = ComUserConfig.class, reverseConvertGenerate = false)
public class ComUserConfigUpdateBo extends BaseEntity {


    /**
     * 配置ID
     */
    private Long id;


    /**
     * 新增或移除的id
     */
    private String configValueId;

}
