package org.dromara.kb.searchHistory.domain.vo;

import org.dromara.kb.searchHistory.domain.SearchHistory;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;



/**
 * 搜索历史视图对象 kb_search_history
 *
 * @author Moks
 * @date 2024-03-06
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SearchHistory.class)
public class SearchHistoryVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * 搜索关键字
     */
    @ExcelProperty(value = "搜索关键字")
    private String keyword;

    /**
     * 搜索次数
     * */
    private Integer count;

}
