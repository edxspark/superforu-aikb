package org.dromara.kb.searchHistory.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 搜索历史对象 kb_search_history
 *
 * @author Moks
 * @date 2024-03-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("kb_search_history")
public class SearchHistory extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 搜索关键字
     */
    private String keyword;

    /**
     * 图片地址
     */
    private String picUrl;

    /**
     * 删除标志
     */
    @TableLogic
    private Long delFlag;

    /**
     * 搜索次数
     * */
    private Integer count;


}
