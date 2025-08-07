package org.dromara.com.team.service;

import org.dromara.com.team.domain.bo.ComTeamDeleteBo;
import org.dromara.com.team.domain.bo.ComTeamEditBo;
import org.dromara.com.team.domain.vo.ComTeamVo;
import org.dromara.com.team.domain.bo.ComTeamBo;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;


import java.util.List;

/**
 * 团队管理Service接口
 *
 * @author JackLiao
 * @date 2023-12-08
 */
public interface IComTeamService {


    /**
     * 查询团队管理列表
     */
    TableDataInfo<ComTeamVo> queryList(PageQuery pageQuery);

    /**
     * 新增团队管理
     */
    Boolean insertByBo(ComTeamBo bo);

    /**
     * 修改团队管理
     */
    Boolean updateByBo(ComTeamEditBo bo);

    /**
     * 删除团队管理信息(解散团队)
     */
    void deleteTeam(ComTeamDeleteBo bo);


    /**
     * 退出团队
     */
    void quitTeam(ComTeamDeleteBo bo);
}
