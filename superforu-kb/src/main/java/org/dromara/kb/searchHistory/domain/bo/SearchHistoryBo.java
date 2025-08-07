package org.dromara.kb.searchHistory.domain.bo;

import org.dromara.kb.searchHistory.domain.SearchHistory;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 搜索历史业务对象 kb_search_history
 *
 * @author Moks
 * @date 2024-03-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SearchHistory.class, reverseConvertGenerate = false)
public class SearchHistoryBo extends BaseEntity {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 搜索关键字
     */
    @NotBlank(message = "搜索关键字不能为空", groups = { AddGroup.class, EditGroup.class })
    private String keyword;

    /**
     * 搜索次数
     * */
    private Integer count;


}
